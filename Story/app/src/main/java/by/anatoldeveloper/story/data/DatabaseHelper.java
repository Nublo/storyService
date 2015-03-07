package by.anatoldeveloper.story.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import by.anatoldeveloper.story.model.Site;
import by.anatoldeveloper.story.model.Story;

/**
 * Created by Anatol on 18.02.2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "story.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Story, Integer> storyDao = null;
    private Dao<Site, Integer> siteDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Site.class);
            TableUtils.createTable(connectionSource, Story.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public Dao<Story, Integer> getStoryDao() throws SQLException {
        if (storyDao == null) {
            storyDao = getDao(Story.class);
        }
        return storyDao;
    }

    public Dao<Site, Integer> getSiteDao() throws SQLException {
        if (siteDao == null) {
            siteDao = getDao(Site.class);
        }
        return siteDao;
    }

    @Override
    public void close() {
        super.close();
        storyDao = null;
    }
}
