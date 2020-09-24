package kathrine.nanna.bactibeater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OverviewActivity extends AppCompatActivity {

    //Buttons
    private Button goBackB;
    private Button exitB;
    private Button goToChooseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        //UI's and widges setup
        goBackB = findViewById(R.id.goBackB);
        exitB = findViewById(R.id.exitB);
        goToChooseActivity = findViewById(R.id.specificDataB);

        //Button functionality

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
                finish();
                Intent intent = new Intent(OverviewActivity.this, SignInActivity.class);
                startActivity(intent);
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
}
