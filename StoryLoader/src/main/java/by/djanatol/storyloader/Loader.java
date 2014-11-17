package by.djanatol.storyloader;

import java.io.IOException;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Loader {
	
	private static final String BASE_URL = "http://killpls.me";
	
	private String url;
	private Document document;
	
	public Loader(String url) {
		this.url = url;
	}
	
	public void load() {
		try {
			document = Jsoup.connect(BASE_URL + url).get();
		} catch (IOException e) {
			System.out.println("IOExcpetion has occured while retreving data from :" + this.url);
		}
	}
	
	public List<Story> getStoriesList() {
		return Parser.parse(document);
	}

}
