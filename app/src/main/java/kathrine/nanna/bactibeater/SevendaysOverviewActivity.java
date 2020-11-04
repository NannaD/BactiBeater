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
import Items.SummedBehavioursItem;
import Items.SummedSanitizersItem;
import Service.BackgroundService;

public class SevendaysOverviewActivity extends AppCompatActivity {

    private static final String BROADCASTSEVENDAYSAB = "SevenDaysItemsAB";
    private static final String BROADCASTSEVENDAYSSB = "SevenDaysItemsSB";
    private static final String BROADCASTSEVENDAYSSS = "SevenDaysItemsSS";

    //Average Behaviour
    private PieChart aBonedayago;
    private PieChart aBtwodaysago;
    private PieChart aBthreedaysago;
    private PieChart aBfourdaysago;
    private PieChart aBfivedaysago;
    private PieChart aBsixdaysago;
    private PieChart aBsevendaysago;

    private int aBSanitized;
    private int aBNotSanitized;
    private String aBDate;

    //Summed Behaviour
    private PieChart sBonedayago;
    private PieChart sBtwodaysago;
    private PieChart sBthreedaysago;
    private PieChart sBfourdaysago;
    private PieChart sBfivedaysago;
    private PieChart sBsixdaysago;
    private PieChart sBsevendaysago;

    private int sBSanitized;
    private int sBNotSanitized;
    private String sBDate;

    //Summed Sanitations
    private PieChart sSonedayago;
    private PieChart sStwodaysago;
    private PieChart sSthreedaysago;
    private PieChart sSfourdaysago;
    private PieChart sSfivedaysago;
    private PieChart sSsixdaysago;
    private PieChart sSsevendaysago;

    private int sSLocationChangeTrue;
    private int getsSLocationChangeFalse;
    private String sSDate;

    private BackgroundService bService;
    private boolean bound;
    private List<AverageBehaviourItem> averageBehaviourItems = new ArrayList<>();
    private List<SummedBehavioursItem> summedBehavioursItems = new ArrayList<>();
    private List<SummedSanitizersItem> summedSanitizersItems = new ArrayList<>();

