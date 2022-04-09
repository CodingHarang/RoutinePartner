package com.cookandroid.routinepartner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActInfoRecyclerViewAdapter extends RecyclerView.Adapter<ActInfoRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ActInfoItem> actInfoList = new ArrayList<ActInfoItem>();
    Context context;

    public ActInfoRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        int index;
        Button btnEdit;

        public ViewHolder(View view) {
            super(view);
            btnEdit = view.findViewById(R.id.btnEdit);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, Integer.toString(index), Toast.LENGTH_SHORT).show();
                    delItem(index);
                }
            });
        }

        public void setItem(ActInfoItem item, int position) {
            index = position;
            btnEdit.setText(item.Category + "\n" + Integer.toString(item.Year) + " - " + Integer.toString(item.Month) + " - " + Integer.toString(item.Date) + "\n" + Integer.toString(item.StartHour) + " : " + Integer.toString(item.StartMinute) + " ~ " + Integer.toString(item.EndHour) + " : " + Integer.toString(item.EndMinute));

        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_info_item, viewGroup, false);
        return new ViewHolder(view);
    }

    public void addItem(ActInfoItem item) {
        actInfoList.add(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        ActInfoItem item = actInfoList.get(position);
        viewHolder.setItem(item, position);
    }

    public int getItemCount() { return actInfoList.size(); }

    public ActInfoItem getItem(int position) { return actInfoList.get(position); }

    public void delItem(int position) {
        actInfoList.remove(position);
        notifyDataSetChanged();
    }
}
