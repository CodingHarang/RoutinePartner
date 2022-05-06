package com.astudio.routinepartner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActInfoRecyclerViewAdapter extends RecyclerView.Adapter<ActInfoRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ActInfoItem> ActInfoList = new ArrayList<ActInfoItem>();
    Context Context, ActivityContext;
    ViewGroup parent;

    public ActInfoRecyclerViewAdapter(Context context) {
        this.Context = context;
    }

    public void setActivityContext(Context context) {
        ActivityContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        int Index;
        Button BtnEdit;
        ImageButton BtnDelete;
        public ViewHolder(View view) {
            super(view);
            BtnEdit = view.findViewById(R.id.btnEditItem);
            BtnDelete = view.findViewById(R.id.btnDeleteItem);

            BtnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(Context, "clicked" + ActivityContext, Toast.LENGTH_SHORT).show();
                    View dialogView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_add, null);
                    EditText EdtCategory = dialogView.findViewById(R.id.edtCategory);
                    Button BtnOK = dialogView.findViewById(R.id.btnOK);
                    NumberPicker[] NumPickers = new NumberPicker[10];
                    YJS.numPickerSetting(dialogView, NumPickers);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivityContext);
                    alertDialogBuilder.setView(dialogView);
                    AlertDialog alertDialog;
                    alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    BtnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                }
            });
            BtnDelete.setOnClickListener(new View.OnClickListener() {
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
        if(parent == null) {
            parent = viewGroup;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        ActInfoItem item = ActInfoList.get(position);
        viewHolder.setItem(item, position);
    }

    public void addItem(ActInfoItem item) {
        ActInfoList.add(item);
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