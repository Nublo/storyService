package by.anatoldeveloper.story.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import by.anatoldeveloper.story.R;
import by.anatoldeveloper.story.model.Story;

/**
 * Created by Anatol on 07.03.2015.
 * Project Story
 */
public class FavoriteStoryFragment extends BaseFragment {

    private static final String STORY_ID = "story_id";

    private int mCurrentStoryId;
    private TextView mTvStoryText;
    private ToggleButton mLikeButton;

    public static FavoriteStoryFragment newInstance(int storyId) {
        FavoriteStoryFragment fragment = new FavoriteStoryFragment();
        Bundle args = new Bundle();
        args.putInt(STORY_ID, storyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_story, container, false);
        mCurrentStoryId = getArguments().getInt(STORY_ID);
        mTvStoryText = (TextView) rootView.findViewById(R.id.tv_text);
        mTvStoryText.setMovementMethod(new ScrollingMovementMethod());
        mTvStoryText.setScrollbarFadingEnabled(false);
        Button nextStory = (Button) rootView.findViewById(R.id.btn_next);
        nextStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStoryWithMinId(mCurrentStoryId + 1);
            }
        });
        mLikeButton = (ToggleButton) rootView.findViewById(R.id.tbn_like);
        mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean favorite = mLikeButton.isChecked();
                mRepository.updateFavoriteById(favorite, mCurrentStoryId);
                Story s = mRepository.getStoryById(mCurrentStoryId);
                Log.d("test", "Story after update : " + s.toString());
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        showStoryWithMinId(mCurrentStoryId);
    }

    private void showStoryWithMinId(int id) {
        Story s = mRepository.getFavoriteStory(id);
        showStory(s);
    }

    private void showStory(Story s) {
        if (s != null) {
            mTvStoryText.scrollTo(0, 0);
            mLikeButton.setChecked(s.favorite);
            mTvStoryText.setText(s.text);
            mCurrentStoryId = (int) s.id;
        } else {
            Toast.makeText(getActivity(), "У вас больше нету любимых историй", Toast.LENGTH_SHORT).show();
        }
    }

}