package Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;

import APIConnection.APISignInConnection;
import APIConnection.APISummedBehaviourConnection;
import APIConnection.APISummedSanitizerConnection;
import APIConnection.Callback;
import APIConnection.APIAverageBehaviorConnection;
import APIConnection.APIBehaviourConnection;
import APIConnection.APISanitizeItemConnection;
import Items.BehaviourItem;
import Items.SanitizeItem;
import Items.AverageBehaviourItem;
import Items.SummedBehavioursItem;
import Items.SummedSanitizersItem;

public class BackgroundService extends Service {

    private static final String LOG = "MyBackgroundService";

    //For broadcasting
    private static final String OVERVIEWCHARTDATA = "overviewPieData";
    private static final String LOCATIONSPECIFICDATA = "locationSpecificData";
    private static final String BROADCASTSEVENDAYSAB = "SevenDaysItemsAB";
    private static final String BROADCASTSEVENDAYSSB = "SevenDaysItemsSB";
    private static final String BROADCASTSEVENDAYSSS = "SevenDaysItemsSS";

    public String uName;
    public String pWord;

    private int visitorsSanitized = 0;
    private int visitorsDidNotSanitize = 0;
    private int sizeTest = 0;

    //Lists for datahandling
    private List<String> locationNames = new ArrayList<>();
    private List<BehaviourItem> behaviourData = new ArrayList<>();
    private List<SanitizeItem> sanitizeItems = new ArrayList<>();
    private ArrayList<SanitizeItem> locationSpecificSanitizeItems = new ArrayList<>();
    private List<AverageBehaviourItem> lastSevenAverageBehaviourItems = new ArrayList<>();
    private List<SummedBehavioursItem> lastSevenSummedBehaviourItems = new ArrayList<>();
    private List<SummedSanitizersItem> lastSevenSummedSanitizerItems = new ArrayList<>();

    private APIBehaviourConnection APIBehaviourConnection = new APIBehaviourConnection(BackgroundService.this);
    private APISanitizeItemConnection APISanitizeItemConnection = new APISanitizeItemConnection(BackgroundService.this);
    private APIAverageBehaviorConnection APIAverageBehaviorConnection = new APIAverageBehaviorConnection(BackgroundService.this);
    private APISummedBehaviourConnection APISummedBehaviourConnection = new APISummedBehaviourConnection(BackgroundService.this);
    private APISummedSanitizerConnection APISummedSanitizerConnection = new APISummedSanitizerConnection(BackgroundService.this);
    private APISignInConnection apiSignInConnection = new APISignInConnection(BackgroundService.this);


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

        return super.onStartCommand(intent, flags, startId);
    }

    //Broadcasts
    private void broadcastOverviewChartData() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(OVERVIEWCHARTDATA);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }

    private void broadcastLocationSpecificData(){
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(LOCATIONSPECIFICDATA);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }

    private void broadcastSevenDaysABItems(){
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(BROADCASTSEVENDAYSAB);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }

    private void broadcastSevenDaysSBItems(){
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(BROADCASTSEVENDAYSSB);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }

    public void broadcastSevenDaysSSItems(){
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(BROADCASTSEVENDAYSSS);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }

    //Getting and handling data
    public void getAPIData(){
        APIBehaviourConnection.getBehaviours(uName, pWord, new APIBehaviourConnection.VolleyResponseListener() {
            @Override
            public void onError(String message) { }

            @Override
            public void onResponse(List<BehaviourItem> response) {
                behaviourData = response;
                visitorsSanitized = 0;
                visitorsDidNotSanitize = 0;

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

    public void getLocationSpecificSanitizeItems(final String location) {
        APISanitizeItemConnection.getLocationAndDateSpecificData(uName, pWord, new APISanitizeItemConnection.VolleyResponseListener() {
            @Override
            public void onError(String message) { }

            @Override
            public void onResponse(List<SanitizeItem> response) {
                sanitizeItems = response;

                for (int i = 0; i < sanitizeItems.size(); i++){
                    String v = sanitizeItems.get(i).getLocation();
                    if (v.equals(location) == true){
                        locationSpecificSanitizeItems.add(sanitizeItems.get(i));
                    }
                }
                broadcastLocationSpecificData();
            }
        });
    }

    public void getLastSevenDaysABOverview(){
          APIAverageBehaviorConnection.getSevenDaysOverview(uName, pWord, new APIAverageBehaviorConnection.VolleyResponseListener() {
            @Override
            public void onError(String message) { }

            @Override
            public void onResponse(List<AverageBehaviourItem> response) {
                sizeTest = 0;

                if (response.size() < 8){
                    sizeTest = response.size();
                }
                else {
                    sizeTest = 7;
                }

                for (int i = 0; i < sizeTest; i++){
                    lastSevenAverageBehaviourItems.add(response.get(response.size()-1-i));
                }
                broadcastSevenDaysABItems();
            }
        });
    }

    public void getLastSevenDaysSBOverview(){
        APISummedBehaviourConnection.getSummedBehaviours(uName, pWord, new APISummedBehaviourConnection.VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(List<SummedBehavioursItem> response) {
                sizeTest = 0;

                if (response.size() < 8){
                    sizeTest = response.size();
                }
                else {
                    sizeTest = 7;
                }


                for (int i = 0; i < sizeTest; i++){
                    lastSevenSummedBehaviourItems.add(response.get(response.size()-1-i));
                }
                broadcastSevenDaysSBItems();
            }
        });
    }

    public void getLastSevenDaysSSOverview(){
        APISummedSanitizerConnection.getSummedSanitizerData(uName, pWord, new APISummedSanitizerConnection.VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(List<SummedSanitizersItem> response) {
                sizeTest = 0;

                if (response.size() < 8){
                    sizeTest = response.size();
                }
                else {
                    sizeTest = 7;
                }

                for (int i = 0; i < sizeTest; i++){
                    lastSevenSummedSanitizerItems.add(response.get(response.size()-1-i));
                }
                broadcastSevenDaysSSItems();
            }
        });
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

    public List<SanitizeItem> returnLocationSpecificData(){
        return locationSpecificSanitizeItems;
    }

    public int returnSanitizedDataForOverview(){
        return visitorsSanitized;
    }

    public int returnDidNotSanitizeDataForOverview(){
        return visitorsDidNotSanitize;
    }

    public List<AverageBehaviourItem> returnLastSevenDaysABOverview(){ return lastSevenAverageBehaviourItems;}
    public List<SummedBehavioursItem> returnLastSevenDaysSBOverview(){ return lastSevenSummedBehaviourItems;}
    public List<SummedSanitizersItem> returnLastSevenDaysSSOverview(){ return lastSevenSummedSanitizerItems;}

    public void SignIn(String userName, String password, Callback callback) {
        apiSignInConnection.userName = userName;
        apiSignInConnection.password = password;

        uName = userName;
        pWord = password;

        apiSignInConnection.isSignedIn(callback);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationSpecificSanitizeItems.clear();
        behaviourData.clear();
        sanitizeItems.clear();
        locationNames.clear();
    }
}
