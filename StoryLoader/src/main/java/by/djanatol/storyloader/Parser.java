package by.djanatol.storyloader;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
	
	public static List<Story> parse(Document document) {
		List<Story> stories = new ArrayList<Story>();
		
		Document doc = document;
		Elements htmlStories = doc.getElementsByClass("fi");
		for	(Element htmlStory : htmlStories) {
			Elements htmlText = htmlStory.getElementsByClass("fi_text");
			String story = htmlText.first().text();
			if (story.equals("18+")) {
				String storyLink = htmlText.select("a").first().attr("href");
				Loader loader = new Loader(storyLink);
				loader.load();
				List<String> tags = new ArrayList<String>();
				tags.add("18+");
				stories.add(new Story(loader.getStoriesList().get(0).getText(), tags));
				continue;
			}
			stories.add(new Story(htmlText.first().text(), null));
		}

		return stories;
	}
	
}
