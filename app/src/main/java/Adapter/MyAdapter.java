/*package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kathrine.nanna.bactibeater.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    List<Location> locations;

    public MyAdapter(List<Location> locations, Context context) {
        this.locations = locations;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.locationitem, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Location location = locations.get(position);

        holder.locationTV.setText(location.getName());
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView locationTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            locationTV = itemView.findViewById(R.id.locationTV);
        }
    }


} */
