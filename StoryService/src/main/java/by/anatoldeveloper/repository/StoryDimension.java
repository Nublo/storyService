package by.anatoldeveloper.repository;

public class StoryDimension {

	private long stories;
	private long sexStories;
	
	public StoryDimension() {
		
	}
	
	public StoryDimension(long stories, long sexStories) {
		this.stories = stories;
		this.sexStories = sexStories;
	}

	public long getStories() {
		return stories;
	}

	public void setStories(long stories) {
		this.stories = stories;
	}

	public long getSexStories() {
		return sexStories;
	}

	public void setSexStories(long sexStories) {
		this.sexStories = sexStories;
	}

	
	
}
