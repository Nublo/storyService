package by.anatoldeveloper.stories;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

import com.afollestad.materialdialogs.MaterialDialog;

import by.anatoldeveloper.stories.persistence.Account;

/**
 * Created by Anatol on 21.04.2015.
 * Project Story
 */
public class RateAppHelper {

    private static final String MARKET_RATE_URI = "market://details?id=by.anatoldeveloper.stories&hl=ru";
    private static final String MARKET_DIRECT_LINK = "http://play.google.com/store/apps/details?id=by.anatoldeveloper.stories&hl=ru";
    private static final int DIALOG_SHOW = 100;
    private static final int APP_USAGES_TO_SHOW_DIALOG = 15;

    private Activity mActivity;
    private Account mAccount;

    public RateAppHelper(Activity activity) {
        this.mActivity = activity;
        mAccount = new Account();
        mAccount.restoreUsages(activity);
    }

    public void increaseAppUsage() {
        mAccount.mUsages += 1;
        mAccount.saveUsages(mActivity);
        if (mAccount.mUsages >= APP_USAGES_TO_SHOW_DIALOG) {
            new MaterialDialog.Builder(mActivity)
                    .title(R.string.rate_app_title)
                    .content(R.string.rate_app_content)
                    .positiveText(R.string.yes)
                    .negativeText(R.string.no)
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            rateApp(mActivity);
                        }
                        @Override
                        public void onNegative(MaterialDialog dialog) {
                            mActivity.onBackPressed();
                        }
                    })
                    .show();
            mAccount.mUsages += DIALOG_SHOW;
            mAccount.saveUsages(mActivity);
        } else {
            mActivity.finish();
        }
    }

    public boolean hasDialogBeenShowed() {
        return (mAccount.mUsages >= DIALOG_SHOW);
    }

    private void rateApp(Activity activity) {
        Uri uri = Uri.parse(MARKET_RATE_URI);
        Intent rateAppIntent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            activity.startActivity(rateAppIntent);
        } catch (ActivityNotFoundException e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_DIRECT_LINK)));
        }
    }

}