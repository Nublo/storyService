package by.anatoldeveloper.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Story {
	
	private long id;
	@JsonProperty("story_name")
	private String storyContent;
	
	public Story(long id, String storyContent) {
		this.id = id;
		this.storyContent = storyContent;
	}

	public long getId() {
		return id;
	}

	public String getStoryContent() {
		return storyContent;
	}

}
