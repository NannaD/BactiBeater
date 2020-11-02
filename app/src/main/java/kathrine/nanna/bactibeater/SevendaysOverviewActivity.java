package kathrine.nanna.bactibeater;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import Items.AverageBehaviourItem;
import Service.BackgroundService;

public class SevendaysOverviewActivity extends AppCompatActivity {

    private static final String BROADCASTSEVENDAYS = "SevenDaysItems";

    private PieChart onedayago;
    private PieChart twodaysago;
    private PieChart threedaysago;
    private PieChart fourdaysago;
    private PieChart fivedaysago;
    private PieChart sixdaysago;
    private PieChart sevendaysago;

    private int firstSanitized;
    private int firstNotSanitized;
    private int secondSanitized;
    private int secondNotSanitized;
    private int thirdSanitized;
    private int thirdNotSanitized;
    private int fourthSanitized;
    private int fourthNotSanitized;
    private int fifthSanitized;
    private int fifthNotSanitized;
    private int sixthSanitized;
    private int sixtNotSanitized;
    private int seventhSanitized;
    private int seventhNotSanitized;

    private String firstDate;
    private String secondDate;
    private String thirdDate;
    private String fourthDate;
    private String fifthDate;
    private String sixthDate;
    private String seventhDate;

    private BackgroundService bService;
    private boolean bound;
    private List<AverageBehaviourItem> averageBehaviourItems = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sevenday_overview);

        //set up widgets
        onedayago = findViewById(R.id.overviewPieChart1);
        twodaysago = findViewById(R.id.overviewPieChart2);
        threedaysago = findViewById(R.id.overviewPieChart3);
        fourdaysago = findViewById(R.id.overviewPieChart4);
        fivedaysago = findViewById(R.id.overviewPieChart5);
        sixdaysago = findViewById(R.id.overviewPieChart6);
        sevendaysago = findViewById(R.id.overviewPieChart7);
    }

    public void updateSevenPieCharts(List<AverageBehaviourItem> averageBehaviourItems){

        firstSanitized = averageBehaviourItems.get(0).getSanitations();
        firstNotSanitized = averageBehaviourItems.get(0).getLocationChanges() - averageBehaviourItems.get(0).getSanitations();
        firstDate = averageBehaviourItems.get(0).getDate();

        secondSanitized = averageBehaviourItems.get(1).getSanitations();
        secondNotSanitized = averageBehaviourItems.get(1).getLocationChanges() - averageBehaviourItems.get(1).getSanitations();
        secondDate = averageBehaviourItems.get(1).getDate();

        thirdSanitized = averageBehaviourItems.get(2).getSanitations();
        thirdNotSanitized = averageBehaviourItems.get(2).getLocationChanges() - averageBehaviourItems.get(2).getSanitations();
        thirdDate = averageBehaviourItems.get(2).getDate();


       /* fourthSanitized = averageBehaviourItems.get(3).getSanitations();
        fourthNotSanitized = averageBehaviourItems.get(3).getLocationChanges() - averageBehaviourItems.get(3).getSanitations();
        fourthDate = averageBehaviourItems.get(3).getDate();

        fifthSanitized = averageBehaviourItems.get(4).getSanitations();
        fifthNotSanitized = averageBehaviourItems.get(4).getLocationChanges() - averageBehaviourItems.get(4).getSanitations();
        fifthDate = averageBehaviourItems.get(4).getDate();

        sixthSanitized = averageBehaviourItems.get(5).getSanitations();
        sixtNotSanitized = averageBehaviourItems.get(5).getLocationChanges() - averageBehaviourItems.get(5).getSanitations();
        sixthDate = averageBehaviourItems.get(5).getDate();

        seventhSanitized = averageBehaviourItems.get(6).getSanitations();
        seventhNotSanitized = averageBehaviourItems.get(6).getLocationChanges() - averageBehaviourItems.get(6).getSanitations();
        seventhDate = averageBehaviourItems.get(6).getDate(); */

        //FIRST DAY
        ArrayList<PieEntry> firstDay = new ArrayList<>();
        firstDay.add(new PieEntry(firstSanitized, "Santized"));
        firstDay.add(new PieEntry(firstNotSanitized, "Failed to sanitize"));

        PieDataSet firstPieDataset = new PieDataSet(firstDay, "");
        firstPieDataset.setColors(ColorTemplate.MATERIAL_COLORS);
        firstPieDataset.setValueTextColor(Color.BLACK);
        firstPieDataset.setValueTextSize(16f);

        PieData firstPieData = new PieData(firstPieDataset);

        onedayago.getDescription().setEnabled(false);
        onedayago.setCenterText(firstDate);

        onedayago.setData(firstPieData);
        onedayago.invalidate();

        //SECOND DAY
        ArrayList<PieEntry> secondDay = new ArrayList<>();
        secondDay.add(new PieEntry(secondSanitized, "Santized"));
        secondDay.add(new PieEntry(secondNotSanitized, "Failed to sanitize"));

        PieDataSet secondPieDataSet = new PieDataSet(secondDay, "");
        secondPieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        secondPieDataSet.setValueTextColor(Color.BLACK);
        secondPieDataSet.setValueTextSize(16f);

        PieData secondPieData = new PieData(secondPieDataSet);

        twodaysago.getDescription().setEnabled(false);
        twodaysago.setCenterText(secondDate);

        twodaysago.setData(secondPieData);
        twodaysago.invalidate();

        //THIRD DAY
        ArrayList<PieEntry> thirdDay = new ArrayList<>();
        thirdDay.add(new PieEntry(thirdSanitized, "Santized"));
        thirdDay.add(new PieEntry(thirdNotSanitized, "Failed to sanitize"));

        PieDataSet thirdPieDataSet = new PieDataSet(thirdDay, "");
        thirdPieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        thirdPieDataSet.setValueTextColor(Color.BLACK);
        thirdPieDataSet.setValueTextSize(16f);

        PieData thirdPieData = new PieData(thirdPieDataSet);

        threedaysago.getDescription().setEnabled(false);
        threedaysago.setCenterText(thirdDate);

        threedaysago.setData(thirdPieData);
        threedaysago.invalidate();


/*
        //FOURTH DAY
        ArrayList<PieEntry> fourthDay = new ArrayList<>();
        fourthDay.add(new PieEntry(fourthSanitized, "Santized"));
        fourthDay.add(new PieEntry(fourthNotSanitized, "Failed to sanitize"));

        PieDataSet fourthPieDataSet = new PieDataSet(fourthDay, "");
        fourthPieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        fourthPieDataSet.setValueTextColor(Color.BLACK);
        fourthPieDataSet.setValueTextSize(16f);

        PieData fourthPieData = new PieData(fourthPieDataSet);

        fourdaysago.getDescription().setEnabled(false);
        fourdaysago.setCenterText(fourthDate);

        fourdaysago.setData(fourthPieData);
        fourdaysago.invalidate();

        //FIFTH DAY
        ArrayList<PieEntry> fifthhDay = new ArrayList<>();
        fifthhDay.add(new PieEntry(fifthSanitized, "Santized"));
        fifthhDay.add(new PieEntry(fifthNotSanitized, "Failed to sanitize"));

        PieDataSet fifthPieDataSet = new PieDataSet(fifthhDay, "");
        fifthPieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        fifthPieDataSet.setValueTextColor(Color.BLACK);
        fifthPieDataSet.setValueTextSize(16f);

        PieData fifthPieData = new PieData(fifthPieDataSet);

        fivedaysago.getDescription().setEnabled(false);
        fivedaysago.setCenterText(fifthDate);

        fivedaysago.setData(fifthPieData);
        fivedaysago.invalidate();

        //SIXTH DAY
        ArrayList<PieEntry> sixthDay = new ArrayList<>();
        sixthDay.add(new PieEntry(sixthSanitized, "Santized"));
        sixthDay.add(new PieEntry(sixtNotSanitized, "Failed to sanitize"));

        PieDataSet sixthPieDataSet = new PieDataSet(sixthDay, "");
        sixthPieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        sixthPieDataSet.setValueTextColor(Color.BLACK);
        sixthPieDataSet.setValueTextSize(16f);

        PieData sixthPieData = new PieData(sixthPieDataSet);

        sixdaysago.getDescription().setEnabled(false);
        sixdaysago.setCenterText(sixthDate);

        sixdaysago.setData(sixthPieData);
        sixdaysago.invalidate();

        //SEVENTH DAY
        ArrayList<PieEntry> seventhDay = new ArrayList<>();
        seventhDay.add(new PieEntry(seventhSanitized, "Santized"));
        seventhDay.add(new PieEntry(seventhNotSanitized, "Failed to sanitize"));

        PieDataSet seventhPieDataSet = new PieDataSet(seventhDay, "");
        seventhPieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        seventhPieDataSet.setValueTextColor(Color.BLACK);
        seventhPieDataSet.setValueTextSize(16f);

        PieData seventhPiedata = new PieData(seventhPieDataSet);

        sevendaysago.getDescription().setEnabled(false);
        sevendaysago.setCenterText(seventhDate);

        sevendaysago.setData(seventhPiedata);
        sevendaysago.invalidate(); */


    }

    public class SevenDaysBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            averageBehaviourItems = bService.returnLastSevenDaysOverview();
            updateSevenPieCharts(averageBehaviourItems);
        }
    }

    //Setting up connection to service
    private ServiceConnection connection;
    private void setupConnectionToService() {
        connection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName className, IBinder service) {
                // We've bound to LocalService, cast the IBinder and get LocalService instance
                bService = ((BackgroundService.LocalBinder)service).getService();
                bound = true;

                bService.getLastSevenDaysOverview();
            }

            @Override
            public void onServiceDisconnected(ComponentName arg0) {
                bService = null;
                bound = false;
            }
        };
    }

    private void startMyService(){
        Intent serviceIntent = new Intent(SevendaysOverviewActivity.this, BackgroundService.class);
        startService(serviceIntent);
    }

    //Bind to Background service, learned how to from https://developer.android.com/guide/components/bound-services
    void bindToService() {
        Intent intent = new Intent(this, BackgroundService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        bound = true;
    }

    void unBindFromService() {
        if (bound) {
            // Detach our existing connection.
            unbindService(connection);
            bound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindFromService();
    }

    @Override
    protected void onStart() {
        super.onStart();

        startMyService();
        setupConnectionToService();
        bindToService();

        LocalBroadcastManager.getInstance(this).registerReceiver(new SevenDaysBroadcastReceiver(), new IntentFilter(BROADCASTSEVENDAYS));
    }
}
