package org.pbl.servlets;

import com.google.gson.Gson;
import org.pbl.business.PblResponse;
import org.pbl.business.PostedItem;
import org.pbl.db.ItemDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kishorekolluru on 12/1/16.
 */
@WebServlet(name = "AllItemsServlet", urlPatterns = "/allPostedItems")
public class AllItemsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        List<PostedItem> items = null;
        PblResponse respon = new PblResponse();
        ItemDao dao = new ItemDao();
        try {
            items = dao.getAllPostedItems();
            respon.setStatus("success");
            respon.setResponse(items);
            writer.println(new Gson().toJson(respon, PblResponse.class));
            respon.getResponse();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            respon.setStatus("failure");
            respon.setError("Something went wrong: "+e.getMessage());
        }

    }
}
