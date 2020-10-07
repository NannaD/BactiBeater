package kathrine.nanna.bactibeater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import RecyclerView.MySpecificLocationAdapter;

import Items.SpecificLocationSanitizeItem;

public class SpecificLocationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MySpecificLocationAdapter mySpecificLocationAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //TextViews, Lists, etc.
    private List<SpecificLocationSanitizeItem> specificLocationSanitizeItems;

    //Buttons
    private Button goBackB;
    private Button exitB;

    public SpecificLocationActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_location);

        //Adding fictional data to specificlocationsanitizeitem
        SpecificLocationSanitizeItem item1 = new SpecificLocationSanitizeItem("13/5","78%","155","120");
        SpecificLocationSanitizeItem item2 = new SpecificLocationSanitizeItem("12/5","89%","170","159");
        SpecificLocationSanitizeItem item3 = new SpecificLocationSanitizeItem("11/5","69%","189","153");
        SpecificLocationSanitizeItem item4 = new SpecificLocationSanitizeItem("10/5","12%","135","110");

        //Adding to list to test recyclerview
        specificLocationSanitizeItems = new ArrayList<>();
        specificLocationSanitizeItems.add(item1);
        specificLocationSanitizeItems.add(item2);
        specificLocationSanitizeItems.add(item3);
        specificLocationSanitizeItems.add(item4);

        //UI and widgets
        recyclerView = findViewById(R.id.specificSanitizeDataRV);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //testing recyclerview
        testRecyclerView();

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

    private void testRecyclerView() {
        mySpecificLocationAdapter = new MySpecificLocationAdapter(specificLocationSanitizeItems, SpecificLocationActivity.this);
        recyclerView.setAdapter(mySpecificLocationAdapter);
        mySpecificLocationAdapter.notifyDataSetChanged();
    }
}
