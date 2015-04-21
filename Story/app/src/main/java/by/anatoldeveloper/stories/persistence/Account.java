package by.anatoldeveloper.stories.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Anatol on 07.03.2015.
 * Project Story
 */
public class Account {

    private static final String STORY_READ = "stories";
    private static final String SEX_STORY_READ = "sex_stories";
    private static final String UNDO = "undo";
    private static final String USAGES = "usages";

    public int mStories;
    public int mSexStories;
    public int mUsages;
    public boolean mUndo;

    public void save(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        if (mStories >= 0)
            editor.putInt(STORY_READ, mStories);
        editor.putInt(SEX_STORY_READ, mSexStories);
        editor.putBoolean(UNDO, mUndo);
        editor.apply();
    }

    public void restore(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        mStories = prefs.getInt(STORY_READ, 0);
        mSexStories = prefs.getInt(SEX_STORY_READ, 0);
        mUndo = prefs.getBoolean(UNDO, true);
    }

    public void saveUsages(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("usage_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(USAGES, mUsages);
        editor.apply();
    }

    public void restoreUsages(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("usage_preferences", Context.MODE_PRIVATE);
        mUsages = prefs.getInt(USAGES, mUsages);
    }

    @Override
    public String toString() {
        return String.format("[stories = %d, sexStories = %d, usages = %d,  undo = %b]", mStories, mSexStories, mUsages, mUndo);
    }

}