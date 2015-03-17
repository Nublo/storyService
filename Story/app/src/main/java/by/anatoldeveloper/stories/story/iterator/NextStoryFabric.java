package by.anatoldeveloper.stories.story.iterator;

/**
 * Created by Anatol on 07.03.2015.
 * Project Story
 */
public class NextStoryFabric {

    public static NextStory getNextStoryByKey(String key) {
        if (key.equals("All"))
            return new NextAnyStory();
        if (key.equals("18+"))
            return new NextSexStory();
        if (key.equals("18-"))
            return new NextPublicStory();
        return new NextAnyStory();
    }

}
