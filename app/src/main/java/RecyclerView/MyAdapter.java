package RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kathrine.nanna.bactibeater.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private List<String> locationItems;
    private Context context;

    public MyAdapter(List<String> locationItems, Context context) {
        this.locationItems = locationItems;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.locationitem, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String locationItem = locationItems.get(position);

        holder.locationTV.setText(locationItem);
    }

    @Override
    public int getItemCount() {
        return locationItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView locationTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            locationTV = itemView.findViewById(R.id.locationTV);
        }
    }
}
