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

import java.util.ArrayList;
import java.util.List;

import RecyclerView.MyAdapter;
import Service.BackgroundService;

public class ChooseLocationActivity extends AppCompatActivity implements MyAdapter.onLocationClickListener {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //TextViews, Lists, etc.
    private List<String> locationNames;
    private BackgroundService bService;
    private boolean bound;

    //Buttons
    private Button exitB;

    //Strings
    private static final String LOCATIONNAME = "locationName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);

        //Adding to list to test recyclerview
        locationNames = new ArrayList<>();
        //locationItems.add("Stue 1");
        //locationItems.add("Stue 2");
        //locationItems.add("Stue 3");

        //UI and widgets
        recyclerView = findViewById(R.id.locationRV);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        exitB = findViewById(R.id.exitB);

        //Button functionality
        exitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                ChooseLocationActivity.this.finish();
                System.exit(0);
            }
        });
    }

    @Override
    public void onLocationClickListener(int position) {
        String locationNavigation = locationNames.get(position);

        finish();
        Intent intent = new Intent(ChooseLocationActivity.this, SpecificLocationActivity.class);
        intent.putExtra(LOCATIONNAME, locationNavigation);
        startActivity(intent);
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

                locationNames = bService.returnAllLocations();

                myAdapter = new MyAdapter(locationNames, ChooseLocationActivity.this, ChooseLocationActivity.this);
                recyclerView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
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

        setupConnectionToService();
        bindToService();

        //LocalBroadcastManager.getInstance(this).registerReceiver(new ChooseLocationActivity.LocationsBroadcastReceiver(), new IntentFilter(LOCATIONS));
    }
}
