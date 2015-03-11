package by.anatoldeveloper.story.fragment;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.List;

import by.anatoldeveloper.story.BuildConfig;
import by.anatoldeveloper.story.MainActivity;
import by.anatoldeveloper.story.R;
import by.anatoldeveloper.story.Utils;
import by.anatoldeveloper.story.model.Story;
import by.anatoldeveloper.story.network.RetrofitStoryRequest;
import by.anatoldeveloper.story.persistence.Account;
import by.anatoldeveloper.story.story.iterator.NextStory;
import by.anatoldeveloper.story.story.iterator.NextStoryFabric;

/**
 * Created by Anatol on 11.12.2014.
 * Project Story
 */
public class StoryFragment extends BaseFragment {

    private static final int LOAD_SIZE = 100;

    private Account mAccount;
    private TextView mTvStoryText;
    private ProgressBar mStoryLoading;
    private int mCurrentStory;
    private ToggleButton mLikeButton;
    private Button mStoryContent;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAccount = new Account();
        mAccount.restore(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_story, container, false);
        mTvStoryText = (TextView) rootView.findViewById(R.id.tv_main_text);
        mTvStoryText.setMovementMethod(new ScrollingMovementMethod());
        mTvStoryText.setScrollbarFadingEnabled(false);
        mStoryLoading = (ProgressBar) rootView.findViewById(R.id.progressBar);

        Button nextStory = (Button) rootView.findViewById(R.id.btn_main_next);
        nextStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextStory();
            }
        });

        mLikeButton = (ToggleButton) rootView.findViewById(R.id.tbn_main_like);
        mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean favorite = mLikeButton.isChecked();
                mRepository.updateFavoriteById(favorite, mCurrentStory);
                Story s = mRepository.getStoryById(mCurrentStory);
                Utils.log("Story after update : " + s.toString());
            }
        });
        mStoryContent = (Button) rootView.findViewById(R.id.btn_story_content);
        mStoryContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStoryContent.getText().equals("All")) {
                    mStoryContent.setText("18+");
                } else if (mStoryContent.getText().equals("18+")) {
                    mStoryContent.setText("18-");
                } else if (mStoryContent.getText().equals("18-")){
                    mStoryContent.setText("All");
                }
            }
        });
        if (BuildConfig.CATEGORIES_ENABLED) {
            mStoryContent.setVisibility(View.VISIBLE);
        } else {
            mStoryContent.setVisibility(View.GONE);
        }

        mCurrentStory = mAccount.mStories;
        Utils.log("currentStory = " + mCurrentStory);

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
        RetrofitStoryRequest storyRequest = new RetrofitStoryRequest(getCurrentRequest() * LOAD_SIZE+1,
                getCurrentRequest() * LOAD_SIZE + LOAD_SIZE);
        ((MainActivity) getActivity()).getSpiceManager().execute(storyRequest,
                "story:" + getCurrentRequest(),
                DurationInMillis.ONE_MINUTE * 5,
                new StoryRequestListener());
        mStoryLoading.setVisibility(View.VISIBLE);
    }

    private int getCurrentRequest() {
        return mCurrentStory / LOAD_SIZE;
    }

    private void showNextStory() {
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
        mAccount.mStories = mCurrentStory-1;
        mAccount.save(getActivity());
    }

    private final class StoryRequestListener implements RequestListener<Story.StoryList> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            if (spiceException.getMessage().equalsIgnoreCase("Network is not available")) {
                Utils.log("network connection failed");
            } else {
                Utils.log("server is not responding");
            }
            mStoryLoading.setVisibility(View.GONE);
        }

        @Override
        public void onRequestSuccess(final Story.StoryList result) {
            new InsertDataTask().execute(result);
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
            mStoryLoading.setVisibility(View.GONE);
            Utils.log("success end");
            showNextStory();
        }
    }

}