    private List<PieChart> aBPieCharts = new ArrayList<>();
    private List<PieChart> sBPieCharts = new ArrayList<>();
    private List<PieChart> sSPieCharts = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sevenday_overview);

        //set up widgets
        //Average Behaviour
        aBonedayago = findViewById(R.id.ABPieChart1);
        aBtwodaysago = findViewById(R.id.ABPieChart2);
        aBthreedaysago = findViewById(R.id.ABPieChart3);
        aBfourdaysago = findViewById(R.id.ABPieChart4);
        aBfivedaysago = findViewById(R.id.ABPieChart5);
        aBsixdaysago = findViewById(R.id.ABPieChart6);
        aBsevendaysago = findViewById(R.id.ABPieChart7);

        aBPieCharts.add(aBonedayago);
        aBPieCharts.add(aBtwodaysago);
        aBPieCharts.add(aBthreedaysago);
        aBPieCharts.add(aBfourdaysago);
        aBPieCharts.add(aBfivedaysago);
        aBPieCharts.add(aBsixdaysago);
        aBPieCharts.add(aBsevendaysago);

        //Summed Behaviour
        sBonedayago = findViewById(R.id.SBPieChart1);
        sBtwodaysago = findViewById(R.id.SBPieChart2);
        sBthreedaysago = findViewById(R.id.SBPieChart3);
        sBfourdaysago = findViewById(R.id.SBPieChart4);
        sBfivedaysago = findViewById(R.id.SBPieChart5);
        sBsixdaysago = findViewById(R.id.SBPieChart6);
        sBsevendaysago = findViewById(R.id.SBPieChart7);

        sBPieCharts.add(sBonedayago);
        sBPieCharts.add(sBtwodaysago);
        sBPieCharts.add(sBthreedaysago);
        sBPieCharts.add(sBfourdaysago);
        sBPieCharts.add(sBfivedaysago);
        sBPieCharts.add(sBsixdaysago);
        sBPieCharts.add(sBsevendaysago);

        //Summed sanitations
        sSonedayago = findViewById(R.id.SSPieChart1);
        sStwodaysago = findViewById(R.id.SSPieChart2);
        sSthreedaysago = findViewById(R.id.SSPieChart3);
        sSfourdaysago = findViewById(R.id.SSPieChart4);
        sSfivedaysago = findViewById(R.id.SSPieChart5);
        sSsixdaysago = findViewById(R.id.SSPieChart6);
        sSsevendaysago = findViewById(R.id.SSPieChart7);

        sSPieCharts.add(sSonedayago);
        sSPieCharts.add(sStwodaysago);
        sSPieCharts.add(sSthreedaysago);
        sSPieCharts.add(sSfourdaysago);
        sSPieCharts.add(sSfivedaysago);
        sSPieCharts.add(sSsixdaysago);
        sSPieCharts.add(sSsevendaysago);

    }

    public void updateABSevenPieCharts(List<AverageBehaviourItem> averageBehaviourItems){

        for (int i = 0; i < averageBehaviourItems.size(); i++){
            aBSanitized = averageBehaviourItems.get(i).getSanitations();
            aBNotSanitized = averageBehaviourItems.get(i).getLocationChanges() - averageBehaviourItems.get(i).getSanitations();
            aBDate = averageBehaviourItems.get(i).getDate();

            ArrayList<PieEntry> specificDay = new ArrayList<>();
            specificDay.add(new PieEntry(aBSanitized, "Santized"));
            specificDay.add(new PieEntry(aBNotSanitized, "Failed to sanitize"));

            PieDataSet pieDataset = new PieDataSet(specificDay, "");
            pieDataset.setColors(ColorTemplate.MATERIAL_COLORS);
            pieDataset.setValueTextColor(Color.BLACK);
            pieDataset.setValueTextSize(16f);

            PieData firstPieData = new PieData(pieDataset);

            aBPieCharts.get(i).getDescription().setEnabled(false);
            aBPieCharts.get(i).setCenterText(aBDate);

            aBPieCharts.get(i).setData(firstPieData);
            aBPieCharts.get(i).invalidate();
        }
    }

    public void updateSBSevenPieCharts(List<SummedBehavioursItem> summedBehavioursItems){
        for (int i = 0; i < summedBehavioursItems.size(); i++){
            sBSanitized = summedBehavioursItems.get(i).getLocationChangesDidSanitize();
            sBNotSanitized = summedBehavioursItems.get(i).getLocationChangesDidNotSanitize();
            sBDate = summedBehavioursItems.get(i).getDate();

            ArrayList<PieEntry> specificDay = new ArrayList<>();
            specificDay.add(new PieEntry(sBSanitized, "Santized"));
            specificDay.add(new PieEntry(sBNotSanitized, "Failed to sanitize"));

            PieDataSet pieDataset = new PieDataSet(specificDay, "");
            pieDataset.setColors(ColorTemplate.MATERIAL_COLORS);
            pieDataset.setValueTextColor(Color.BLACK);
            pieDataset.setValueTextSize(16f);

            PieData firstPieData = new PieData(pieDataset);

            sBPieCharts.get(i).getDescription().setEnabled(false);
            sBPieCharts.get(i).setCenterText(sBDate);

            sBPieCharts.get(i).setData(firstPieData);
            sBPieCharts.get(i).invalidate();
        }
    }

    public void updateSSSevenPieCharts(List<SummedSanitizersItem> summedSanitizersItems){
        for (int i = 0; i < summedSanitizersItems.size(); i++){
            sSLocationChangeTrue = summedSanitizersItems.get(i).getSanitationLocationChangesTrue();
            getsSLocationChangeFalse = summedSanitizersItems.get(i).getGetSanitationLocationChangesFalse();
            sSDate = summedSanitizersItems.get(i).getDate();

            ArrayList<PieEntry> specificDay = new ArrayList<>();
            specificDay.add(new PieEntry(sSLocationChangeTrue, "At location change"));
            specificDay.add(new PieEntry(getsSLocationChangeFalse, "Random sanitations"));

            PieDataSet pieDataset = new PieDataSet(specificDay, "");
            pieDataset.setColors(ColorTemplate.MATERIAL_COLORS);
            pieDataset.setValueTextColor(Color.BLACK);
            pieDataset.setValueTextSize(16f);

            PieData firstPieData = new PieData(pieDataset);

            sSPieCharts.get(i).getDescription().setEnabled(false);
            sSPieCharts.get(i).setCenterText(sSDate);

            sSPieCharts.get(i).setData(firstPieData);
            sSPieCharts.get(i).invalidate();
        }
    }

    public class aBSevenDaysBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            averageBehaviourItems = bService.returnLastSevenDaysABOverview();
            updateABSevenPieCharts(averageBehaviourItems);
        }
    }

    public class sBSevenDaysBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            summedBehavioursItems = bService.returnLastSevenDaysSBOverview();
            updateSBSevenPieCharts(summedBehavioursItems);
        }
    }

    public class sSSevenDaysBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            summedSanitizersItems = bService.returnLastSevenDaysSSOverview();
            updateSSSevenPieCharts(summedSanitizersItems);
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

                bService.getLastSevenDaysABOverview();
                bService.getLastSevenDaysSBOverview();
                bService.getLastSevenDaysSSOverview();
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

        LocalBroadcastManager.getInstance(this).registerReceiver(new aBSevenDaysBroadcastReceiver(), new IntentFilter(BROADCASTSEVENDAYSAB));
        LocalBroadcastManager.getInstance(this).registerReceiver(new sBSevenDaysBroadcastReceiver(), new IntentFilter(BROADCASTSEVENDAYSSB));
        LocalBroadcastManager.getInstance(this).registerReceiver(new sSSevenDaysBroadcastReceiver(), new IntentFilter(BROADCASTSEVENDAYSSS));
    }
}
