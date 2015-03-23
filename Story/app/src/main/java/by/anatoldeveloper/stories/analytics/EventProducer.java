package by.anatoldeveloper.stories.analytics;

import android.app.Activity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import by.anatoldeveloper.stories.AnalyticsApplication;

/**
 * Created by Anatol on 23.03.2015.
 * Project Story
 */
public class EventProducer {

    private static void trackEvent(Activity activity, String category, String action, String label) {
        Tracker t = ((AnalyticsApplication) activity.getApplication()).getTracker(
                AnalyticsApplication.TrackerName.APP_TRACKER);
        t.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label)
                .build());
    }

    public static void trackEventRead(Activity activity, int storiesRead) {
        trackEvent(activity, "Stories", "Read", storiesRead+"");
    }

    public static void trackStoryLike(Activity activity, int story) {
        trackEvent(activity, "Stories", "Like", story+"");
    }

    public static void analyticsTrack(Activity activity, boolean isLikedStory, int story) {
        if (story % 1000 == 0) {
            trackEventRead(activity, story);
        }
        if (isLikedStory) {
            trackStoryLike(activity, story);
        }
    }

}