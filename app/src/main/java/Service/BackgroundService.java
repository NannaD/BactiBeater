package Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import APIConnection.Callback;
import APIConnection.FirebaseAPIBehaviourConnection;
import APIConnection.FirebaseAPISignInConnection;
import Items.BehaviourItem;
import Items.SpecificLocationSanitizeItem;
import Items.UserItem;
import kathrine.nanna.bactibeater.SpecificLocationActivity;

public class BackgroundService extends Service {
    private static final String LOG = "MyBackgroundService";
    private static final String BROADCASTTEST = "test";
    private static final String OVERVIEWCHARTDATA = "overviewPieData";
    private static final String LOCATIONS = "locations";
    private static final String LOCATIONSPECIFICDATA = "locationSpecificData";

    private List<String> locationNames;
    private List<BehaviourItem> behaviourData;
    List<BehaviourItem> locationSpecificBehaviourData;
    private List<SpecificLocationSanitizeItem> locationSpecificSanitizeData;
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
        behaviourData = new ArrayList<>();

        return super.onStartCommand(intent, flags, startId);
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

    private void broadcastLocationSpecificData(){
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(LOCATIONSPECIFICDATA);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }

    public int returnSanitizedDataForOverview(){
        return visitorsSanitized;
    }

    public int returnDidNotSanitizeDataForOverview(){
        return visitorsDidNotSanitize;
    }

    //Gets the data as soon as an user has signed in, and this data will be used throughout this userscenario
    public void getAPIData(){
        firebaseAPIBehaviourConnection.getBehaviours(new FirebaseAPIBehaviourConnection.VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(List<BehaviourItem> response) {
                behaviourData = response;

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

    public void SignIn(String userName, String password, Callback callback) {
        firebaseAPIBehaviourConnection.userName = userName;
        firebaseAPIBehaviourConnection.password = password;

        firebaseAPIBehaviourConnection.isSignedIn(callback);
    }

    public List<String> returnAllLocations() {
        locationNames = new ArrayList<>();
        for (int i = 0; i < behaviourData.size(); i++) {
            String locationName = behaviourData.get(i).getBeaconName();
            if (locationNames.contains(locationName) == false) {
                locationNames.add(locationName);
            }
        }
        return locationNames;
    }

    public void getLocationAndDateSpecificData(String locationName){
        locationSpecificBehaviourData = new ArrayList<>();
        broadcastLocationSpecificData();
    }

    public List<SpecificLocationSanitizeItem> returnLocationSpecificData(){

        locationSpecificSanitizeData = new ArrayList<>(); 
        List<String> dates = new ArrayList<>();
        int visitorCount = 0;
        int sanitizeCount = 0;

        SpecificLocationSanitizeItem specificLocationSanitizeItem;

        for (int i = 0; i < locationSpecificBehaviourData.size(); i++)
        {
            if (dates.contains(locationSpecificBehaviourData.get(i).getDate()) == false){
                dates.add(locationSpecificBehaviourData.get(i).getDate());
            }
        }

        for (int i = 0; i < dates.size(); i++){
            for (int j = 0; j < locationSpecificBehaviourData.size(); i++){

                String locationForList = locationSpecificBehaviourData.get(j).getBeaconName();
                String dateForList = locationSpecificBehaviourData.get(j).getDate();

                if (dates.get(i) == locationSpecificBehaviourData.get(j).getDate())
                {
                    visitorCount++;

                    if (locationSpecificBehaviourData.get(j).isDidSanitize() == true){
                        sanitizeCount++;
                    }
                }
                specificLocationSanitizeItem  = new SpecificLocationSanitizeItem(locationForList, dateForList, visitorCount, sanitizeCount);
                locationSpecificSanitizeData.add(specificLocationSanitizeItem);
            }
        }

        return locationSpecificSanitizeData;
    }


}
