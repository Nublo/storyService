package by.anatoldeveloper.stories.story.iterator;

import by.anatoldeveloper.stories.data.StoryRepository;
import by.anatoldeveloper.stories.model.Story;

/**
 * Created by Anatol on 07.03.2015.
 * Project Story
 */
public class NextStoryById implements NextStory {
    @Override
    public Story nextStory(StoryRepository repository, int storyId) {
        return repository.getStoryById(storyId);
    }
}
