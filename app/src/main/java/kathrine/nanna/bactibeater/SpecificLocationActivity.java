package kathrine.nanna.bactibeater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

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
    private static final String LOCATIONNAME = "locationName";

    //TextViews, Lists, etc.
    private List<SanitizeItem> specificLocationItems;

    //Buttonss
    private Button goBackB;
    private Button exitB;

    public SpecificLocationActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_location);

        //UI and widgets
        recyclerView = findViewById(R.id.specificSanitizeDataRV);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //UI's and widges setup
        goBackB = findViewById(R.id.goBackB);
        exitB = findViewById(R.id.exitB);

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
            specificLocationItems = bService.returnLocationSpecificData();
            mySpecificLocationAdapter.updateRecyclerview(specificLocationItems);
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
                String locationName = intent.getStringExtra(LOCATIONNAME);

                bService.getLocationAndDateSpecificData(locationName);

                mySpecificLocationAdapter = new MySpecificLocationAdapter(specificLocationItems, SpecificLocationActivity.this);
                recyclerView.setAdapter(mySpecificLocationAdapter);
                mySpecificLocationAdapter.notifyDataSetChanged();
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
    protected void onStart() {
        super.onStart();

        setupConnectionToService();
        bindToService();
    }


}
