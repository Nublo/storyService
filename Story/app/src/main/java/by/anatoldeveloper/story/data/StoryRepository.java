package by.anatoldeveloper.story.data;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.anatoldeveloper.story.model.Site;
import by.anatoldeveloper.story.model.Story;

/**
 * Created by Anatol on 18.02.2015.
 */
public class StoryRepository {

    private Dao<Story, Integer> storyDao;
    private Dao<Site, Integer> siteDao;

    public StoryRepository(Context ctx)
    {
        try {
            DatabaseManager dbManager = new DatabaseManager();
            DatabaseHelper db = dbManager.getHelper(ctx);
            storyDao = db.getStoryDao();
            siteDao = db.getSiteDao();
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

    public int deleteNotFavoriteStoriesWithMinId(int minId) {
        try {
            DeleteBuilder<Story, Integer> deleteBuilder =
                    storyDao.deleteBuilder();
            deleteBuilder.where().lt(Story.ID_FIELD_NAME, minId).and().ne(Story.FAVORITE_FIELD_NAME, true);
            return deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Story> findFavoriteStories() {
        try {
            QueryBuilder<Story, Integer> builder = storyDao.queryBuilder();
            return storyDao.query(builder.where().eq(Story.FAVORITE_FIELD_NAME, true).prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
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
            Story s = storyDao.queryForFirst(where.prepare());
            return s;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Story getFavoriteStory(int minId) {
        try {
            QueryBuilder<Story, Integer> builder = storyDao.queryBuilder();
            Where<Story, Integer> where = builder.where();
            where.ge(Story.ID_FIELD_NAME, minId).
                    and().eq(Story.FAVORITE_FIELD_NAME, true);
            Story s = storyDao.queryForFirst(where.prepare());
            return s;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Site> allSites() {
        try {
            return siteDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}