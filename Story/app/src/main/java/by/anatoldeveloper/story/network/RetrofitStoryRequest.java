package by.anatoldeveloper.story.network;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import by.anatoldeveloper.story.model.Story;

/**
 * Created by Anatol on 26.12.2014.
 */
public class RetrofitStoryRequest extends RetrofitSpiceRequest<Story.StoryList, StoryRestServer> {

    private long start;
    private long end;

    public RetrofitStoryRequest(long start, long end) {
        super(Story.StoryList.class, StoryRestServer.class);
        this.start = start;
        this.end = end;
    }

    @Override
    public Story.StoryList loadDataFromNetwork() throws Exception {
        return getService().stories(start, end);
    }
}
