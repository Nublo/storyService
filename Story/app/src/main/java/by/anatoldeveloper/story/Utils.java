package by.anatoldeveloper.story;

import android.util.Log;

/**
 * Created by Anatol on 11.03.2015.
 * Project Story
 */
public class Utils {

    private static final String LOG_TAG = "story.log";

    public static void log(String message) {
        if (BuildConfig.DEBUG)
            Log.d(LOG_TAG, message);
    }

}