package by.anatoldeveloper.stories.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import by.anatoldeveloper.stories.BuildConfig;
import by.anatoldeveloper.stories.R;
import by.anatoldeveloper.stories.Utils;
import by.anatoldeveloper.stories.analytics.Analytics;
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
    private boolean isAnimating = false;

    public static FavoriteStoryFragment newInstance(int storyId) {
        FavoriteStoryFragment fragment = new FavoriteStoryFragment();
        Bundle args = new Bundle();
        args.putInt(STORY_ID, storyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.favorite_story_fragment, container, false);
        mCurrentStory = getArguments().getInt(STORY_ID);
        mTvStoryText = (TextView) rootView.findViewById(R.id.tv_story);
        mTvStoryText.setMovementMethod(new ScrollingMovementMethod());
        Button nextStory = (Button) rootView.findViewById(R.id.btn_next);
        nextStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAnimating) {
                    Analytics.trackFavoriteStory(getActivity(), mLikeButton.isChecked(), mCurrentStory);
                    showStoryWithMinId(mCurrentStory + 1, true);
                }
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
        showStoryWithMinId(mCurrentStory, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.favorite_stories);
    }

    private void showStoryWithMinId(int id, boolean withAnimation) {
        Story s = mRepository.getFavoriteStory(id);
        if (s != null && withAnimation) {
            showStoryWithAnimation(s);
        } else if (s != null && !withAnimation) {
            showStory(s);
        } else {
            Utils.showSnackbar(getActivity(), R.string.no_more_favorite_stories);
        }
    }

    private void showStory(Story s) {
        mTvStoryText.scrollTo(0, 0);
        mLikeButton.setChecked(s.favorite);
        mTvStoryText.setText(s.text);
        mCurrentStory = (int) s.id;
    }

    private void showStoryWithAnimation(final Story s) {
        Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
        final Animation slideFromRight = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_right);
        slideFromRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimating = false;
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { isAnimating = true; }
            @Override
            public void onAnimationEnd(Animation animation) {
                showStory(s);
                mTvStoryText.startAnimation(slideFromRight);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        mTvStoryText.startAnimation(fadeOut);
    }

}