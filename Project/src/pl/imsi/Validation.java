package pl.imsi;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Validation")
public class Validation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Validation() {
		super();
	}

	public void init() throws ServletException {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

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
						request.setAttribute("error", "Your account is blocked!");
					request.getRequestDispatcher("/form.jsp").forward(request, response);
				} else {

					b = database.checkIfIsPasswordCorrect(username, password);
					if (b == false) {

						id = database.getUserId(username);
						if (id.equals("0")) {
						} else {
							database.insertIntoLoginTest(id);
						}

						String count = database.countValid(id);

						if (count.equals("3")) {
							database.blockAccount(id);

							request.setAttribute("error", "Password is incorrect!\nYou have typed incorrect password "
									+ count + " times! Your account is blocked!");
							request.getRequestDispatcher("/form.jsp").forward(request, response);
						} else {
							request.setAttribute("error",
									"Password is incorrect!\nYou have typed incorrect password " + count + " times!");
							request.getRequestDispatcher("/form.jsp").forward(request, response);
						}
					} else {
						b = database.checkIfIsPasswordExpired(username, password);
						if (b) {
							request.setAttribute("username", request.getParameter("username"));
								request.setAttribute("error", "Your password is expired!");
							request.getRequestDispatcher("/password.jsp").forward(request, response);
						}

						else {
							id = database.getUserId(username);
							database.insertIntoTime(id);
							database.deleteFromLoginTest(id);
							request.setAttribute("username", request.getParameter("username"));
							request.getRequestDispatcher("welcome.jsp").forward(request, response);
						}

					}
				}
			}

			else {
				request.setAttribute("error", "There is no such user!");
				request.getRequestDispatcher("/form.jsp").forward(request, response);
			}

		} catch (Exception e) {
			System.out.println("The exception is" + e);

		}
	}
}
