package by.anatoldeveloper.stories.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_stories, container, false);
        ListView lvStories = (ListView) rootView.findViewById(R.id.lv_favorite_stories_stories);
        List<Story> favoriteStories = mRepository.findFavoriteStories();
        final StoryAdapter adapter = new StoryAdapter(getActivity(), favoriteStories);
        lvStories.setAdapter(adapter);
        lvStories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Story s = adapter.getItem(position);
                openFavoriteFragmentWithId((int) s.id);
            }
        });
        TextView favoriteLabel = (TextView) rootView.findViewById(R.id.favorite);
        showLayout(lvStories, favoriteLabel, favoriteStories.size() > 0 ? true : false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.favorite_stories);
    }

    public void showLayout(ListView stories, TextView favorite, boolean isListWithItems) {
        if (isListWithItems) {
            stories.setVisibility(View.VISIBLE);
            favorite.setVisibility(View.GONE);
        } else {
            stories.setVisibility(View.GONE);
            favorite.setVisibility(View.VISIBLE);
        }
    }

    public void openFavoriteFragmentWithId(int id) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, FavoriteStoryFragment.newInstance(id));
        transaction.addToBackStack(null);
        transaction.commit();
    }

}