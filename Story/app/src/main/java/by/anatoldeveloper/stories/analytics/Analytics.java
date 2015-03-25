package by.anatoldeveloper.stories.analytics;

import android.app.Activity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import by.anatoldeveloper.stories.AnalyticsApplication;

/**
 * Created by Anatol on 23.03.2015.
 * Project Story
 */
public class Analytics {

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

    public static void trackStoryLike(Activity activity, boolean like, int story) {
        trackEvent(activity, "Stories", like ? "Like" : "Unlike", story+"");
    }

    public static void trackStory(Activity activity, boolean isLikedStory, int story) {
        if (story % 1000 == 0) {
            trackEventRead(activity, story);
        }
        if (isLikedStory) {
            trackStoryLike(activity, isLikedStory, story);
        }
    }

    public static void trackFavoriteStory(Activity activity, boolean isLikedStory, int story) {
        if (!isLikedStory) {
            trackStoryLike(activity, isLikedStory, story);
        }
    }

}