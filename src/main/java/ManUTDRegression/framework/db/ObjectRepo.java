package ManUTDRegression.framework.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ObjectRepo {
	
	final static Logger log = LogManager.getLogger(ObjectRepo.class);
	
	public String readPropLoc(String webElementName) {
		String sql = "SELECT web_element_type AS  WebElementLocator FROM web_element_data wed WHERE web_element_property = ?";
		String locator = "";
		try (Connection conn = DatabaseUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, webElementName);
			try (ResultSet rs = ps.executeQuery();) {
				while(rs.next()) {
					locator = rs.getString("WebElementLocator");
				}
			}
		} catch (Exception e) {
			log.error("Error in fetching ::",e);
		}
		return locator;
	}

	public String readProperty(String webElementName) {
		String sql = "SELECT web_element_value AS  ElementProperty FROM web_element_data wed WHERE web_element_property = ?";
		String locator = "";
		try (Connection conn = DatabaseUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, webElementName);
			try (ResultSet rs = ps.executeQuery();) {
				while(rs.next()) {
					locator = rs.getString("ElementProperty");
				}
			}
		} catch (Exception e) {
			log.error("Error in fetching ::",e);
		}
		return locator;
	}
}

