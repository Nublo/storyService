package by.anatoldeveloper.stories.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;

import by.anatoldeveloper.stories.MainActivity;
import by.anatoldeveloper.stories.R;
import by.anatoldeveloper.stories.data.StoryRepository;

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
    public void onResume() {
        super.onResume();
        setTitle(R.string.app_name);
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

    protected void setTitle(int resourceId) {
        ((MainActivity) getActivity()).setTitle(resourceId);
    }

}