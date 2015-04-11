package by.anatoldeveloper.stories.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import by.anatoldeveloper.stories.R;
import by.anatoldeveloper.stories.adapter.StoryAdapter;
import by.anatoldeveloper.stories.model.Story;

/**
 * Created by Anatol on 25.02.2015.
 * Project Story
 */
public class FavoriteStoriesFragment extends BaseFragment {

    public static String FAVORITE_STORIES_FRAGMENT = "FAVORITE_STORIES_FRAGMENT";

    private static final String SELECTED_ITEM = "SELECTED_ITEM";

    private boolean isTwoPaneMode = false;
    private StoryAdapter mAdapter;
    private ListView mStories;
    private ViewGroup mStoryContainer;
    private TextView mFavoriteLabel;

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mAdapter.setSelectedItem(savedInstanceState.getInt(SELECTED_ITEM, -1));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM, mAdapter.getSelectedItem());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_stories, container, false);
        mStoryContainer = (LinearLayout) rootView.findViewById(R.id.list_stories_container);
        mStories = (ListView) rootView.findViewById(R.id.lv_favorite_stories_stories);
        mStories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setSelectedItem(position);
                Story s = mAdapter.getItem(position);
                openFavoriteFragmentWithId((int) s.id);
            }
        });
        mFavoriteLabel = (TextView) rootView.findViewById(R.id.favorite);
        refreshList();
        if (rootView.findViewById(R.id.story_container) != null) {
            isTwoPaneMode = true;
        }
        return rootView;
    }

    private void refreshList() {
        List<Story> favoriteStories = mRepository.findFavoriteStories();
        mAdapter = new StoryAdapter(getActivity(), favoriteStories);
        mStories.setAdapter(mAdapter);
        updateLayoutVisibility(favoriteStories.size() > 0);
    }

    private void updateLayoutVisibility(boolean isListWithItems) {
        if (isListWithItems) {
            mStoryContainer.setVisibility(View.VISIBLE);
            mFavoriteLabel.setVisibility(View.GONE);
        } else {
            mStoryContainer.setVisibility(View.GONE);
            mFavoriteLabel.setVisibility(View.VISIBLE);
        }
    }

    private void openFavoriteFragmentWithId(int id) {
        if (isTwoPaneMode) {
            getChildFragmentManager().
                    beginTransaction().
                    replace(R.id.story_container, FavoriteStoryFragment.newInstance(id)).
                    commit();
        } else {
            getActivity().
                    getSupportFragmentManager().beginTransaction().
                    replace(R.id.container, FavoriteStoryFragment.newInstance(id)).
                    addToBackStack(null).commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.favorite_stories);
        if (isTwoPaneMode && mAdapter.getCount() > 0) {
            updateContentChildFragment();
        } else if (mAdapter.getSelectedItem() != -1) {
            openFavoriteFragmentWithId((int)mAdapter.getSelectedStory().id);
        }
    }

    private void updateContentChildFragment() {
        mAdapter.updateSelectedItemIfNeeded(); // If selectedItem goes out of 0 .. stories.size() - 1, we need update it
        openFavoriteFragmentWithId((int) mAdapter.getSelectedStory().id);
        listScrollToPositionOnMainUIThread(mStories, mAdapter.getSelectedItem());
    }

    public void updateSelectedStoryItem(int storyId) {
        mAdapter.updateSelectedStoryItem(storyId);
        if (mAdapter.getSelectedItem() != -1) {
            listScrollToPositionOnMainUIThread(mStories, mAdapter.getSelectedItem());
        }
    }

    public void refreshFavoriteStories(boolean isFavorite, int storyId) {
        final int selectedItem = mAdapter.getSelectedItem();
        refreshList();
        if (isFavorite) {
            updateSelectedStoryItem(storyId);
        } else {
            listScrollToPositionOnMainUIThread(mStories, selectedItem);
        }
    }

    private void listScrollToPositionOnMainUIThread(ListView list, final int position) {
        list.post(new Runnable() {
            @Override
            public void run() {
                mStories.smoothScrollToPosition(position);
            }
        });
    }

}