package by.anatoldeveloper.stories.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.afollestad.materialdialogs.MaterialDialog;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import by.anatoldeveloper.stories.BuildConfig;
import by.anatoldeveloper.stories.MainActivity;
import by.anatoldeveloper.stories.R;
import by.anatoldeveloper.stories.Utils;
import by.anatoldeveloper.stories.analytics.EventProducer;
import by.anatoldeveloper.stories.model.Story;
import by.anatoldeveloper.stories.network.RetrofitStoryRequest;
import by.anatoldeveloper.stories.persistence.Account;
import by.anatoldeveloper.stories.story.iterator.NextStory;
import by.anatoldeveloper.stories.story.iterator.NextStoryFabric;

/**
 * Created by Anatol on 11.12.2014.
 * Project Story
 */
public class StoryFragment extends BaseFragment {

    private static final int LOAD_SIZE = 100;

    private Account mAccount;
    private TextView mTvStoryText;
    private ProgressWheel mStoryLoading;
    private int mCurrentStory;
    private ToggleButton mLikeButton;
    private Button mStoryContent;
    private Boolean isLoading;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAccount = new Account();
        mAccount.restore(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_story, container, false);
        mTvStoryText = (TextView) rootView.findViewById(R.id.tv_story);
        mTvStoryText.setMovementMethod(new ScrollingMovementMethod());
        mStoryLoading = (ProgressWheel) rootView.findViewById(R.id.progressBar);

        Button nextStory = (Button) rootView.findViewById(R.id.btn_next);
        nextStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextStory();
            }
        });

        mLikeButton = (ToggleButton) rootView.findViewById(R.id.tbn_like);
        mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean favorite = mLikeButton.isChecked();
                mRepository.updateFavoriteById(favorite, mCurrentStory);
                if (BuildConfig.DEBUG) {
                    Story s = mRepository.getStoryById(mCurrentStory);
                    if (s != null) {
                        Utils.log("Story after update : " + s.toString());
                    }
                } // remove in release code
            }
        });
        mStoryContent = (Button) rootView.findViewById(R.id.btn_story_content);
        mStoryContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentTitle = mStoryContent.getText().toString();
                mStoryContent.setText(NextStoryFabric.getNextTitle(currentTitle));
            }
        });
        if (BuildConfig.CATEGORIES_ENABLED) {
            mStoryContent.setVisibility(View.VISIBLE);
        } else {
            mStoryContent.setVisibility(View.GONE);
        }

        mCurrentStory = mAccount.mStories;
        isLoading = false;

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onStart() {
        super.onStart();
        showNextStory();
    }

    private void loadNewStories() {
        if (isLoading) {
            return;
        }
        RetrofitStoryRequest storyRequest = new RetrofitStoryRequest(getCurrentRequest() * LOAD_SIZE+1,
                getCurrentRequest() * LOAD_SIZE + LOAD_SIZE);
        ((MainActivity) getActivity()).getSpiceManager().execute(storyRequest,
                "story:" + getCurrentRequest(),
                DurationInMillis.ONE_MINUTE * 5,
                new StoryRequestListener());
        startLoadingProcess();
    }

    private void startLoadingProcess() {
        mStoryLoading.setVisibility(View.VISIBLE);
        isLoading = true;
    }

    private void stopLoadingProcess() {
        mStoryLoading.setVisibility(View.GONE);
        isLoading = false;
    }

    private int getCurrentRequest() {
        return mCurrentStory / LOAD_SIZE;
    }

    private void showNextStory() {
        EventProducer.analyticsTrack(getActivity(), mLikeButton.isChecked(), mCurrentStory);
        NextStory next = NextStoryFabric.getNextStoryByKey(mStoryContent.getText().toString());
        Story s = next.nextStory(mRepository, mCurrentStory+1);
        if (s == null) {
            if (mCurrentStory % LOAD_SIZE != 0)
                mCurrentStory = (getCurrentRequest() + 1) * LOAD_SIZE;
            loadNewStories();
        } else {
            mTvStoryText.scrollTo(0, 0);
            mLikeButton.setChecked(s.favorite);
            mTvStoryText.setText(s.text);
            mCurrentStory = (int)s.id;
            Utils.log(s.toString());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mCurrentStory--;
        mAccount.mStories = mCurrentStory;
        mAccount.save(getActivity());
    }

    private final class StoryRequestListener implements RequestListener<Story.StoryList> {

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
                        showNextStory();
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
                new InsertDataTask().execute(result);
            }
        }
    }

    private final class InsertDataTask extends AsyncTask<List<Story>, Void, Void> {

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
            showNextStory();
        }
    }

}