package by.anatoldeveloper.stories.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import by.anatoldeveloper.stories.R;
import by.anatoldeveloper.stories.Utils;
import by.anatoldeveloper.stories.analytics.Analytics;
import by.anatoldeveloper.stories.model.Story;
import by.anatoldeveloper.stories.story.iterator.NextStory;
import by.anatoldeveloper.stories.story.iterator.NextStoryFabric;

/**
 * Created by Anatol on 07.03.2015.
 * Project Story
 */
public class FavoriteStoryFragment extends BaseStoryFragment {

    private static final String STORY_ID = "story_id";

    public static FavoriteStoryFragment newInstance(int storyId) {
        FavoriteStoryFragment fragment = new FavoriteStoryFragment();
        Bundle args = new Bundle();
        args.putInt(STORY_ID, storyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView(View rootView) {
        mCurrentStory = getArguments().getInt(STORY_ID);
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
        NextStory nextStory = NextStoryFabric.favoriteStory();
        Story s = nextStory.nextStory(mRepository, id);
        if (s != null && withAnimation) {
            showStoryWithAnimation(s);
        } else if (s != null) {
            showStory(s);
        } else {
            Utils.showSnackbar(getActivity(), R.string.no_more_favorite_stories);
        }
    }

}