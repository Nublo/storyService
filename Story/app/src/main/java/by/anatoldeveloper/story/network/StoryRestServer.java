package by.anatoldeveloper.story.network;

import by.anatoldeveloper.story.model.Story;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Anatol on 26.12.2014.
 */
public interface StoryRestServer {

    @GET("/story")
    public Story.StoryList stories(@Query("start") long start, @Query("end") long end);

    @GET("/story/sex")
    public Story.StoryList sexStories(@Query("page") long page, @Query("limit") long limit);
}
