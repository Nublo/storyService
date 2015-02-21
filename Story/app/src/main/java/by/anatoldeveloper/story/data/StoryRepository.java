package by.anatoldeveloper.story.data;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;

import by.anatoldeveloper.story.model.Story;

/**
 * Created by Anatol on 18.02.2015.
 */
public class StoryRepository {

    private Dao<Story, Integer> storyDao;

    public StoryRepository(Context ctx)
    {
        try {
            DatabaseManager dbManager = new DatabaseManager();
            DatabaseHelper db = dbManager.getHelper(ctx);
            storyDao = db.getStoryDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int create(Story story)
    {
        try {
            return storyDao.create(story);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(Story story)
    {
        try {
            return storyDao.delete(story);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(Story story) {
        try {

            return storyDao.update(story);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Story getStoryById(int id) {
        try {
            return storyDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int updateFavoriteById(boolean favorite, int id) {
        try {
            Story s = getStoryById(id);
            s.favorite = favorite;
            return storyDao.update(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteStoryWithMinId(int minId) {
        try {
            DeleteBuilder<Story, Integer> deleteBuilder =
                    storyDao.deleteBuilder();
            deleteBuilder.where().lt(Story.ID_FIELD_NAME, minId);
            return deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countOfStories() {
        try {
            return (int)storyDao.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Story getPublicStory(int minId) {
        return getStory(minId, false);
    }

    public Story getSexStory(int minId) {
        return getStory(minId, true);
    }

    private Story getStory(int minId, boolean sex) {
        try {
            QueryBuilder<Story, Integer> builder = storyDao.queryBuilder();
            Where<Story, Integer> where = builder.where();
            where.ge(Story.ID_FIELD_NAME, minId).
                and().eq(Story.SEX_FIELD_NAME, sex);
            List<Story> stories = storyDao.query(where.prepare());
            if (stories != null && stories.size() > 0) {
                return stories.get(0);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}