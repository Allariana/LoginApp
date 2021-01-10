package pl.et.servlet;

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

/**
 * Servlet implementation class ChangePassword
 */
@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangePassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		String connectionURL = "jdbc:h2:tcp://localhost/~/test10";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String password = request.getParameter("new_password");
		String r_password = request.getParameter("repeat_password");
		response.setContentType("text/html");

		if (password.equals(r_password)) {
			try {
				Class.forName("org.h2.Driver");
				connection = DriverManager.getConnection(connectionURL, "sa", "");
				String sql1 = "SELECT ID FROM USER WHERE USERNAME = ?";
				preparedStatement = connection.prepareStatement(sql1);
				preparedStatement.setString(1, request.getParameter("username"));
				rs = preparedStatement.executeQuery();
				if (rs.next()) {

					System.out.println(rs.getString(1));
					String id = rs.getObject(1).toString();
					
					String sql = "INSERT into PASSWORD values ((VALUES NEXT VALUE FOR auto.number), ?,?, SYSDATE); ";

					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setString(1, id);
					preparedStatement.setString(2, password);

					preparedStatement.execute();

					request.getRequestDispatcher("/form.jsp").forward(request, response);
				}

			} catch (Exception e) {
				System.out.println("The exception is" + e);

			}
		}else out.println("Different passwords!");
	}
}
