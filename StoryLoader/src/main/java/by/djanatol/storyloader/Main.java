package by.djanatol.storyloader;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		int range = 9;
		int start = 9;
		while (start > 0) {
			loadStoriesToFile(start, start-range);
			start-=10;
		}
	}
	
	public static void loadStoriesToFile(int start, int end) throws FileNotFoundException {
		List<Story> stories = new ArrayList<Story>();
		for (int i = start; i > end; i--) {
			Loader storyLoader = new Loader("/page/" + i);
			storyLoader.load();
			stories.addAll(storyLoader.getStoriesList());
		}
		PrintWriter out = new PrintWriter("stories/" + start + "-" + end +".txt");
		out.println(new Gson().toJson(stories).toString());
		out.close();
		System.out.println("Pages from " + start + " to " + end + " has been loaded");
	}

}
