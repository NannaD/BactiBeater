package kathrine.nanna.bactibeater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

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

import Service.BackgroundService;

public class SignInActivity extends AppCompatActivity {

    private static final String BROADCASTTEST = "test";
    private TextView testTb;
    private BackgroundService bService;
    private boolean bound;

    //Buttons
    private Button signInB;
    private Button exitB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalBroadcastManager.getInstance(this).registerReceiver(new SignInActivity.ServiceBroadcastReceiver(), new IntentFilter(BROADCASTTEST));

        testTb = findViewById(R.id.usernameTB);

        setupConnectionToService();
        bindToService();

        //Set up buttons
        signInB = findViewById(R.id.signInB);
        exitB = findViewById(R.id.exitB);

        //Button functionality
        signInB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, OverviewActivity.class);
                startActivity(intent);
            }
        });

        exitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                SignInActivity.this.finish();
                System.exit(0);
            }
        });
    }

    //Recieving broadcasts
    public class ServiceBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            testTb.setText("test");
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
