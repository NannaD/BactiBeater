package RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kathrine.nanna.bactibeater.ChooseLocationActivity;
import kathrine.nanna.bactibeater.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private List<String> locationItems;
    private Context context;
    onLocationClickListener monLocationClickListener;

    public MyAdapter(List<String> locationItems, Context context, onLocationClickListener monLocationClickListener) {
        this.locationItems = locationItems;
        this.context = context;
        this.monLocationClickListener = monLocationClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.locationitem, parent, false);
        return new MyViewHolder(v, monLocationClickListener);
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

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView locationTV;
        onLocationClickListener _onLocationClickListener;

        public MyViewHolder(@NonNull View itemView, onLocationClickListener _onLocationClickListener) {
            super(itemView);

            this._onLocationClickListener = _onLocationClickListener;
            itemView.setOnClickListener(this);

            locationTV = itemView.findViewById(R.id.locationTV);
        }

        @Override
        public void onClick(View v) {
            _onLocationClickListener.onLocationClickListener(getAdapterPosition());
        }
    }
    public interface onLocationClickListener {
        void onLocationClickListener(int position);
    }

    public void updateRecyclerview(List<String> locationNames)
    {
        locationItems.clear();
        locationItems.addAll(locationNames);
        notifyDataSetChanged();
    }
}
