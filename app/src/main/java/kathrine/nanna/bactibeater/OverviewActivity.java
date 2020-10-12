package kathrine.nanna.bactibeater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import Service.BackgroundService;

public class OverviewActivity extends AppCompatActivity {

    //Buttons and widgets
    private Button goBackB;
    private Button exitB;
    private Button goToChooseActivity;
    private PieChart overviewPieChart;

    private BackgroundService bService;
    private boolean bound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        //UI's and widges setup
        goBackB = findViewById(R.id.goBackB);
        exitB = findViewById(R.id.exitB);
        goToChooseActivity = findViewById(R.id.specificDataB);
        overviewPieChart = findViewById(R.id.overviewPieChart);

        //Setup backgroundservice connection
        setupConnectionToService();
        bindToService();

        //Button functionality for navigation
        goToChooseActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(OverviewActivity.this, ChooseLocationActivity.class);
                startActivity(intent);
            }
        });

        goBackB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*finish();
                Intent intent = new Intent(OverviewActivity.this, SignInActivity.class);
                startActivity(intent);

                 */
                bService.APITest();
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

        updateOverviewPieChart();
    }

    public void updateOverviewPieChart(){
        ArrayList<PieEntry> visitorsSanitationToday = new ArrayList<>();
        visitorsSanitationToday.add(new PieEntry(33, "Sanitized"));
        visitorsSanitationToday.add(new PieEntry(44, "Failed to sanitize"));

        PieDataSet pieDataSet = new PieDataSet(visitorsSanitationToday, "Visitors and sanitation");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        overviewPieChart.setData(pieData);
        overviewPieChart.getDescription().setEnabled(false);
        overviewPieChart.setCenterText("Visitors and sanitation");
        overviewPieChart.animate();
    }

    //Recieving broadcasts
    public class ServiceBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
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
}
