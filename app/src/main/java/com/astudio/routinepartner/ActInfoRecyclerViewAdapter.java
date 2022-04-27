package com.astudio.routinepartner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActInfoRecyclerViewAdapter extends RecyclerView.Adapter<ActInfoRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ActInfoItem> ActInfoList = new ArrayList<ActInfoItem>();
    Context Context;

    public ActInfoRecyclerViewAdapter(Context context) {
        this.Context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        int Index;
        Button BtnEdit;

        public ViewHolder(View view) {
            super(view);
            BtnEdit = view.findViewById(R.id.btnEdit);
            BtnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(Context, Integer.toString(Index), Toast.LENGTH_SHORT).show();
                    ActInfoDB.DatabaseWriteExecutor.execute(() -> {
                        ActInfoDB db = ActInfoDB.getDatabase(Context);
                        ActInfoDAO mActInfoDao = db.actInfoDao();
                        mActInfoDao.deleteByActId(ActInfoList.get(Index).ItemId);
                    });
                    Toast.makeText(Context, "id" + Integer.toString(Index) + "deleted", Toast.LENGTH_SHORT).show();
                    delItem(Index);
                }
            });
        }

        public void setItem(ActInfoItem item, int position) {
            Index = position;
            BtnEdit.setText(item.Category + "   id : " + Integer.toString(item.ItemId) + "\n" + Integer.toString(item.Year) + " - " + Integer.toString(item.Month) + " - " + Integer.toString(item.Date) + "\n" + Integer.toString(item.StartHour) + " : " + Integer.toString(item.StartMinute) + " ~ " + Integer.toString(item.EndHour) + " : " + Integer.toString(item.EndMinute));

        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_info_item, viewGroup, false);
        return new ViewHolder(view);
    }

    public void addItem(ActInfoItem item) {
        ActInfoList.add(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        ActInfoItem item = ActInfoList.get(position);
        viewHolder.setItem(item, position);
    }

    public int getItemCount() { return ActInfoList.size(); }

    public ActInfoItem getItem(int position) { return ActInfoList.get(position); }

    public void delItem(int position) {
        ActInfoList.remove(position);
        notifyDataSetChanged();
    }

    public void clearView() {
        ActInfoList.clear();
        notifyDataSetChanged();
    }
}