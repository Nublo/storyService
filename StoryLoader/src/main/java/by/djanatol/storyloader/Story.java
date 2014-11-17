package by.djanatol.storyloader;

import java.util.List;

public class Story {
	
	private String text;
	private List<String> tags;
	
	public Story(String text, List<String> tags) {
		this.text = text;
		this.tags = tags;
	}

	public String getText() {
		return text;
	}

	public List<String> getTags() {
		return tags;
	}
	
}
