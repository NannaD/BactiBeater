package Service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Database.TaskApplication;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

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

    public class LocalBinder extends Binder {
        public BackgroundService getService() {
            return BackgroundService.this;
        }
    }

    private final IBinder binder = new LocalBinder();


    private void broadcastPlayersList() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(BROADCASTPLAYERLIST);

        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }

    private void broadcastTopThreePlayersList(){
        Intent ttbroadcastIntent = new Intent();
        ttbroadcastIntent.setAction(BROADCASTTOPTHREE);

        LocalBroadcastManager.getInstance(this).sendBroadcast(ttbroadcastIntent);
    }

    private void broadcastLocation(){
        Intent locbroadcastIntent = new Intent();
        locbroadcastIntent.setAction(BROADCASTLOCATION);

        LocalBroadcastManager.getInstance(this).sendBroadcast(locbroadcastIntent);
    }




}
