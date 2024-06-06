package perseverance;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GenericDAO {
	
	private Connection c;
	
	public GenericDAO() {
		super();
	}
	
	public Connection getConnection() throws SQLException, ClassNotFoundException{
		String hostName = "localhost";
		String dbName = "university_lib";
		String user = "root";
		String password ="root@Gabe";
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		c = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306/%s", hostName, dbName), user, password);
		
		return c;
	}

}
