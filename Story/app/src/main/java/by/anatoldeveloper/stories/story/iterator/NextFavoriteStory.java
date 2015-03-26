package by.anatoldeveloper.stories.story.iterator;

import by.anatoldeveloper.stories.data.StoryRepository;
import by.anatoldeveloper.stories.model.Story;

/**
 * Created by Anatol on 26.03.2015.
 * Project Story
 */
public class NextFavoriteStory implements NextStory {
    @Override
    public Story nextStory(StoryRepository repository, int storyId) {
        return repository.getFavoriteStory(storyId);
    }
}
