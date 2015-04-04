package by.anatoldeveloper.stories.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Anatol on 18.02.2015.
 * Project Story
 */
@DatabaseTable(tableName = "site")
public class Site {

    public static final String ID_FIELD_NAME = "id";
    public static final String NAME_FIELD_NAME = "name";

    @DatabaseField(generatedId = true, columnName = ID_FIELD_NAME)
    public int id;
    @DatabaseField(unique = true, columnName = NAME_FIELD_NAME)
    public String name;

    public Site() {

    }

    @Override
    public String toString() {
        return String.format("[id = %d, name = %s]", id, name);
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