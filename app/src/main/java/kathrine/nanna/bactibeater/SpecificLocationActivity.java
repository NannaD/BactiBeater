package kathrine.nanna.bactibeater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SpecificLocationActivity extends AppCompatActivity {

    //Buttons
    private Button goBackB;
    private Button exitB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_location);

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
}
