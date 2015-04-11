package by.anatoldeveloper.stories;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import by.anatoldeveloper.stories.fragment.FavoriteStoriesFragment;
import by.anatoldeveloper.stories.fragment.StoryFragment;

/**
 * Created by Anatol on 07.03.2015.
 * Project Story
 */
public class MainActivity extends BaseSpiceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new StoryFragment())
                    .commit();
        }
    }

    private void initToolBar() {
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        if (toolBar != null) {
            setSupportActionBar(toolBar);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_favorite) {
            getSupportFragmentManager().beginTransaction().
                replace(R.id.container, new FavoriteStoriesFragment(),
                        FavoriteStoriesFragment.FAVORITE_STORIES_FRAGMENT).addToBackStack(null).commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}