package by.anatoldeveloper.story.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;

import by.anatoldeveloper.story.R;
import by.anatoldeveloper.story.data.StoryRepository;

/**
 * Created by Anatol on 07.03.2015.
 * Project Story
 */
public class BaseFragment extends Fragment {

    protected StoryRepository mRepository;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mRepository = new StoryRepository(activity);
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.emty_menu, menu);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mRepository = null;
    }

}
