package by.anatoldeveloper.stories.adapter;

import android.content.Context;
import android.graphics.Color;
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
    private int mSelectedItemColor;
    private int mSelectedItem = -1;

    public StoryAdapter(Context context, List<Story> stories) {
        this.mStories = stories;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mSelectedItemColor = context.getResources().getColor(R.color.font_selected);
        this.mSelectedItem = -1;
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
        TextView storyText = (TextView) view.findViewById(R.id.tv_story);
        Story s = getItem(position);
        storyText.setText(s.text);
        view.setBackgroundColor(Color.TRANSPARENT);
        if (position == mSelectedItem) {
            view.setBackgroundColor(mSelectedItemColor);
        }
        return view;
    }

    public Story getSelectedStory() {
        if (mSelectedItem >= 0 && mSelectedItem < mStories.size()) {
            return mStories.get(mSelectedItem);
        }
        return null;
    }

    public void updateSelectedItemIfNeeded() {
        if (mSelectedItem == -1) {
            setSelectedItem(0);
        } else if (mSelectedItem >= getCount()) {
            setSelectedItem(getCount() - 1);
        }
    }

    public void updateSelectedStoryItem(int storyId) {
        for (int i = 0; i < mStories.size(); i++) {
            if (mStories.get(i).id == storyId) {
                mSelectedItem = i;
                break;
            }
            if (i == mStories.size()-1) { // If we didn't find such Story in collection
                mSelectedItem = -1;
            }
        }
        notifyDataSetChanged();
    }

    public void setSelectedItem(int selectedItem) {
        this.mSelectedItem = selectedItem;
        notifyDataSetChanged();
    }

    public void removeSelectedItem() {
        if (mSelectedItem >= 0 && mSelectedItem < mStories.size()) {
            mStories.remove(getSelectedItem());
            mSelectedItem = -1;
            notifyDataSetChanged();
        }
    }

    public void addStoryWithUpdateUI(Story s) {
        if (mStories == null)
            return;
        for(int i = 0; i < mStories.size(); i++) {
            if (mStories.get(i).id > s.id) {
                mStories.add(i, s);
                setSelectedItem(i);
                return;
            }
        }
        mStories.add(s);
        notifyDataSetChanged();
        setSelectedItem(mStories.size() - 1);
    }

    public int getSelectedItem() {
        return this.mSelectedItem;
    }

}