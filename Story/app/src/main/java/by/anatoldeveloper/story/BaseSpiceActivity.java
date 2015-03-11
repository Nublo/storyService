package by.anatoldeveloper.story;

import android.support.v7.app.ActionBarActivity;

import com.octo.android.robospice.SpiceManager;

import by.anatoldeveloper.story.data.DatabaseManager;
import by.anatoldeveloper.story.network.RetrofitStoryService;

/**
 * Created by Anatol on 07.03.2015.
 * Project Story
 */
public class BaseSpiceActivity extends ActionBarActivity {

    private SpiceManager spiceManager = new SpiceManager(RetrofitStoryService.class);
    private DatabaseManager databaseManager = new DatabaseManager();

    @Override
    protected void onStart() {
        spiceManager.start(this);
        databaseManager.getHelper(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        databaseManager.releaseHelper();
        super.onStop();
    }

    public SpiceManager getSpiceManager() {
        return spiceManager;
    }

}
