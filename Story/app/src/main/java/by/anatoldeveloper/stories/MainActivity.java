package by.anatoldeveloper.stories;

import android.os.Bundle;
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
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new StoryFragment())
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            getSupportFragmentManager().beginTransaction().
                replace(R.id.container, new FavoriteStoriesFragment()).addToBackStack(null).commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}