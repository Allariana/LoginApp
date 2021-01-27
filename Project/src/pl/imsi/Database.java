package pl.imsi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Database {
	String connectionURL = "jdbc:h2:tcp://localhost/~/test10";
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;

	public boolean checkIfUserExists(String username) throws SQLException {
		boolean b = false;

		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(connectionURL, "sa", "");
			String sql = "select * from user where username = ?;";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				b = true;
			}

		} catch (Exception e) {
			System.out.println("The exception is" + e);

		}
		return b;
	}

	public boolean checkIfUserIsBlocked(String username) throws SQLException {
		boolean b = false;

		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(connectionURL, "sa", "");
			String sql = "select b.date_end from block b join user u on b.user_id =u.id where u.username = ? order by b.date_start desc";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				if (rs.getDate(1).after(new Date())) {
					b = true;
				}
			}

		} catch (Exception e) {
			System.out.println("The exception is" + e);

		}
		return b;
	}

	public boolean checkIfIsPasswordCorrect(String username, String password) throws SQLException {
		boolean b = false;

		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(connectionURL, "sa", "");
			String sql = "select * from user u join password p on u.id = p.user_id where u.username = ? and p.password = ? and p.date_end is null";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				b = true;
			}

		} catch (Exception e) {
			System.out.println("The exception is" + e);

		}
		return b;
	}
	public boolean checkIfIsUserHadThisPassword(String id, String password) throws SQLException {
		boolean b = false;

		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(connectionURL, "sa", "");
			String sql1 = "select * from password where user_id = ? and password = ?";
			preparedStatement = connection.prepareStatement(sql1);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, password);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				b = true;
			}

		} catch (Exception e) {
			System.out.println("The exception is" + e);

		}
		return b;
	}
	public boolean selectTimeOut(String id) throws SQLException {
		boolean b = false;

		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(connectionURL, "sa", "");
			String sql = "select LOGINTIMEOUT from LOGINTIME where user_id = ? AND LOGINTIMEOUT is null";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				b = true;
			}

		} catch (Exception e) {
			System.out.println("The exception is" + e);

		}
		return b;
	}

	public String getUserId(String username) throws SQLException {
		String id = "0";

		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(connectionURL, "sa", "");
			String sql = "SELECT ID FROM USER WHERE USERNAME = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				id = rs.getObject(1).toString();
			}

		} catch (Exception e) {
			System.out.println("The exception is" + e);

		}
		return id;
	}

	public void insertIntoLoginTest(String id) throws SQLException {

		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(connectionURL, "sa", "");
			String sql = "INSERT INTO LOGINTEST VALUES ((VALUES NEXT VALUE FOR auto.number),?);";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			preparedStatement.execute();

		} catch (Exception e) {
			System.out.println("The exception is" + e);

		}

	}

	public String countValid(String id) throws SQLException {
		String count = "0";

		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(connectionURL, "sa", "");
			String sql = "SELECT COUNT(*) FROM LOGINTEST WHERE USER_ID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				count = rs.getObject(1).toString();
			}

		} catch (Exception e) {
			System.out.println("The exception is" + e);

		}
		return count;
	}

	public void blockAccount(String id) throws SQLException {

		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(connectionURL, "sa", "");
			String sql = "DELETE FROM LOGINTEST WHERE USER_ID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			preparedStatement.execute();
			sql = "INSERT INTO BLOCK VALUES((VALUES NEXT VALUE FOR auto.number), ?,parsedatetime(sysdate,'yyyy-MM-dd hh:mm:ss'), parsedatetime(sysdate + 1,'yyyy-MM-dd hh:mm:ss'));";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			preparedStatement.execute();

		} catch (Exception e) {
			System.out.println("The exception is" + e);

		}

	}
	public void deleteFromLoginTest(String id) throws SQLException {

		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(connectionURL, "sa", "");
			String sql = "DELETE FROM LOGINTEST WHERE USER_ID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			preparedStatement.execute();

		} catch (Exception e) {
			System.out.println("The exception is" + e);

		}

	}
	public boolean checkIfIsPasswordExpired(String username, String password) throws SQLException {
		boolean b = false;

		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(connectionURL, "sa", "");
			String sql = "select p.expire_max from user u join password p on u.id = p.user_id where u.username = ? and p.password = ? and p.date_end is null";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				if (rs.getDate(1).before(new Date())) {
					b = true;
				}
			}

		} catch (Exception e) {
			System.out.println("The exception is" + e);

		}
		return b;
	}
	public boolean checkIfIsPasswordMinimumIntervalPassed(String id) throws SQLException {
		boolean b = false;

		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(connectionURL, "sa", "");
			String sql = "select expire_min from password where user_id = ? and date_end is null";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				if (rs.getDate(1).before(new Date())) {
					b = true;
				}
			}

		} catch (Exception e) {
			System.out.println("The exception is" + e);

		}
		return b;
	}
	public void insertIntoTime(String id) throws SQLException {

		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(connectionURL, "sa", "");
			String sql = "INSERT into LOGINTIME(ID,USER_ID, LOGINTIME, LOGINTIMEOUT) values ((VALUES NEXT VALUE FOR auto.number),?, parsedatetime(sysdate,'yyyy-MM-dd hh:mm:ss'), null)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			preparedStatement.execute();

		} catch (Exception e) {
			System.out.println("The exception is" + e);

		}

	}
	public void updateLogoutTime(String id) throws SQLException {

		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(connectionURL, "sa", "");
			String sql2 = "UPDATE LOGINTIME SET LOGINTIMEOUT=parsedatetime(sysdate,'yyyy-MM-dd hh:mm:ss') WHERE USER_ID = ? AND LOGINTIMEOUT is null ";
			preparedStatement = connection.prepareStatement(sql2);
			preparedStatement.setString(1, id);
			preparedStatement.execute();

		} catch (Exception e) {
			System.out.println("The exception is" + e);

		}

	}
	public void changePassword(String id, String password) throws SQLException {

		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection(connectionURL, "sa", "");
			String sql2 = "UPDATE PASSWORD SET DATE_END=sysdate WHERE USER_ID = ? AND DATE_END is null ";
			preparedStatement = connection.prepareStatement(sql2);
			preparedStatement.setString(1, id);
			preparedStatement.execute();

			String sql = "INSERT into PASSWORD values ((VALUES NEXT VALUE FOR auto.number), ?,?, SYSDATE, null, SYSDATE+1, SYSDATE+30); ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, password);
			preparedStatement.execute();

		} catch (Exception e) {
			System.out.println("The exception is" + e);

		}

	}

}
