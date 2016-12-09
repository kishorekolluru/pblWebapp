package org.pbl.db;

import org.pbl.business.Category;
import org.pbl.business.DonorReceipt;
import org.pbl.business.Item;
import org.pbl.business.PblResponse;
import org.pbl.business.PostedItem;
import org.pbl.business.User;
import org.pbl.utils.DAOUtility;

import com.mysql.jdbc.CallableStatement;
import com.pbl.email.SendMail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by kishorekolluru on 12/1/16.
 */
public class ItemDao {
	Connection connection = null;
	Random rand = new Random();
	CallableStatement cstmt = null;

	public List<PostedItem> getAllPostedItems() throws SQLException,
			ClassNotFoundException {
		Connection conn = DBConnection.getConnection();
		List<PostedItem> allItemList = null;
		PreparedStatement st = conn
				.prepareStatement("select i.name, pi.itemId, pi.price, i.Brand, i.description, i.color, i.ItemType,i.Size, i.datereceived, i.donorId, i.processed, i.picture, pi.posteddate, pi.price\n" +
						",pi.discount, pi.brandvalue FROM posteditem pi INNER JOIN item i ON pi.itemId = i.ItemId;");
		try {

			ResultSet rs = st.executeQuery();
			allItemList = new ArrayList<>();
			while (rs.next()) {
				PostedItem item = new PostedItem();
				item.setItemId(rs.getInt("itemId"));
				item.setDescription(rs.getString("description"));
				item.setColor(rs.getString("color"));
				item.setItemType(rs.getString("ItemType"));
				item.setSize(rs.getString("Size"));
				item.setBrand(rs.getString("Brand"));
				item.setReceivedDate(rs.getDate("datereceived"));
				item.setDonorId(rs.getInt("donorId"));
				item.setPicture(rs.getString("picture"));
				item.setProcessedFlag(rs.getInt("processed"));
				item.setPrice(rs.getDouble("price"));
				//new columns
				item.setName(rs.getString("name"));
				item.setCategories(getCategoriesForItem(item.getItemId(), conn));
				allItemList.add(item);
			}
		} finally {
			DBConnection.closeConn(conn);
		}
		return allItemList;
	}

	private List<Category> getCategoriesForItem(int itemId, Connection conn)
			throws SQLException {
		String sql = "SELECT ic.catid, ic.itemid, c.name as categ_description, c.Gender FROM itemcat ic INNER JOIN category C ON ic.catid = C.CategoryId\n"
				+ "where ic.itemid=?;";
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, itemId);
		ResultSet rs = st.executeQuery();
		List<Category> categories = new ArrayList<>();
		while (rs.next()) {
			Category ca = new Category();
			ca.setCategDescription(rs.getString("categ_description"));
			ca.setCategGender(rs.getString("Gender"));
			ca.setCategId(rs.getInt("catid"));
			ca.setItemId(rs.getInt("itemId"));
			categories.add(ca);
		}
		return categories;
	}

	// CREATED BY SRIRAM
	@SuppressWarnings("finally")
	public PblResponse getDonatedItems(Integer donorId) {
		Item item = null;
		PblResponse pblResponse = new PblResponse();
		try {
			connection = DBConnection.getConnection();
			PreparedStatement ps = (PreparedStatement) connection
					.prepareStatement(DAOUtility.DONATED_ITEMS_QUERY);
			ps.setInt(DAOUtility.ONE, donorId);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet != null) {
				List<Item> donatedItems = new ArrayList<Item>();
				while (resultSet.next()) {
					item = new Item();
					item.setBrand(resultSet.getString("brand"));
					item.setColor(resultSet.getString("color"));
					item.setDescription(resultSet.getString("description"));
					item.setDonorId(resultSet.getInt("donorId"));
					item.setItemId(resultSet.getInt("itemId"));
					item.setItemType(resultSet.getString("itemType"));
					item.setPicture(resultSet.getString("picture"));
					item.setProcessed(resultSet.getBoolean("processed"));
					item.setReceivedDate(DAOUtility.sdf.format(new Date(
							resultSet.getDate("datereceived").getTime())));
					item.setSize(resultSet.getString("size"));
					item.setCategories(getCategoriesForItem(item.getItemId(),
							connection));
					donatedItems.add(item);
				}
				pblResponse.setStatus("success");
				pblResponse.setResponse(donatedItems);

			}
		}

		catch (SQLException e) {
			e.getMessage();
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		} finally {
			DBConnection.closeConn(connection);
			return pblResponse;
		}
	}

	@SuppressWarnings("finally")
	public PblResponse createItem(Item item) {

		PblResponse pblResponse = new PblResponse();
		try {
			connection = DBConnection.getConnection();
			PreparedStatement ps = (PreparedStatement) connection
					.prepareStatement(DAOUtility.CREATE_ITEM_QUERY);
			ps.setString(DAOUtility.ONE, item.getDescription());
			ps.setString(DAOUtility.TWO, item.getColor());
			ps.setString(DAOUtility.THREE, item.getItemType());
			ps.setString(DAOUtility.FOUR, item.getSize());
			ps.setString(DAOUtility.FIVE, item.getBrand());
			ps.setDate(DAOUtility.SIX, new java.sql.Date(new Date().getTime()));
			ps.setInt(DAOUtility.SEVEN, item.getDonorId());
			ps.setString(DAOUtility.EIGHT, item.getPicture());
			ps.setBoolean(DAOUtility.NINE, item.getProcessed());
			Integer inserted = ps.executeUpdate();
			if (inserted != null) {
				try (ResultSet generatedKeys = (ResultSet) ps
						.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						item.setItemId(generatedKeys.getInt(1));
						cstmt = (CallableStatement) connection
								.prepareCall(DAOUtility.INCREMENT_DONATED_COUNT);
						cstmt.setInt(DAOUtility.ONE, item.getDonorId());
						cstmt.execute();
					}
				}
				pblResponse.setResponse(item);
				pblResponse.setStatus("success");
				User donor = new UserDAO().getUserById(item.getDonorId());
				DonorReceipt donorReceipt = new DonorReceipt();
				donorReceipt.setEmail(donor.getEmail());
				donorReceipt.setDonorName(donor.getFirstName() + " "
						+ donor.getSecondName());
				donorReceipt.setDonorAddress(donor.getAddress() + ", "
						+ donor.getCity() + ", " + donor.getCountry());
				donorReceipt.setTaxGift((rand.nextInt((20 - 1) + 1) + 1) + "$");
				donorReceipt.setDonatedOn(DAOUtility.sdf.format(new Date()));
				donorReceipt.setReceiptNo(item.getItemId().toString());
				donorReceipt.setItem(item.getDescription());
				donorReceipt.setReceiptDate(DAOUtility.sdf.format(new Date()));

				new SendMail().sendReceipt(donorReceipt);

			}

		} catch (SQLException e) {
			e.getMessage();
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		} finally {
			DBConnection.closeConn(connection);
			return pblResponse;
		}
		return pblResponse;

	}
}
