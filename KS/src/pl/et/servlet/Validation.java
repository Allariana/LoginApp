package pl.et.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Validation
 */
@WebServlet("/Validation")
public class Validation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Validation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		String connectionURL = "jdbc:h2:tcp://localhost/~/test10";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		response.setContentType("text/html");

		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(connectionURL, "sa", "");
			String sql = "select * from user u join password p on u.id = p.user_id where u.username = ? and p.password = ? and status = 'ACTUAL'";
			String destPage = "form.jsp";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				sql = "select p.expire_max from user u join password p on u.id = p.user_id where u.username = ? and p.password = ? and status = 'ACTUAL'";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, password);

				rs = preparedStatement.executeQuery();
				if (rs.next()) {
					System.out.println(rs.getDate(1));
					if (rs.getDate(1).after(new Date())) {
						
						request.setAttribute("username", request.getParameter("username"));
						request.setAttribute("password", request.getParameter("password"));
						request.getRequestDispatcher("/welcome.jsp").forward(request, response);
					} else {
						request.setAttribute("username", request.getParameter("username"));
						request.getRequestDispatcher("/password2.jsp").forward(request, response);
					}
				}

			} else {
				out.println("You are not valid");
			}

		} catch (Exception e) {
			System.out.println("The exception is" + e);

		}

	}

}
