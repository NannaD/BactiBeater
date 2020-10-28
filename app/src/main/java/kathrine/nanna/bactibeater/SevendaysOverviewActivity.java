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
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import Items.SevenDaysOverviewItem;
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

    private BackgroundService bService;
    private boolean bound;
    private List<SevenDaysOverviewItem> sevenDaysOverviewItems = new ArrayList<>();

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

    public void updateSevenPieCharts(List<SevenDaysOverviewItem> sevenDaysOverviewItems){

        /*ArrayList<PieEntry> visitorsSanitationToday = new ArrayList<>();
        visitorsSanitationToday.add(new PieEntry(didSanitize, "Sanitized"));
        visitorsSanitationToday.add(new PieEntry(didNotSanitize, "Failed to sanitize"));

        PieDataSet pieDataSet = new PieDataSet(visitorsSanitationToday, "");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);


        overviewPieChart.getDescription().setEnabled(false);
        overviewPieChart.setCenterText("Visitors and sanitation");
        //NinoverviewPieChart.animate();

        overviewPieChart.setData(pieData);
        overviewPieChart.invalidate(); */
    }

    public class SevenDaysBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            sevenDaysOverviewItems = bService.returnLastSevenDaysOverview();
            updateSevenPieCharts(sevenDaysOverviewItems);
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
        startService(intent);
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
    protected void onStop() {
        super.onStop();
        unBindFromService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
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
