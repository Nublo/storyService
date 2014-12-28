package by.anatoldeveloper.story.model;

import java.util.ArrayList;

/**
 * Created by Anatol on 26.12.2014.
 */
public class Story {

    public long id;
    public String text;
    public boolean sex;
    public Site site;

    public static class StoryList extends ArrayList<Story> {

    }

    public static class Site {
        public String name;
    }
}
