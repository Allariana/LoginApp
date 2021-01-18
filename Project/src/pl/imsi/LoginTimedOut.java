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
		PrintWriter out = response.getWriter();
		String connectionURL = "jdbc:h2:tcp://localhost/~/test10";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		response.setContentType("text/html");

		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(connectionURL, "sa", "");
			String sql = "SELECT ID FROM USER WHERE USERNAME = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, request.getParameter("username"));
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				String id = rs.getObject(1).toString();
				sql = "select logintimeout from time where user_id = ? and status = 'ACTUAL'";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, id);
				rs = preparedStatement.executeQuery();

				if (rs.next()) {

					String sql2 = "UPDATE TIME SET LOGINTIMEOUT=SYSDATE WHERE USER_ID = ? AND STATUS = 'ACTUAL' ";
					preparedStatement = connection.prepareStatement(sql2);
					preparedStatement.setString(1, id);
					preparedStatement.execute();

					String sql1 = "UPDATE TIME SET STATUS='OLD' WHERE USER_ID = ? AND STATUS = 'ACTUAL' ";
					preparedStatement = connection.prepareStatement(sql1);
					preparedStatement.setString(1, id);
					preparedStatement.execute();
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
