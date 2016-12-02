package org.pbl.db;

import org.pbl.business.Category;
import org.pbl.business.PostedItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kishorekolluru on 12/1/16.
 */
public class ItemDao {
    public List<PostedItem> getAllPostedItems() throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.getConnection();
        List<PostedItem> allItemList = null;
        PreparedStatement st =conn.prepareStatement("select pi.itemId, i.Brand, i.description, i.color, i.ItemType,i.Size, cat.CategoryId,\n" +
                "  cat.Gender, cat.name as cat_description, i.datereceived, i.donorId, i.processed, i.picture, pi.posteddate, pi.price\n" +
                ",pi.discount, pi.brandvalue FROM posteditem pi INNER JOIN item i ON pi.itemId = i.ItemId\n" +
                "INNER JOIN itemcat ic on ic.itemid = pi.itemId INNER JOIN category cat on ic.catid = cat.CategoryId;");
        try{

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
                item.setCategories(getCategoriesForItem(item.getItemId(),conn));
                allItemList.add(item);
            }
        }finally {
            DBConnection.closeConn(conn);
        }
        return allItemList;
    }

    private List<Category> getCategoriesForItem(int itemId, Connection conn) throws SQLException {
        String sql = "SELECT ic.catid, ic.itemid, c.name as categ_description, c.Gender FROM itemcat ic INNER JOIN category C ON ic.catid = C.CategoryId\n" +
                "where ic.itemid=?;";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1,itemId);
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
}
