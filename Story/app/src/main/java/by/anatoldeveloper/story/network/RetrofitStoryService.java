package by.anatoldeveloper.story.network;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

/**
 * Created by Anatol on 26.12.2014.
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
