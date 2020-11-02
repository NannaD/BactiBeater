package kathrine.nanna.bactibeater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Service.BackgroundService;

public class OverviewActivity extends AppCompatActivity {

    //Buttons and widgets
    private Button goBackB;
    private Button exitB;
    private Button goToChooseActivity;
    private Button goToSevenDayOverview;

    private PieChart overviewPieChart;
    private TextView dateTV;

    private BackgroundService bService;
    private boolean bound;
    int didSanitize;
    int didNotSanitize;

    private static final String OVERVIEWCHARTDATA = "overviewPieData";
    private static final String DIDSANITIZE = "didSanitize";
    private static final String DIDNOTSANITIZE = "didNotSanitize";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        //UI's and widges setup
        goBackB = findViewById(R.id.goBackB);
        exitB = findViewById(R.id.exitB);
        goToChooseActivity = findViewById(R.id.specificDataB);
        goToSevenDayOverview = findViewById(R.id.sevenDaysOverviewButton);

        overviewPieChart = findViewById(R.id.overviewPieChart);
        dateTV = findViewById(R.id.currentDateTV);

        //saving information during rotation
        if (savedInstanceState != null) {
            didSanitize = savedInstanceState.getInt(DIDSANITIZE);
            didNotSanitize = savedInstanceState.getInt(DIDNOTSANITIZE);
            updateOverviewPieChart(didSanitize, didNotSanitize);
        }

        //Setting the date in overview activity
        dateTV.setText(SimpleDateFormat.getDateInstance().format(Calendar.getInstance().getTime()));

        //Button functionality for navigation
        goToChooseActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(OverviewActivity.this, ChooseLocationActivity.class);
                startActivity(intent);
            }
        });

        goToSevenDayOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(OverviewActivity.this, SevendaysOverviewActivity.class);
                startActivity(intent);
            }
        });

        goBackB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(OverviewActivity.this, SignInActivity.class);
                startActivity(intent);

                //bService.getBehaviourModels(); //Hvad gør denne, hilsen kat? Eller mere, skal den bruges her?
            }
        });

        exitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OverviewActivity.this.finish();
                System.exit(0);
            }
        });
    }

    //Kilder til piechart:
    // https://www.youtube.com/watch?v=vhKtbECeazQ&t=798s
    // https://www.anychart.com/technical-integrations/samples/android-charts/
    public void updateOverviewPieChart(int didSanitize, int didNotSanitize){
        ArrayList<PieEntry> visitorsSanitationToday = new ArrayList<>();
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
        overviewPieChart.invalidate();
    }

    //Recieving broadcasts
    public class ChartDataBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            didSanitize = bService.returnSanitizedDataForOverview();
            didNotSanitize = bService.returnDidNotSanitizeDataForOverview();
            updateOverviewPieChart(didSanitize, didNotSanitize);
        }
    }

    //Setting up connection to service
    private ServiceConnection connection;
    private void setupConnectionToService() {
        connection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName className, IBinder service) {
                // We've bound to LocalService, cast the IBinder and get LocalService instance
                bService = new BackgroundService();

                bService = ((BackgroundService.LocalBinder)service).getService();
                bound = true;

                bService.getAPIData();
            }

            @Override
            public void onServiceDisconnected(ComponentName arg0) {
                bService = null;
                bound = false;
            }
        };
    }

    //Bind to Background service, learned how to from https://developer.android.com/guide/components/bound-services
    void bindToService() {
        Intent intent = new Intent(this, BackgroundService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        bound = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(DIDSANITIZE, didSanitize);
        outState.putSerializable(DIDNOTSANITIZE, didNotSanitize);
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

        //Setup backgroundservice connection
        setupConnectionToService();
        bindToService();

        //Set up broadcastmanagers
        LocalBroadcastManager.getInstance(this).registerReceiver(new OverviewActivity.ChartDataBroadcastReceiver(), new IntentFilter(OVERVIEWCHARTDATA));
    }
}
