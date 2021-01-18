package pl.imsi;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginTimedOut")
public class LoginTimedOut extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginTimedOut() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Boolean b;
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		Database database = new Database();

		try {

			String id = database.getUserId(request.getParameter("username"));

			if (!id.equals("0")) {

				b = database.selectTimeOut(id);

				if (b) {

					database.updateLogoutTime(id);
					request.getRequestDispatcher("/form.jsp").forward(request, response);

				}

			}

		} catch (Exception e) {
			System.out.println("The exception is" + e);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
