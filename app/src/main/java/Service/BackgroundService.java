package Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;

import APIConnection.Callback;
import APIConnection.FirebaseAPIBehaviourConnection;
import APIConnection.FirebaseAPISignInConnection;
import Items.BehaviourItem;
import Items.UserItem;

public class BackgroundService extends Service {
    private static final String LOG = "MyBackgroundService";
    private static final String BROADCASTTEST = "test";
    private static final String OVERVIEWCHARTDATA = "overviewPieData";
    private static final String LOCATIONS = "locations";

    private List<String> locationNames;
    private int visitorsSanitized = 0;
    private int visitorsDidNotSanitize = 0;

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

    //Broadcasts
    private void broadcastOverviewChartData() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(OVERVIEWCHARTDATA);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }

    private void broadcastLocations() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(LOCATIONS);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }

    public void getBehaviourModels() {

        firebaseAPIBehaviourConnection.getBehaviours(new FirebaseAPIBehaviourConnection.VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(List<BehaviourItem> response) {
                //MANGLER
            }
        });
    }

    public int returnSanitizedDataForOverview(){
        return visitorsSanitized;
    }

    public int returnDidNotSanitizeDataForOverview(){
        return visitorsDidNotSanitize;
    }

    public void getAPIDataForOverview(){
        firebaseAPIBehaviourConnection.getBehaviours(new FirebaseAPIBehaviourConnection.VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(List<BehaviourItem> response) {
                for(int i = 0; i < response.size(); i++) {
                    if (response.get(i).isDidSanitize() == true) {
                        visitorsSanitized++;
                    } else if (response.get(i).isDidSanitize() == false) {
                        visitorsDidNotSanitize++;
                    }
                }
                broadcastOverviewChartData();
            }
        });
    }

    /*
    public void getAPIDataForOverview(){
        firebaseAPIBehaviourConnection.getBehaviours(new FirebaseAPIBehaviourConnection.VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(final List<BehaviourItem> response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < response.size(); i++){
                            if (response.get(i).isDidSanitize() == true){
                                visitorsSanitized++;
                            } else if (response.get(i).isDidSanitize() == false){
                                visitorsDidNotSanitize++;
                            }
                        }
                    }
                }).start();
                broadcastOverviewChartData();

            }
        });
    }

     */

    public void SignIn(String userName, String password, Callback callback) {
        firebaseAPIBehaviourConnection.userName = userName;
        firebaseAPIBehaviourConnection.password = password;

        firebaseAPIBehaviourConnection.isSignedIn(callback);
    }

    public void getAllLocations(){
        firebaseAPIBehaviourConnection.getBehaviours(new FirebaseAPIBehaviourConnection.VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(final List<BehaviourItem> response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        locationNames = new ArrayList<>();
                        for(int i = 0; i < response.size(); i++){
                            String locationName = response.get(i).getBeaconId();
                            if (locationNames.contains(locationName) == false){
                                locationNames.add(locationName);
                            }
                        }
                    }
                }).start();
                broadcastLocations();
            }
        });
    }

    public List<String> returnAllLocations(){
        return locationNames;
    }
}
