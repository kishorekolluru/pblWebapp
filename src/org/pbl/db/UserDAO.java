package org.pbl.db;

import org.pbl.business.Login;
import org.pbl.business.PblResponse;
import org.pbl.business.User;
import org.pbl.utils.DAOUtility;

import java.sql.*;
import java.text.ParseException;



public class UserDAO {
	private Connection connection = null;
	private ResultSet resultSet = null;
	private PblResponse pblResponse = null;

	@SuppressWarnings({ "finally" })
	public PblResponse login(String email, String password) {
		try {
			pblResponse = new PblResponse();
			User user = null;
			connection = (Connection) DBConnection.getConnection();
			PreparedStatement ps = (PreparedStatement) connection
					.prepareStatement(DAOUtility.LOGIN_QUERY);
			ps.setString(DAOUtility.ONE, email);
			ps.setString(DAOUtility.TWO, password);
			ps.setBoolean(DAOUtility.THREE, true);
			resultSet = (ResultSet) ps.executeQuery();
			if (resultSet != null && resultSet.next()) {
				ps = (PreparedStatement) connection
						.prepareStatement(DAOUtility.USER_QUERY);
				ps.setInt(DAOUtility.ONE, resultSet.getInt("userid"));
				resultSet = (ResultSet) ps.executeQuery();
				if (resultSet != null && resultSet.next()) {
					user = getUserById(resultSet.getInt("userid"));
					pblResponse.setStatus("success");
					pblResponse.setResponse(user);
				}
			} else {
				pblResponse.setStatus("failure");
				pblResponse.setError(DAOUtility.LOGIN_FAILED);
			}
		} catch (ClassNotFoundException e) {
			e.getMessage();
		} catch (SQLException e) {
			e.getMessage();
		} finally {
			DBConnection.closeConn(connection);
			return pblResponse;
		}
	}

	@SuppressWarnings("finally")
	public PblResponse loginNewUser(User user, Login login) {
		PblResponse pblResponse = new PblResponse();
		Integer userId = createUser(user);
		try {
			if (userId != null) {
				login.setUserId(userId);

				PreparedStatement ps = (PreparedStatement) connection
						.prepareStatement(DAOUtility.CREATE_LOGIN_USER_QUERY);
				ps.setString(DAOUtility.ONE, login.getEmail());
				ps.setString(DAOUtility.TWO, login.getPassword());
				ps.setInt(DAOUtility.THREE, userId);
				ps.setBoolean(DAOUtility.FOUR, login.getActive());
				ps.setBoolean(DAOUtility.FIVE, login.getResetPassword());
				Integer loginId = ps.executeUpdate();
				if (loginId != null) {
					user.setUserId(userId);
					pblResponse.setResponse(user);
					pblResponse.setStatus("success");
				}
			} else {
				pblResponse.setStatus("failure");
				pblResponse.setError(DAOUtility.USER_EXISTS);
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

	}

	public Integer createUser(User user) {
		Integer userId = null;
		try {
			connection = (Connection) DBConnection.getConnection();
			PreparedStatement ps = (PreparedStatement) connection
					.prepareStatement(DAOUtility.CREATE_USER_QUERY);
			ps.setString(DAOUtility.ONE, user.getFirstName());
			ps.setString(DAOUtility.TWO, user.getSecondName());
			ps.setString(DAOUtility.THREE, user.getAddress());
			ps.setString(DAOUtility.FOUR, user.getCity());
			ps.setString(DAOUtility.FIVE, user.getState());
			ps.setString(DAOUtility.SIX, user.getZip());
			ps.setString(DAOUtility.SEVEN, user.getPhone());
			ps.setString(DAOUtility.EIGHT, user.getEmail());
			ps.setString(DAOUtility.NINE, user.getCountry());
			ps.setDate(DAOUtility.TEN,
					new Date(DAOUtility.sdf.parse(user.getDob()).getTime()));
			int created = ps.executeUpdate();
			if (created == 1) {
				try (ResultSet generatedKeys = (ResultSet) ps
						.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						userId = generatedKeys.getInt(1);
					} else {
						throw new SQLException(
								"Creating user failed, no ID obtained.");
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.getMessage();
		} catch (SQLException e) {
			e.getMessage();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return userId;
	}

	public User getUserById(Integer userId) {

		User user = null;
		try {
			PreparedStatement ps = (PreparedStatement) connection
					.prepareStatement(DAOUtility.USER_QUERY);
			ps.setInt(DAOUtility.ONE, userId);
			resultSet = (ResultSet) ps.executeQuery();
			if (resultSet != null && resultSet.next()) {
				user = new User();
				user.setUserId(resultSet.getInt("userid"));
				user.setAddress(resultSet.getString("firstname"));
				user.setCity(resultSet.getString("secondname"));
				user.setCountry(resultSet.getString("country"));
				user.setDob(resultSet.getString("dob"));
				user.setEmail(resultSet.getString("email"));
				user.setFirstName(resultSet.getString("firstname"));
				user.setPhone(resultSet.getString("phone"));
				user.setSecondName(resultSet.getString("secondname"));
				user.setZip(resultSet.getString("zip"));
			}
		} catch (SQLException e) {
			e.getMessage();
		} finally {
			DBConnection.closeConn(connection);
		}
		return user;
	}

}