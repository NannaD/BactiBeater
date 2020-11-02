package kathrine.nanna.bactibeater;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import androidx.appcompat.app.AlertDialog;

import APIConnection.Callback;
import Service.BackgroundService;

public class SignInActivity extends AppCompatActivity {

    private static final String BROADCASTTEST = "test";
    private BackgroundService bService;
    private boolean bound;

    //TextViews
    private TextView usernameTB;
    private TextView passwordTB;

    //Buttons
    private Button signInB;
    private Button exitB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up textviews
        usernameTB = findViewById(R.id.usernameTB);
        passwordTB = findViewById(R.id.passwordTB);

        //Set up buttons
        signInB = findViewById(R.id.signInB);
        exitB = findViewById(R.id.exitB);

        //Button functionality
        signInB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameTB.getText().length() != 0 && passwordTB.getText().length() != 0)
                {
                    bService.SignIn(usernameTB.getText().toString(), passwordTB.getText().toString(), new Callback() {
                        @Override
                        public void onSuccess(Object result) {
                            finish();
                            Intent intent = new Intent(SignInActivity.this, OverviewActivity.class);
                            startActivity(intent);
                        }
                    });
                }
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

    //Dialog to be showed if the wrong username or password is typed
    public void showAlertDialogEmptyText() {
        //Kilde: https://stackoverflow.com/questions/43513919/android-alert-dialog-with-one-two-and-three-buttons
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.message_box_title);
        builder.setMessage(R.string.please_fill_in_both_textviews);

        // add a button
        builder.setPositiveButton("OK", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showAlertDialogWrongUsernameOrPassword() {
        //Kilde: https://stackoverflow.com/questions/43513919/android-alert-dialog-with-one-two-and-three-buttons
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.message_box_title);
        builder.setMessage(R.string.please_fill_in_right_information);

        // add a button
        builder.setPositiveButton("OK", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
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
    private void startMyService(){
        Intent serviceIntent = new Intent(SignInActivity.this, BackgroundService.class);
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

        LocalBroadcastManager.getInstance(this).registerReceiver(new SignInActivity.ServiceBroadcastReceiver(), new IntentFilter(BROADCASTTEST));
    }
}


