package by.anatoldeveloper.stories.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

import by.anatoldeveloper.stories.R;
import by.anatoldeveloper.stories.model.Story;

/**
 * Created by Anatol on 07.03.2015.
 * Project Story
 */
public class FavoriteStoryFragment extends BaseFragment {

    private static final String STORY_ID = "story_id";

    private int mCurrentStoryId;
    private TextView mTvStoryText;
    private ToggleButton mLikeButton;
    private ViewGroup mStoryLayout;

    public static FavoriteStoryFragment newInstance(int storyId) {
        FavoriteStoryFragment fragment = new FavoriteStoryFragment();
        Bundle args = new Bundle();
        args.putInt(STORY_ID, storyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_story, container, false);
        mCurrentStoryId = getArguments().getInt(STORY_ID);
        mTvStoryText = (TextView) rootView.findViewById(R.id.tv_story);
        mTvStoryText.setMovementMethod(new ScrollingMovementMethod());
        Button nextStory = (Button) rootView.findViewById(R.id.btn_next);
        nextStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStoryWithMinId(mCurrentStoryId + 1);
            }
        });
        mLikeButton = (ToggleButton) rootView.findViewById(R.id.tbn_like);
        mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean favorite = mLikeButton.isChecked();
                mRepository.updateFavoriteById(favorite, mCurrentStoryId);
                Story s = mRepository.getStoryById(mCurrentStoryId);
                Log.d("test", "Story after update : " + s.toString());
            }
        });
        mStoryLayout = (LinearLayout)rootView;
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        showStoryWithMinId(mCurrentStoryId);
    }

    private void showStoryWithMinId(int id) {
        Story s = mRepository.getFavoriteStory(id);
        showStory(s);
    }

    private void showStory(Story s) {
        if (s != null) {
            mTvStoryText.scrollTo(0, 0);
            mLikeButton.setChecked(s.favorite);
            mTvStoryText.setText(s.text);
            mCurrentStoryId = (int) s.id;
        } else {
            SnackbarManager.show(
                Snackbar.with(getActivity().getApplicationContext())
                    .text("У вас больше нету любимых историй")
                    .duration(Snackbar.SnackbarDuration.LENGTH_SHORT)
                , getActivity());
        }
    }

}