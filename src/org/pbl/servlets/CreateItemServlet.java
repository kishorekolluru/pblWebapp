package org.pbl.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pbl.business.Item;
import org.pbl.business.PblResponse;
import org.pbl.db.ItemDao;
import org.pbl.db.UserDAO;
import org.pbl.utils.ServletUtility;

@WebServlet(name = "createItem", urlPatterns = "/donate")
public class CreateItemServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8600192625053968595L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Item item = new Item();
		item.setBrand(req.getParameter("brand"));
		item.setColor(req.getParameter("color"));
		item.setDescription(req.getParameter("description"));
		item.setDonorId(Integer.parseInt(req.getParameter("donorId")));
		item.setItemType(req.getParameter("itemType"));
		item.setPicture(req.getParameter("picture"));
		item.setProcessed(Boolean.parseBoolean(req.getParameter("processed")));
		item.setReceivedDate(req.getParameter("dateReceived"));
		item.setSize(req.getParameter("size"));
		PblResponse pblResponse = new ItemDao().createItem(item);
		PrintWriter pw = resp.getWriter();
		pw.write(ServletUtility.toJson(pblResponse));
	}

}
