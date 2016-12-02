package org.pbl.servlets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.geometry.Pos;
import org.pbl.business.PostedItem;
import org.pbl.db.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

/**
 * Created by kishorekolluru on 12/1/16.
 */
@WebServlet(name ="ItemBoughtServlet",urlPatterns = "/itemBought")
public class ItemBoughtServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Scanner s = new Scanner(req.getInputStream(), "UTF-8").useDelimiter("\\A");
        String st = s.hasNext() ? s.next() : "";
//        String json = st.substring(4);
//        List<PostedItem> boughtItems = new Gson().fromJson(json, new TypeToken<List<PostedItem>>(){}.getType());
        try {
            Connection conn = DBConnection.getConnection();
            CallableStatement cst = conn.prepareCall("{call ITEM_BOUGHT_PROC(?, ?)}");
            cst.setInt(1, 1);
            cst.setInt(2, 3);
            cst.execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Body: " +st);
        resp.getWriter().write("Now here is an output!");
    }
}
