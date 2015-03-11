package by.anatoldeveloper.story.network;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

/**
 * Created by Anatol on 07.03.2015.
 * Project Story
 */
public class RetrofitStoryService extends RetrofitGsonSpiceService {

    private final static String STORY_SERVER_URL = "http://storyservice.herokuapp.com";

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(StoryRestServer.class);
    }

    @Override
    protected String getServerUrl() {
        return STORY_SERVER_URL;
    }
}
