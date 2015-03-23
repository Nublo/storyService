package by.anatoldeveloper.stories;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

/**
 * Created by Anatol on 23.03.2015.
 * Project Story
 */
public class AnalyticsApplication extends Application {

    private static final String PROPERTY_ID = "UA-61115112-1";
    public static int GENERAL_TRACKER = 0;
    public HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public AnalyticsApplication() {
        super();
    }

    public synchronized Tracker getTracker(TrackerName appTracker) {
        if (!mTrackers.containsKey(appTracker)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = (appTracker == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID) : null;
            mTrackers.put(appTracker, t);
        }
        return mTrackers.get(appTracker);
    }

    public enum TrackerName {
        APP_TRACKER
    }

}