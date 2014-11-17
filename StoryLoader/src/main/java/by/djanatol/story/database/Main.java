package by.djanatol.story.database;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import by.djanatol.story.database.loader.DatabaseLoader;
import by.djanatol.storyloader.Story;

import com.google.gson.Gson;

public class Main {

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		DatabaseLoader loader = new DatabaseLoader();
		File[] listOfFiles = new File("stories/").listFiles();
		for(File file : listOfFiles) {
			byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
			Story[]	stories = new Story[1000];
			stories  = new Gson().fromJson(new String(encoded, Charset.defaultCharset()), stories.getClass());
			loader.insertValues(stories);
			System.out.println("File " + file.getName() + " loaded to DB");
		}
	}

}
