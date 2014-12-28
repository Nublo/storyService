package by.anatoldeveloper.story;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import by.anatoldeveloper.story.model.Story;
import by.anatoldeveloper.story.network.RetrofitStoryRequest;
import by.anatoldeveloper.story.persistence.Account;

/**
 * Created by Anatol on 11.12.2014.
 */
public class StoryFragment extends Fragment {

    private static final int LOAD_SIZE = 100;

    private TextView mTvStoryText;
    private ProgressBar mStoryLoading;
    private Story.StoryList mStories;
    private Account mAccount;
    private int mCurrentStory;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAccount = new Account();
        mAccount.restore(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
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

        mStories = new Story.StoryList();
        mCurrentStory = mAccount.mStories;

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mStories.size() == 0) {
            loadNewStories();
        }
    }

    public void loadNewStories() {
        RetrofitStoryRequest storyRequest = new RetrofitStoryRequest(getCurrentRequest() * LOAD_SIZE+1,
                getCurrentRequest() * LOAD_SIZE + LOAD_SIZE);
        ((MainActivity) getActivity()).getSpiceManager().execute(storyRequest,
                "story:" + getCurrentRequest(),
                DurationInMillis.ONE_MINUTE * 5,
                new StoryRequestListener());
        mStoryLoading.setVisibility(View.VISIBLE);
    }

    private int getCurrentRequest() {
        return (mCurrentStory-1) / LOAD_SIZE;
    }

    private int getCurrentStory() {
        return (mCurrentStory-1) % LOAD_SIZE;
    }

    private void showNextStory() {
        if (mStories.size() == 0 || (getCurrentStory() == 0 && mStories.get(getCurrentStory()).id != mCurrentStory)) {
            loadNewStories();
            return;
        }
        mTvStoryText.scrollTo(0, 0);
        mTvStoryText.setText(mStories.get(getCurrentStory()).text);
        mCurrentStory++;
    }

    @Override
    public void onStop() {
        super.onStop();
        mAccount.mStories = mCurrentStory-1;
        mAccount.save(getActivity());
    }

    public final class StoryRequestListener implements RequestListener<Story.StoryList> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            mStoryLoading.setVisibility(View.GONE);
        }

        @Override
        public void onRequestSuccess(final Story.StoryList result) {
            mStoryLoading.setVisibility(View.GONE);
            mStories = result;
            showNextStory();
        }
    }

}