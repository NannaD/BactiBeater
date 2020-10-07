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

import RecyclerView.MyAdapter;

public class ChooseLocationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //TextViews, Lists, etc.
    private List<String> locationItems;

    //Buttons
    private Button exitB;
    private Button videreB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);

        //Adding to list to test recyclerview
        locationItems = new ArrayList<>();
        locationItems.add("Stue 1");
        locationItems.add("Stue 2");
        locationItems.add("Stue 3");

        //UI and widgets
        recyclerView = findViewById(R.id.locationRV);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        exitB = findViewById(R.id.exitB);
        videreB = findViewById(R.id.videreB);

        //testing recyclerview
        testRecyclerView();

        //Button functionality
        exitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                ChooseLocationActivity.this.finish();
                System.exit(0);
            }
        });
        videreB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(ChooseLocationActivity.this, SpecificLocationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void testRecyclerView(){
        myAdapter = new MyAdapter(locationItems, ChooseLocationActivity.this);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }
}
