import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class sqlConnector {

	private final static String URL = "jdbc:mysql://localhost:3306/noppri_belajar?useTimezone=true&serverTimezone=UTC";
	private final static String USER = "root";
	private final static String PASS = "";
	
	public static Connection connection() {
		Connection conn = null;
		try {
			//Driver manager tu fungsinya panggil driver yg connect ke mysql
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			//connection hubungin dengan URL, USER, PASSWORD di mysql
			conn = DriverManager.getConnection(URL, USER, PASS);
//			System.out.println("Success connection");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
}
