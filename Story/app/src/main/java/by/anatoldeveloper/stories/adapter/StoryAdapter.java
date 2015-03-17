package by.anatoldeveloper.stories.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import by.anatoldeveloper.stories.R;
import by.anatoldeveloper.stories.model.Story;

/**
 * Created by Anatol on 07.03.2015.
 * Project Story
 */
public class StoryAdapter extends BaseAdapter {

    private List<Story> mStories;
    private LayoutInflater mInflater;

    public StoryAdapter(Context context, List<Story> stories) {
        this.mStories = stories;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mStories.size();
    }

    @Override
    public Story getItem(int position) {
        return mStories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mStories.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.story_item, parent, false);
        }
        TextView storyText = (TextView) view.findViewById(R.id.tv_text);
        Story s = getItem(position);
        storyText.setText(s.text);
        return view;
    }

}
