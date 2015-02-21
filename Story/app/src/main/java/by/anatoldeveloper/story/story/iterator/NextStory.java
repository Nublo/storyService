package by.anatoldeveloper.story.story.iterator;

import by.anatoldeveloper.story.data.StoryRepository;
import by.anatoldeveloper.story.model.Story;

/**
 * Created by Anatol on 21.02.2015.
 */
public interface NextStory {

    public Story nextStory(StoryRepository repository, int storyId);

}