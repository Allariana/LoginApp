package pl.imsi;

import java.io.IOException;
import java.io.PrintWriter;
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
		Database database = new Database();
		Boolean b = false;
		PrintWriter out = response.getWriter();
		String password = request.getParameter("new_password");
		String r_password = request.getParameter("repeat_password");
		response.setContentType("text/html");

		try {
			String id = database.getUserId(request.getParameter("username"));

			b = database.checkIfIsPasswordMinimumIntervalPassed(id);

			if (b == false) {
				request.setAttribute("error", "Password not passed the minimum validity interval!");
				request.getRequestDispatcher("/password.jsp").forward(request, response);
			} else if (password.equals(r_password)) {

				b = database.checkIfIsUserHadThisPassword(id, password);
				if (b) {
					request.setAttribute("error", "You have used this password earlier!");
					request.getRequestDispatcher("/password.jsp").forward(request, response);
				} else {
					if (password.length() < 8) {
						request.setAttribute("error", "Password is too short! Password must have at least 8 letters!");
					request.getRequestDispatcher("/password.jsp").forward(request, response);
					}
					else {
						String regex = "^(?=.*[a-z])(?=." + "*[A-Z])(?=.*\\d)" + "(?=.*[-+_!@#$%^&*., ?]).+$";
						Pattern p = Pattern.compile(regex);
						Matcher m = p.matcher(password);
						if (m.matches()) {
							database.changePassword(id, password);
							database.updateLogoutTime(id);
							request.getRequestDispatcher("/form.jsp").forward(request, response);
						} else {
							request.setAttribute("error", "Password does not meet the complexity requirement!"
									+ " Password must contain uppercase, lowercase, special character and numeric value.");
							request.getRequestDispatcher("/password.jsp").forward(request, response);
						}
					}
				}
			} else {
				request.setAttribute("error", "Different passwords!");
				request.getRequestDispatcher("/password.jsp").forward(request, response);
			}
		} catch (Exception e) {
			System.out.println("The exception is" + e);

		}
	}

}