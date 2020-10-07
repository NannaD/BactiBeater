package RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Items.SpecificLocationSanitizeItem;
import kathrine.nanna.bactibeater.R;

public class MySpecificLocationAdapter extends RecyclerView.Adapter<MySpecificLocationAdapter.MyViewHolder> {

    private List<SpecificLocationSanitizeItem> specificLocationSanitizeItems;
    private Context context;

    public MySpecificLocationAdapter(List<SpecificLocationSanitizeItem> specificLocationSanitizeItems, Context context) {
        this.specificLocationSanitizeItems = specificLocationSanitizeItems;
        this.context = context;
    }

    @Override
    public MySpecificLocationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.locationitem, parent, false);
        return new MySpecificLocationAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MySpecificLocationAdapter.MyViewHolder holder, int position) {
        SpecificLocationSanitizeItem specificLocationSanitizeItem = specificLocationSanitizeItems.get(position);

        holder.dateTV.setText(specificLocationSanitizeItem.getDate());
        holder.procentageTV.setText(specificLocationSanitizeItem.getProcentage());
        holder.visitorsTV.setText(specificLocationSanitizeItem.getVisitorCount());
        holder.sanitizeTV.setText(specificLocationSanitizeItem.getSanitizeCount());
    }

    @Override
    public int getItemCount() {
        return specificLocationSanitizeItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView dateTV;
        public TextView procentageTV;
        public TextView visitorsTV;
        public TextView sanitizeTV;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTV = itemView.findViewById(R.id.dateTV);
            procentageTV = itemView.findViewById(R.id.procentageTV);
            visitorsTV = itemView.findViewById(R.id.visitorCountTV);
            sanitizeTV = itemView.findViewById(R.id.sanitizeCountTV);
        }
    }
}
