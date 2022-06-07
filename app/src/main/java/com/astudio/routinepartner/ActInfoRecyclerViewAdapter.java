package com.astudio.routinepartner;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ActInfoRecyclerViewAdapter extends RecyclerView.Adapter<ActInfoRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ActInfoItem> ActInfoList = new ArrayList<ActInfoItem>();
    Context Context, ActivityContext;
    ViewGroup parent;
    SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);

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
                    Calendar cal = Calendar.getInstance();
                    int Year, Month, Date;
                    Year = ContainingItem.Year;
                    Month = ContainingItem.Month;
                    Date = ContainingItem.Date;
                    cal.set(Calendar.YEAR, Year);
                    cal.set(Calendar.MONTH, Month - 1);
                    cal.set(Calendar.DATE, Date);
                    View dialogView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_add, null);
                    EditText EdtCategory = dialogView.findViewById(R.id.edtCategory);
                    Button BtnCalendar = dialogView.findViewById(R.id.btnCalendar);
                    BtnCalendar.setText(SDF.format(cal.getTime()));
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
                    DatePickerDialog.OnDateSetListener DatePickerDiag = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            cal.set(Calendar.YEAR, year);
                            cal.set(Calendar.MONTH, month);
                            cal.set(Calendar.DAY_OF_MONTH, day);
                            BtnCalendar.setText(SDF.format(cal.getTime()));
                        }
                    };
                    BtnCalendar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new DatePickerDialog(parent.getContext(), R.style.MyDatePickerStyle, DatePickerDiag, Year, Month - 1, Date).show();
                        }
                    });
                    BtnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int SAMPM = NumPickers[0].getValue();
                            int Shour = NumPickers[2].getValue();
                            int Sminute = NumPickers[4].getValue();
                            int EAMPM = NumPickers[1].getValue();
                            int Ehour = NumPickers[3].getValue();
                            int Eminute = NumPickers[5].getValue();
                            if(SAMPM == 0) {
                                if(Shour == 12) Shour = 0;
                            } else {
                                if(Shour == 12) Shour = 12;
                                else Shour += 12;
                            }
                            if(EAMPM == 0) {
                                if(Ehour == 12) {
                                    if(Eminute == 0) Ehour = 24;
                                    else Ehour = 0;
                                }
                            } else {
                                if(Ehour == 12) Ehour = 12;
                                else Ehour += 12;
                            }
                            if(Shour > Ehour || (Shour == Ehour && Sminute > Eminute)) {
                                Toast.makeText(Context, "Invalid Time", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ContainingItem.Category = EdtCategory.getText().toString();
                            ContainingItem.Year = cal.get(Calendar.YEAR);
                            ContainingItem.Month = cal.get(Calendar.MONTH) + 1;
                            ContainingItem.Date = cal.get(Calendar.DATE);
                            ContainingItem.StartHour = NumPickers[0].getValue() == 0 ? (NumPickers[2].getValue() == 12 ? 0 : NumPickers[2].getValue()) : (NumPickers[2].getValue() == 12 ? 12 : NumPickers[2].getValue() + 12);
                            ContainingItem.EndHour = NumPickers[1].getValue() == 0 ? (NumPickers[3].getValue() == 12 ? 0 : NumPickers[3].getValue()) : (NumPickers[3].getValue() == 12 ? 12 : NumPickers[3].getValue() + 12);
                            ContainingItem.StartMinute = NumPickers[4].getValue();
                            ContainingItem.EndMinute = NumPickers[5].getValue();
                            ActInfoDB db = ActInfoDB.getDatabase(parent.getContext());
                            ActInfoDAO mActInfoDao = db.actInfoDao();
                            ActInfo actInfo = new ActInfo();
                            actInfo.setCategory(ContainingItem.Category);
                            actInfo.setYear(ContainingItem.Year);
                            actInfo.setMonth(ContainingItem.Month);
                            actInfo.setDate(ContainingItem.Date);
                            actInfo.setStartHour(ContainingItem.StartHour);
                            actInfo.setStartMinute(ContainingItem.StartMinute);
                            actInfo.setEndHour(ContainingItem.EndHour);
                            actInfo.setEndMinute(ContainingItem.EndMinute);
                            mActInfoDao.updateData(ContainingItem.ItemId, ContainingItem.Category, ContainingItem.Year, ContainingItem.Month, ContainingItem.Date, ContainingItem.StartHour, ContainingItem.StartMinute, ContainingItem.EndHour, ContainingItem.EndMinute);

                            BtnEdit.setText(ContainingItem.Category + "\n" + Integer.toString(ContainingItem.Year) + " - " + Integer.toString(ContainingItem.Month) + " - " + Integer.toString(ContainingItem.Date) + "\n" + Integer.toString(ContainingItem.StartHour) + " : " + Integer.toString(ContainingItem.StartMinute) + " ~ " + Integer.toString(ContainingItem.EndHour) + " : " + Integer.toString(ContainingItem.EndMinute));
                            alertDialog.dismiss();
                            updateView();
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
                    ActInfoDB db = ActInfoDB.getDatabase(Context);
                    ActInfoDAO mActInfoDao = db.actInfoDao();
                    mActInfoDao.deleteByActId(ActInfoList.get(Index).ItemId);
                    delItem(Index);
                }
            });

        }

        public void setItem(ActInfoItem item, int position) {
            Index = position;
            ContainingItem = item;
            BtnEdit.setText(item.Category + "\n" + Integer.toString(item.Year) + " - " + Integer.toString(item.Month) + " - " + Integer.toString(item.Date) + "\n" + Integer.toString(item.StartHour) + " : " + Integer.toString(item.StartMinute) + " ~ " + Integer.toString(item.EndHour) + " : " + Integer.toString(item.EndMinute));
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
    public void updateView() {
        notifyDataSetChanged();
    }
}