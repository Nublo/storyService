package by.anatoldeveloper.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;

import by.anatoldeveloper.entity.Story;

public class QueryLoader {

	@Autowired
	BasicDataSource dataSource;

	public Story getStory(long id) throws Exception {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM story where id = " + id);
			rs.next();
			return new Story(rs.getInt(1), rs.getString(2));
		} catch (Exception e) {
			throw e;
		} finally {
			if (connection != null)
				connection.close();
		}
	}

}
