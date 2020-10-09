package Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import APIConnection.Callback;
import APIConnection.FirebaseAPIBehaviourConnection;
import APIConnection.FirebaseAPISignInConnection;
import Items.BehaviourItem;
import Items.UserItem;

public class BackgroundService extends Service {
    private static final String LOG = "MyBackgroundService";
    private static final String BROADCASTTEST = "test";
    private FirebaseAPIBehaviourConnection firebaseAPIBehaviourConnection = new FirebaseAPIBehaviourConnection(BackgroundService.this);


    public class LocalBinder extends Binder {
        public BackgroundService getService() {
            return BackgroundService.this;
        }
    }

    private final IBinder binder = new LocalBinder();


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG, "Service running");
        broadcastTest();


        return super.onStartCommand(intent, flags, startId);
    }

    // This is just a test of sending a broadcast
    private void broadcastTest() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(BROADCASTTEST);

        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }

    public void APITest() {

        firebaseAPIBehaviourConnection.getBehaviours(new FirebaseAPIBehaviourConnection.VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(BehaviourItem response) {

            }
        });
    }

    public void SignIn(String userName, String password, Callback callback) {
        firebaseAPIBehaviourConnection.userName = userName;
        firebaseAPIBehaviourConnection.password = password;

        firebaseAPIBehaviourConnection.isSignedIn(callback);
    }
}
