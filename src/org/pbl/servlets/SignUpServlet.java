package org.pbl.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pbl.business.Login;
import org.pbl.business.PblResponse;
import org.pbl.business.User;
import org.pbl.db.UserDAO;
import org.pbl.utils.ServletUtility;

@WebServlet(name = "signup", urlPatterns = "/signup")
public class SignUpServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		UserDAO userDAO = new UserDAO();
		User user = new User();
		user.setAddress(req.getParameter("address"));
		user.setCity(req.getParameter("city"));
		user.setCountry(req.getParameter("country"));
		user.setDob(req.getParameter("dob"));
		user.setEmail(req.getParameter("email"));
		user.setFirstName(req.getParameter("firstName"));
		user.setPhone(req.getParameter("phone"));
		user.setSecondName(req.getParameter("secondName"));
		user.setZip(req.getParameter("zip"));
		user.setState(req.getParameter("state"));

		Login login = new Login();
		login.setActive(true);
		login.setEmail(req.getParameter("email"));
		login.setPassword(req.getParameter("password"));
		login.setResetPassword(false);

		PblResponse pblResponse = userDAO.loginNewUser(user, login);
		PrintWriter pw = resp.getWriter();
		pw.write(ServletUtility.toJson(pblResponse));
	}

}
