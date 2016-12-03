package org.pbl.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pbl.business.PblResponse;
import org.pbl.db.UserDAO;
import org.pbl.utils.ServletUtility;

@WebServlet(name = "login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3311563498007361313L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		PblResponse pblResponse = new UserDAO().login(email, password);
		PrintWriter pw = resp.getWriter();
		pw.write(ServletUtility.toJson(pblResponse));
	}

}
