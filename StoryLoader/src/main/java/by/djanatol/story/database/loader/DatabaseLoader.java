package by.djanatol.story.database.loader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import by.djanatol.storyloader.Story;

public class DatabaseLoader {
	
	private static final String INSERT_STATEMENT = "INSERT INTO story (story, site_id, sex) VALUES (?, 1, ?)";
	
	private Connection connection;
	
	public DatabaseLoader() throws ClassNotFoundException, SQLException {
		establishConnection();
	}
	
	public void establishConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		connection = DriverManager.getConnection(
				"jdbc:postgresql://127.0.0.1:5432/StoryDB", "postgres",
				"postgres");
		connection.setAutoCommit(false);
	}
	
	public void insertValues(Story[] stories) {
		try {
			for(Story s : stories) {
				PreparedStatement stmt = connection.prepareStatement(INSERT_STATEMENT);
				stmt.setString(1, s.getText());
				stmt.setBoolean(2, (s.getTags() == null) ? false : true);
				stmt.executeUpdate();
				connection.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
