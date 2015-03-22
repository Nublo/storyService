package by.anatoldeveloper.stories;

import android.app.Activity;
import android.util.Log;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by Anatol on 11.03.2015.
 * Project Story
 */
public class Utils {

    private static final String NO_CONNECTION = "Network is not available";
    private static final String LOG_TAG = "story.log";

    public static void log(String message) {
        if (BuildConfig.DEBUG)
            Log.d(LOG_TAG, message);
    }

    public static void showSnackbar(final Activity activity, int resourceId) {
        Snackbar bar = Snackbar.with(activity)
                .type(SnackbarType.SINGLE_LINE)
                .duration(Snackbar.SnackbarDuration.LENGTH_SHORT)
                .text(activity.getString(resourceId))
                .textColor(activity.getResources().getColor(R.color.accent));
        SnackbarManager.show(bar);
    }

    public static int getError(SpiceException spiceException) {
        if (spiceException.getMessage().equalsIgnoreCase(NO_CONNECTION)) {
            Utils.log("network connection failed");
            return R.string.no_connection_error;
        } else {
            Utils.log("server is not responding");
            return R.string.server_error;
        }
    }

}