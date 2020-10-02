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

import APIConnection.FirebaseAPIBehaviourConnection;
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

        LocalBroadcastManager.getInstance(this).registerReceiver(new SignInActivity.ServiceBroadcastReceiver(), new IntentFilter(BROADCASTTEST));

        setupConnectionToService();
        bindToService();

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
                //for (int i = 0; i <= users.Count; i++){
                    if(usernameTB.getText().toString().equals("nanna") && passwordTB.getText().toString().equals("banan")){
                        finish();
                        Intent intent = new Intent(SignInActivity.this, OverviewActivity.class);
                        startActivity(intent);
                    }
                    else if(usernameTB.getText().toString().equals("") && passwordTB.getText().toString().equals("")){
                        showAlertDialogEmptyText();
                    }
                    else if(!usernameTB.getText().toString().equals("nanna") || !passwordTB.getText().toString().equals("banan")){
                        showAlertDialogWrongUsernameOrPassword();
                    }
                //}
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
                bService.APITest();
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


