package kathrine.nanna.bactibeater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import RecyclerView.MySpecificLocationAdapter;

import Items.SanitizeItem;
import Service.BackgroundService;

public class SpecificLocationActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private MySpecificLocationAdapter mySpecificLocationAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private BackgroundService bService;
    private boolean bound;
    private static final String LOCATIONSPECIFICDATA = "locationSpecificData";
    private static final String SANITIZEITEMSROTATION = "SanitizeItemsRotation";

    //TextViews, Lists, etc.
    private List<SanitizeItem> sanitizeItems;

    //Buttons
    private Button goBackB;
    private Button exitB;
    private TextView specificLocationTV;

    public SpecificLocationActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_location);

        //UI and widgetss
        recyclerView = findViewById(R.id.specificSanitizeDataRV);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //UI's and widges setup
        goBackB = findViewById(R.id.goBackB);
        exitB = findViewById(R.id.exitB);
        specificLocationTV = findViewById(R.id.specificLocationTV);

        //saving information during rotation
        if (savedInstanceState != null) {
            sanitizeItems = (List<SanitizeItem>) savedInstanceState.getSerializable(SANITIZEITEMSROTATION);
            if (mySpecificLocationAdapter != null){
                mySpecificLocationAdapter.updateRecyclerview(sanitizeItems);
            }
        } else {
            sanitizeItems = new ArrayList<>();
        }

        //Button functionality
        goBackB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(SpecificLocationActivity.this, ChooseLocationActivity.class);
                startActivity(intent);
            }
        });

        exitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                SpecificLocationActivity.this.finish();
                System.exit(0);
            }
        });

    }

    //Recieving broadcasts
    public class LocationsBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            sanitizeItems = bService.returnLocationSpecificData();

            mySpecificLocationAdapter = new MySpecificLocationAdapter(sanitizeItems, SpecificLocationActivity.this);
            recyclerView.setAdapter(mySpecificLocationAdapter);
            mySpecificLocationAdapter.notifyDataSetChanged();
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

                Intent intent = getIntent();
                String locationName = intent.getStringExtra("locationName");

                specificLocationTV.setText(locationName);
                bService.getLocationAndDateSpecificData(locationName);
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

    void unBindFromService() {
        if (bound) {
            // Detach our existing connection.
            unbindService(connection);
            bound = false;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(SANITIZEITEMSROTATION, (Serializable)sanitizeItems);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindFromService();
    }

    @Override
    protected void onStart() {
        super.onStart();

        setupConnectionToService();
        bindToService();

        //Set up broadcastmanagers
        LocalBroadcastManager.getInstance(this).registerReceiver(new SpecificLocationActivity.LocationsBroadcastReceiver(), new IntentFilter(LOCATIONSPECIFICDATA));
    }


}
