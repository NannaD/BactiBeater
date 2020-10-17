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

public class MySpecificLocationAdapter extends RecyclerView.Adapter<MySpecificLocationAdapter.ViewHolder> {

    private List<SpecificLocationSanitizeItem> specificLocationSanitizeItems;
    private Context context;

    public MySpecificLocationAdapter(List<SpecificLocationSanitizeItem> specificLocationSanitizeItems, Context context) {
        this.specificLocationSanitizeItems = specificLocationSanitizeItems;
        this.context = context;
    }

    @Override
    public MySpecificLocationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sanitizespecificlocationitem, parent, false);
        return new MySpecificLocationAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MySpecificLocationAdapter.ViewHolder holder, int position) {
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

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView dateTV;
        public TextView procentageTV;
        public TextView visitorsTV;
        public TextView sanitizeTV;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTV = itemView.findViewById(R.id.dateNowTV);
            procentageTV = itemView.findViewById(R.id.procentageTV);
            visitorsTV = itemView.findViewById(R.id.visitorCountTV);
            sanitizeTV = itemView.findViewById(R.id.sanitizeCountTV);
        }
    }

    public void updateRecyclerview(List<SpecificLocationSanitizeItem> specificLocationSanitizeItems)
    {
        specificLocationSanitizeItems.clear();
        specificLocationSanitizeItems.addAll(specificLocationSanitizeItems);
        notifyDataSetChanged();
    }
}
