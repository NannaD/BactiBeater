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
    public Integer location;
    @ColumnInfo(name="didSanitize")
    public Boolean didSanitize;
    @ColumnInfo(name="dateTime")
    public Date dateTime;


    public User(Integer id, Integer location, Boolean didSanitize, Date dateTime)
    {
        this.id = id;
        this.location = location;
        this.didSanitize = didSanitize;
        this.dateTime = dateTime;
    }

    public Integer getId() {return id;}

    public void setId (Integer id) {this.id = id;}

    public Integer getLocations() {return location;}

    public Boolean getSanitations() {return didSanitize;}

    public Date getDateTime() {return dateTime;}


}
