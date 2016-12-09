package org.pbl.servlets;

import com.google.gson.Gson;
import org.pbl.business.User;
import org.pbl.db.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by kishorekolluru on 12/6/16.
 */
@WebServlet(name = "LoginCheckServlet", urlPatterns = "/loginCheckServlet")
public class LoginCheckServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String pwd = req.getParameter("password");
        System.out.println(username);
        System.out.println(pwd);

        if (username != null && pwd != null) {
            try {
                Connection conn = DBConnection.getConnection();
                PreparedStatement st = conn.prepareStatement("select from donation.admin where AdminId=?");//TODO CHANGE THE
                //SQL QUERY
                st.setInt(1, Integer.parseInt(username));

                ResultSet rs = st.executeQuery();
                PrintWriter writer = resp.getWriter();
                if (rs.next()) {
                    st = conn.prepareStatement("SELECT *FROM donation.user WHERE UserId=?");
                    st.setInt(1, Integer.parseInt(username));
                    ResultSet rs1 = st.executeQuery();
                    if (rs1.next()) {

                        User user = new User();
                        user.setFirstName(rs1.getString("Firstname"));
                        user.setSecondName(rs1.getString("Secondname"));
                        user.setAddress(rs1.getString("Address"));
                        user.setCity(rs1.getString("City"));
                        user.setState(rs1.getString("State"));
                        user.setZip(rs1.getString("Zip"));
                        user.setZip(rs1.getString("Zip"));
                        user.setPhone(rs1.getString("Phone"));
                        user.setEmail(rs1.getString("Email"));
                        user.setCountry(rs1.getString("Country"));
                        user.setCountry(rs1.getString("Country"));
                        user.setDob(String.valueOf(rs1.getDate("DOB")));

                        writer.println(new Gson().toJson(user, User.class));
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }
}
