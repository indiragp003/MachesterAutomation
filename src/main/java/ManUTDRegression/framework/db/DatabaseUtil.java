package ManUTDRegression.framework.db;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;


public class DatabaseUtil {
	
	final static Logger log = LogManager.getLogger(DatabaseUtil.class);

	private static JdbcConnectionPool jdbcConnPool;

	static {
		// Create Data Source
		try {
			Class.forName("org.h2.Driver");
			System.out.println("Create datasource");
			JdbcDataSource ds = new JdbcDataSource();
			ds.setURL("jdbc:h2:mem:testdb");
			ds.setUser("");
			ds.setPassword("");
			// Create Connection Pool
			 jdbcConnPool = JdbcConnectionPool.create(ds);
			// Init Database with tables
			 try(Connection conn = jdbcConnPool.getConnection();
			     InputStream is = DatabaseUtil.class.getClassLoader().getResourceAsStream("scripts/init.sql");
				 InputStream datais = DatabaseUtil.class.getClassLoader().getResourceAsStream("scripts/data.sql");){
				 //Create Tables
				 RunScript.execute(conn, new InputStreamReader(is));
				 //Populate Web Element
				 RunScript.execute(conn, new InputStreamReader(datais));
			 } catch (IOException e) {
				e.printStackTrace();
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	public static Connection getConnection() throws SQLException {
		return jdbcConnPool.getConnection();
	}
}
