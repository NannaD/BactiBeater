package Database;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DAO _DAO();
}
