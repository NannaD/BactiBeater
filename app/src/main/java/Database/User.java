package Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity(indices = {@Index(value = {"id"}, unique = true)})
public class User {
    @PrimaryKey(autoGenerate = true)
    private Integer uid;
    @ColumnInfo(name="id")
    public Integer id;
    @ColumnInfo(name="location")
    public List<Integer> location;
    @ColumnInfo(name="didSanitize")
    public List<Boolean> didSanitize;
    @ColumnInfo(name="dateTime")
    public List<Date> dateTime;


    public User(Integer id, List<Integer> location, List<Boolean> didSanitize, List<Date> dateTime)
    {
        this.id = id;
        this.location = location;
        this.didSanitize = didSanitize;
        this.dateTime = dateTime;
    }

    public Integer getId() {return id;}

    public void setId (Integer id) {this.id = id;}

    public List<Integer> getLocations() {return location;}

    public List<Boolean> getSanitations() {return didSanitize;}

    public List<Date> getDateTime() {return dateTime;}


}
