package by.anatoldeveloper.story.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Anatol on 18.02.2015.
 * Project Story
 */
@DatabaseTable(tableName = "site")
public class Site {

    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField
    public String name;

    public Site() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Site other = (Site) o;
        if (this.id != other.id || name.equals(other.name)) {
            return false;
        }
        return true;
    }
}