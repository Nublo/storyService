package by.anatoldeveloper.story.story.iterator;

import by.anatoldeveloper.story.data.StoryRepository;
import by.anatoldeveloper.story.model.Story;

/**
 * Created by Anatol on 07.03.2015.
 * Project Story
 */
public class NextAnyStory implements NextStory {
    @Override
    public Story nextStory(StoryRepository repository, int storyId) {
        return repository.getStoryById(storyId);
    }
}
