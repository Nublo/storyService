package by.anatoldeveloper.stories.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;

/**
 * Created by Anatol on 07.03.2015.
 * Project Story
 */

@DatabaseTable(tableName = "story")
public class Story {

    public static final String ID_FIELD_NAME = "id";
    public static final String SEX_FIELD_NAME = "sex";
    public static final String FAVORITE_FIELD_NAME = "favorite";

    @DatabaseField(generatedId = true, columnName = ID_FIELD_NAME)
    public long id;
    @DatabaseField
    public String text;
    @DatabaseField(columnName = SEX_FIELD_NAME)
    public boolean sex;
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public Site site;
    @DatabaseField(columnName = FAVORITE_FIELD_NAME)
    public boolean favorite;

    public Story() {
        favorite = false;
    }

    @Override
    public int hashCode() {
        int prime = 17;
        int result = 1;
        result = prime * result + (int)id;
        result = prime * result + text.hashCode();
        result = prime * result + (sex ? 1 : 0);
        result = prime * result + (site == null ? 0 : site.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != getClass()) {
            return false;
        }
        Story other = (Story) o;
        if (this.id != other.id || !this.text.equals(other.text)
                || this.sex != other.sex || this.site.equals(other.site)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("[id = %d, text = %s, sex = %s, favourite = %s, site = %s]",
                id, text, (sex ? "true":"false"), (favorite ? "true" : "false"), site.toString());
    }

    public static class StoryList extends ArrayList<Story> {

    }

}