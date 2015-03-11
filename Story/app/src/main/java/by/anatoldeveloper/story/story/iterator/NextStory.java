package by.anatoldeveloper.story.story.iterator;

import by.anatoldeveloper.story.data.StoryRepository;
import by.anatoldeveloper.story.model.Story;

/**
 * Created by Anatol on 07.03.2015.
 * Project Story
 */
public interface NextStory {

    public Story nextStory(StoryRepository repository, int storyId);

}