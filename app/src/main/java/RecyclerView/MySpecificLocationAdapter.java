package RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Items.SanitizeItem;
import kathrine.nanna.bactibeater.R;

public class MySpecificLocationAdapter extends RecyclerView.Adapter<MySpecificLocationAdapter.ViewHolder> {

    private List<SanitizeItem> sanitizeItems;
    private Context context;

    public MySpecificLocationAdapter(List<SanitizeItem> sanitizeItems, Context context) {
        this.sanitizeItems = sanitizeItems;
        this.context = context;
    }

    @Override
    public MySpecificLocationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sanitizespecificlocationitem, parent, false);
        return new MySpecificLocationAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MySpecificLocationAdapter.ViewHolder holder, int position) {
        SanitizeItem sanitizeItem = sanitizeItems.get(position);

        holder.dateTV.setText(sanitizeItem.getDate());
        holder.visitorsTV.setText(Integer.toString(sanitizeItem.getVisitorCount()));
        holder.sanitizeTV.setText(Integer.toString(sanitizeItem.getSanitizeCount()));
        holder.procentageTV.setText("0");
    }

    @Override
    public int getItemCount() {
        return sanitizeItems.size();
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

    public void updateRecyclerview(List<SanitizeItem> sanitizeItemsList)
    {
        sanitizeItems = sanitizeItemsList;
        sanitizeItems.clear();
        sanitizeItems.addAll(sanitizeItemsList);
        notifyDataSetChanged();
    }
}
