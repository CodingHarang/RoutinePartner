package com.astudio.routinepartner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
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
        ActInfoItem ContainingItem;
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
                    Button BtnCancel = dialogView.findViewById(R.id.btnCancel);
                    NumberPicker[] NumPickers = new NumberPicker[10];
                    YJS.numPickerSetting(dialogView, NumPickers);
                    EdtCategory.setText(ContainingItem.Category);
                    NumPickers[0].setValue(ContainingItem.StartHour < 12 ? 0 : 1);
                    NumPickers[1].setValue(ContainingItem.EndHour < 12 ? 0 : 1);
                    NumPickers[2].setValue(ContainingItem.StartHour < 12 ? ContainingItem.StartHour : ContainingItem.StartHour - 12);
                    NumPickers[3].setValue(ContainingItem.EndHour < 12 ? ContainingItem.EndHour : ContainingItem.EndHour - 12);
                    NumPickers[4].setValue(ContainingItem.StartMinute);
                    NumPickers[5].setValue(ContainingItem.EndMinute);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivityContext);
                    alertDialogBuilder.setView(dialogView);
                    AlertDialog alertDialog;
                    alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    BtnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ContainingItem.Category = EdtCategory.getText().toString();
                            ContainingItem.StartHour = NumPickers[0].getValue() == 0 ? NumPickers[2].getValue() : NumPickers[2].getValue() + 12;
                            ContainingItem.EndHour = NumPickers[1].getValue() == 0 ? NumPickers[3].getValue() : NumPickers[3].getValue() + 12;
                            ContainingItem.StartMinute = NumPickers[4].getValue();
                            ContainingItem.EndMinute = NumPickers[5].getValue();
                            ActInfoDB.DatabaseWriteExecutor.execute(() -> {
                                ActInfoDB db = ActInfoDB.getDatabase(parent.getContext());
                                ActInfoDAO mActInfoDao = db.actInfoDao();
                                ActInfo actInfo = new ActInfo();
                                mActInfoDao.deleteByActId(ActInfoList.get(Index).ItemId);
                                actInfo.setCategory(ContainingItem.Category);
                                actInfo.setYear(ContainingItem.Year);
                                actInfo.setMonth(ContainingItem.Month);
                                actInfo.setDate(ContainingItem.Date);
                                actInfo.setStartHour(ContainingItem.StartHour);
                                actInfo.setStartMinute(ContainingItem.StartMinute);
                                actInfo.setEndHour(ContainingItem.EndHour);
                                actInfo.setEndMinute(ContainingItem.EndMinute);
                                mActInfoDao.insert(actInfo);
                            });
                            BtnEdit.setText(ContainingItem.Category + "   id : " + Integer.toString(ContainingItem.ItemId) + "\n" + Integer.toString(ContainingItem.Year) + " - " + Integer.toString(ContainingItem.Month) + " - " + Integer.toString(ContainingItem.Date) + "\n" + Integer.toString(ContainingItem.StartHour) + " : " + Integer.toString(ContainingItem.StartMinute) + " ~ " + Integer.toString(ContainingItem.EndHour) + " : " + Integer.toString(ContainingItem.EndMinute));
                            alertDialog.dismiss();
                        }
                    });

                    BtnCancel.setOnClickListener(new View.OnClickListener() {
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

        public void setItem(ActInfoItem item, int position, ViewHolder viewHolder) {
            Index = position;
            ContainingItem = item;
            BtnEdit.setText(item.Category + "   id : " + Integer.toString(item.ItemId) + "\n" + Integer.toString(item.Year) + " - " + Integer.toString(item.Month) + " - " + Integer.toString(item.Date) + "\n" + Integer.toString(item.StartHour) + " : " + Integer.toString(item.StartMinute) + " ~ " + Integer.toString(item.EndHour) + " : " + Integer.toString(item.EndMinute));
            int CategoryNum = SavedSettings.CategoryList.size();
            for(int i = 0; i < 5; i++) {
                if(i == 0 && i < CategoryNum) {
                    if(ContainingItem.Category.equals(SavedSettings.CategoryList.get(0))) {
                        BtnEdit.setBackgroundResource(R.drawable.round_square1);
                        BtnEdit.setTextColor(0XFF000000);
                        BtnDelete.setBackgroundResource(R.drawable.round_square1);
                    }
                }
                if(i == 1 && i < CategoryNum) {
                    if(ContainingItem.Category.equals(SavedSettings.CategoryList.get(1)) && CategoryNum > 1) {
                        BtnEdit.setBackgroundResource(R.drawable.round_square2);
                        BtnEdit.setTextColor(0XFF000000);
                        BtnDelete.setBackgroundResource(R.drawable.round_square2);
                    }
                }
                if(i == 2 && i < CategoryNum) {
                    if(ContainingItem.Category.equals(SavedSettings.CategoryList.get(2)) && CategoryNum > 2) {
                        BtnEdit.setBackgroundResource(R.drawable.round_square3);
                        BtnEdit.setTextColor(0XFF000000);
                        BtnDelete.setBackgroundResource(R.drawable.round_square3);
                    }
                }
                if(i == 3 && i < CategoryNum) {
                    if(ContainingItem.Category.equals(SavedSettings.CategoryList.get(3)) && CategoryNum > 3){
                        BtnEdit.setBackgroundResource(R.drawable.round_square4);
                        BtnEdit.setTextColor(0XFF000000);
                        BtnDelete.setBackgroundResource(R.drawable.round_square4);
                    }
                }
                if(i == 4 && i < CategoryNum) {
                    if(ContainingItem.Category.equals(SavedSettings.CategoryList.get(4)) && CategoryNum > 4){
                        BtnEdit.setBackgroundResource(R.drawable.round_square5);
                        BtnEdit.setTextColor(0XFF000000);
                        BtnDelete.setBackgroundResource(R.drawable.round_square5);
                    }
                }
            }
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
        viewHolder.setItem(item, position, viewHolder);
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