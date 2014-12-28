package by.anatoldeveloper.story.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Anatol on 27.12.2014.
 */
public class Account {

    private static final String STORY_READ = "stories";
    private static final String SEX_STORY_READ = "sex_stories";

    public int mStories;
    public int mSexStories;

    public void save(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(STORY_READ, mStories);
        editor.putInt(SEX_STORY_READ, mSexStories);
        editor.commit();
    }

    public void restore(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        mStories = prefs.getInt(STORY_READ, 1);
        mSexStories = prefs.getInt(SEX_STORY_READ, 0);
    }


}
