package Database;

import android.app.Application;

import androidx.room.Room;

import Database.AppDatabase;

//Inspiration from TheRoomSituation-demo
public class TaskApplication extends Application {
    private AppDatabase db;

    public AppDatabase getDatabase(){
        if(db == null){
            db = Room.databaseBuilder(this, AppDatabase.class, "myBactiBeater").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return db;
    }
}
