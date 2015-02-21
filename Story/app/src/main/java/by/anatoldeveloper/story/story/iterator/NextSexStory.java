package by.anatoldeveloper.story.story.iterator;

import by.anatoldeveloper.story.data.StoryRepository;
import by.anatoldeveloper.story.model.Story;

/**
 * Created by Anatol on 21.02.2015.
 */
public class NextSexStory implements NextStory {
    @Override
    public Story nextStory(StoryRepository repository, int storyId) {
        return repository.getSexStory(storyId);
    }
}
