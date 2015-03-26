package by.anatoldeveloper.stories.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import by.anatoldeveloper.stories.MainActivity;
import by.anatoldeveloper.stories.R;
import by.anatoldeveloper.stories.Utils;
import by.anatoldeveloper.stories.analytics.Analytics;
import by.anatoldeveloper.stories.model.Story;
import by.anatoldeveloper.stories.network.RetrofitStoryRequest;
import by.anatoldeveloper.stories.persistence.Account;
import by.anatoldeveloper.stories.story.iterator.NextStory;
import by.anatoldeveloper.stories.story.iterator.NextStoryFabric;

/**
 * Created by Anatol on 11.12.2014.
 * Project Story
 */
public class StoryFragment extends BaseStoryFragment {

    private static final int LOAD_SIZE = 100;

    private Account mAccount;
    private ProgressWheel mStoryLoading;
    private Boolean isLoading = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAccount = new Account();
        mAccount.restore(activity);
        mCurrentStory = mAccount.mStories;
    }

    @Override
    protected void initView(View rootView) {
        mStoryLoading = (ProgressWheel) rootView.findViewById(R.id.progressBar);
        Button nextStory = (Button) rootView.findViewById(R.id.btn_next);
        nextStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAnimating) {
                    Analytics.trackStory(getActivity(), mLikeButton.isChecked(), mCurrentStory);
                    showStoryWithMinId(mCurrentStory + 1, true);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onStart() {
        super.onStart();
        showStoryWithMinId(mCurrentStory, false);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAccount.mStories = mCurrentStory;
        mAccount.save(getActivity());
    }

    private void loadNewStories(boolean withAnimation) {
        RetrofitStoryRequest storyRequest = new RetrofitStoryRequest(getCurrentRequest() * LOAD_SIZE+1,
                getCurrentRequest() * LOAD_SIZE + LOAD_SIZE);
        ((MainActivity) getActivity()).getSpiceManager().execute(storyRequest, "story:" + getCurrentRequest(),
                DurationInMillis.ONE_MINUTE * 5, new StoryRequestListener(withAnimation));
        startLoadingProcess();
    }

    private int getCurrentRequest() {
        return mCurrentStory / LOAD_SIZE;
    }

    private void showStoryWithMinId(int id, boolean withAnimation) {
        NextStory next = NextStoryFabric.anyStory();
        Story s = next.nextStory(mRepository, id);
        if (s != null && withAnimation) {
            showStoryWithAnimation(s);
        } else if (s != null) {
            showStory(s);
        } else if (!isLoading) {
            if (mCurrentStory % LOAD_SIZE != 0) {
                mCurrentStory = (getCurrentRequest() + 1) * LOAD_SIZE;
            }
            loadNewStories(withAnimation);
        }
    }

    private void startLoadingProcess() {
        mStoryLoading.setVisibility(View.VISIBLE);
        isLoading = true;
    }

    private void stopLoadingProcess() {
        mStoryLoading.setVisibility(View.GONE);
        isLoading = false;
    }

    private final class StoryRequestListener implements RequestListener<Story.StoryList> {

        private boolean withAnimation;

        public StoryRequestListener(boolean withAnimation) {
            this.withAnimation = withAnimation;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_title)
                .content(Utils.getError(spiceException))
                .positiveText(R.string.yes)
                .negativeText(R.string.no)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        showStoryWithMinId(mCurrentStory + 1, withAnimation);
                    }
                })
                .show();
            stopLoadingProcess();
        }

        @Override
        public void onRequestSuccess(final Story.StoryList result) {
            if (result.size() == 0) {
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.dialog_title)
                        .content(R.string.no_more_stories)
                        .positiveText(R.string.ok)
                        .show();
            } else {
                new InsertDataTask(withAnimation).execute(result);
            }
        }
    }

    private final class InsertDataTask extends AsyncTask<List<Story>, Void, Void> {

        private boolean withAnimation;

        public InsertDataTask(boolean withAnimation) {
            this.withAnimation = withAnimation;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Story>... params) {
            List<Story> stories = params[0];
            Utils.log("success start, size = " + stories.size());
            for(Story s : stories) {
                mRepository.create(s);
            }
            mRepository.deleteNotFavoriteStoriesWithMinId(mCurrentStory);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            stopLoadingProcess();
            Utils.log("success end");
            showStoryWithMinId(mCurrentStory+1, withAnimation);
        }
    }

}