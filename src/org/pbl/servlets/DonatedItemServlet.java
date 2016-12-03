package org.pbl.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pbl.business.PblResponse;
import org.pbl.db.ItemDao;
import org.pbl.db.UserDAO;
import org.pbl.utils.ServletUtility;

@WebServlet(name = "donatedItem", urlPatterns = "/donatedItem")
public class DonatedItemServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Integer donorId = Integer.parseInt(req.getParameter("donorId"));
		PblResponse pblResponse = new ItemDao().getDonatedItems(donorId);
		PrintWriter pw = resp.getWriter();
		pw.write(ServletUtility.toJson(pblResponse));

	}

}
