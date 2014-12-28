package by.anatoldeveloper.story;

import android.support.v7.app.ActionBarActivity;

import com.octo.android.robospice.SpiceManager;

import by.anatoldeveloper.story.network.RetrofitStoryService;

/**
 * Created by Anatol on 27.12.2014.
 */
public class BaseSpiceActivity extends ActionBarActivity {

    private SpiceManager spiceManager = new SpiceManager(RetrofitStoryService.class);

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    protected SpiceManager getSpiceManager() {
        return spiceManager;
    }

}
