package Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import Database.TaskApplication;

public class BackgroundService extends Service {
    private static final String LOG = "MyBackgroundService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG, "Service running");
        return super.onStartCommand(intent, flags, startId);
    }



}
