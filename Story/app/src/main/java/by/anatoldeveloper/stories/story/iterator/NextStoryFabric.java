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

    public static String getNextTitle(String currentTitle) {
        if ("All".equals(currentTitle)) {
            return "18+";
        } else if ("18+".equals(currentTitle)) {
            return "18-";
        } else if ("18-".equals(currentTitle)){
            return "All";
        }
        return "All";
    }

}
