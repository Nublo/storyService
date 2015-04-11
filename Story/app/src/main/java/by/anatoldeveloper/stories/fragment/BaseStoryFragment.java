package by.anatoldeveloper.stories.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ToggleButton;

import by.anatoldeveloper.stories.R;
import by.anatoldeveloper.stories.Utils;
import by.anatoldeveloper.stories.model.Story;

/**
 * Created by Anatol on 26.03.2015.
 * Project Story
 */
public abstract class BaseStoryFragment extends BaseFragment{

    protected int mCurrentStory;
    protected TextView mTvStoryText;
    protected ToggleButton mLikeButton;
    protected Boolean isAnimating = false;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_story, container, false);

        mTvStoryText = (TextView) rootView.findViewById(R.id.tv_story);
        mTvStoryText.setMovementMethod(new ScrollingMovementMethod());
        mLikeButton = (ToggleButton) rootView.findViewById(R.id.tbn_like);
        mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFavoriteClickListener(mLikeButton.isChecked());
            }
        });

        initView(rootView);

        return rootView;
    }

    protected void setFavoriteClickListener(boolean favorite) {
        mRepository.updateFavoriteById(favorite, mCurrentStory);
    }

    protected abstract void initView(View rootView);

    protected void showStory(Story s) {
        mTvStoryText.scrollTo(0, 0);
        mLikeButton.setChecked(s.favorite);
        mTvStoryText.setText(s.text);
        mCurrentStory = (int) s.id;
        Utils.log(s.toString());
    }

    protected void showUndoIcon() {
        if (menu != null) {
            MenuItem menuItem = menu.findItem(R.id.action_undo);
            if (menuItem != null) {
                menuItem.setVisible(true);
            }
        }
    }

    protected void showNextStoryWithAnimation(Story s) {
        Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
        Animation slide = Utils.nextStoryAnimation(getActivity(),
                getResources().getConfiguration().orientation);
        showStoryWithAnimation(s, fadeOut, slide);
    }

    protected void showPreviousStoryWithAnimation(Story s) {
        Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
        Animation slide = Utils.prevStoryAnimation(getActivity(),
                getResources().getConfiguration().orientation);
        showStoryWithAnimation(s, fadeOut, slide);
    }

    private void showStoryWithAnimation(final Story s, final Animation firstAnim, final Animation secondAnim) {
        secondAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimating = false;
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        firstAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { isAnimating = true; }
            @Override
            public void onAnimationEnd(Animation animation) {
                showStory(s);
                mTvStoryText.startAnimation(secondAnim);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        mTvStoryText.startAnimation(firstAnim);
    }

}