package by.anatoldeveloper.stories.network;

import by.anatoldeveloper.stories.model.Story;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Anatol on 07.03.2015.
 * Project Story
 */
public interface StoryRestServer {

    @GET("/story")
    public Story.StoryList stories(@Query("start") long start, @Query("end") long end);

    @GET("/story/sex")
    public Story.StoryList sexStories(@Query("page") long page, @Query("limit") long limit);
}
