package by.anatoldeveloper;

import java.net.URISyntaxException;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import by.anatoldeveloper.database.QueryLoader;

@Configuration
public class Config {
	
	@Bean
    public BasicDataSource dataSource() throws URISyntaxException {
        String dbURL = "jdbc:postgresql://127.0.0.1:5432/StoryDB";

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.postgresql.Driver");
        basicDataSource.setUrl(dbURL);
        basicDataSource.setUsername("postgres");
        basicDataSource.setPassword("postgres");

        return basicDataSource;
    }
	
	@Bean
	public QueryLoader queryLoader() {
		return new QueryLoader();
	}

}
