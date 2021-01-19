package pl.imsi;

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
		Database database = new Database();
		Boolean b = false;
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		response.setContentType("text/html");
		String id;

		try {
			b = database.checkIfUserExists(username);
			if (b) {
				b = database.checkIfUserIsBlocked(username);
				if (b) {
					if (b)
						out.print("Your account is blocked!");
				} else {

					b = database.checkIfIsPasswordCorrect(username, password);
					if (b == false) {
						out.println("Password is incorrect!");
						id = database.getUserId(username);
						if (id.equals("0")) {
						} else {
							database.insertIntoLoginTest(id);
						}

						String count = database.countValid(id);
						out.println("You have typed incorrect password " + count + " times!");
						if (count.equals("3")) {
							database.blockAccount(id);
							out.println("Your account is blocked!");
						}

					} else {
						b = database.checkIfIsPasswordExpired(username, password);
						if (b) {
							request.setAttribute("username", request.getParameter("username"));
							request.getRequestDispatcher("/password2.jsp").forward(request, response);
						}

						else {
							id = database.getUserId(username);
							database.insertIntoTime(id);
							database.deleteFromLoginTest(id);
							request.setAttribute("username", request.getParameter("username"));
							request.getRequestDispatcher("/welcome.jsp").forward(request, response);
						}

					}
				}
			}

			else {
				out.println("There is no such user!");
			}

		} catch (Exception e) {
			System.out.println("The exception is" + e);

		}
	}
}
