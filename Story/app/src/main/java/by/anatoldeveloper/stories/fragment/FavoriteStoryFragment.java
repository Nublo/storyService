package by.anatoldeveloper.stories.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import by.anatoldeveloper.stories.BuildConfig;
import by.anatoldeveloper.stories.R;
import by.anatoldeveloper.stories.Utils;
import by.anatoldeveloper.stories.model.Story;

/**
 * Created by Anatol on 07.03.2015.
 * Project Story
 */
public class FavoriteStoryFragment extends BaseFragment {

    private static final String STORY_ID = "story_id";

    private int mCurrentStory;
    private TextView mTvStoryText;
    private ToggleButton mLikeButton;

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
        mCurrentStory = getArguments().getInt(STORY_ID);
        mTvStoryText = (TextView) rootView.findViewById(R.id.tv_story);
        mTvStoryText.setMovementMethod(new ScrollingMovementMethod());
        Button nextStory = (Button) rootView.findViewById(R.id.btn_next);
        nextStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStoryWithMinId(mCurrentStory + 1);
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
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        showStoryWithMinId(mCurrentStory);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.favorite_stories);
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
            mCurrentStory = (int) s.id;
        } else {
            Utils.showSnackbar(getActivity(), R.string.no_more_favorite_stories);
        }
    }

}