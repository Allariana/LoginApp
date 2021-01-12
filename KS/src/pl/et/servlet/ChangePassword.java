package pl.et.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.regex.*;

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

		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(connectionURL, "sa", "");
			String sql1 = "SELECT ID FROM USER WHERE USERNAME = ?";
			preparedStatement = connection.prepareStatement(sql1);
			preparedStatement.setString(1, request.getParameter("username"));
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				String id = rs.getObject(1).toString();
				sql1 = "select expire_min from password where user_id = ? and status = 'ACTUAL'";
				preparedStatement = connection.prepareStatement(sql1);
				preparedStatement.setString(1, id);
				rs = preparedStatement.executeQuery();

				if (rs.next()) {
					if (rs.getDate(1).after(new Date())) {
						out.println("Password not passed the minimum validity interval!");
					} else if (password.equals(r_password)) {

						sql1 = "select * from password where user_id = ? and password = ?";
						preparedStatement = connection.prepareStatement(sql1);
						preparedStatement.setString(1, id);
						preparedStatement.setString(2, password);
						rs = preparedStatement.executeQuery();
						if (rs.next()) {
							out.println("You have used this password earlier!");
						} else {
							if (password.length() < 8)
								out.println("Password is too short! Password must have at least 8 letters!");

							else {
								String regex = "^(?=.*[a-z])(?=." + "*[A-Z])(?=.*\\d)" + "(?=.*[-+_!@#$%^&*., ?]).+$";
								Pattern p = Pattern.compile(regex);
								Matcher m = p.matcher(password);
								if (m.matches()) {
									String sql2 = "UPDATE PASSWORD SET STATUS='OLD' WHERE USER_ID = ? AND STATUS = 'ACTUAL' ";
									preparedStatement = connection.prepareStatement(sql2);
									preparedStatement.setString(1, id);
									preparedStatement.execute();

									String sql = "INSERT into PASSWORD values ((VALUES NEXT VALUE FOR auto.number), ?,?, SYSDATE, SYSDATE+1, SYSDATE+30, 'ACTUAL'); ";
									preparedStatement = connection.prepareStatement(sql);
									preparedStatement.setString(1, id);
									preparedStatement.setString(2, password);
									preparedStatement.execute();

									request.getRequestDispatcher("/form.jsp").forward(request, response);
								} else {
									out.println("Password does not meet the complexity requirement!");
									out.println(
											"Password must contain uppercase, lowercase, special character and numeric value.");
								}
							}
						}
					} else
						out.println("Different passwords!");
				}
			}

		} catch (Exception e) {
			System.out.println("The exception is" + e);

		}
	}

}
