package com.astudio.routinepartner;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SetCategoryAdapter adapter;
    ArrayList<CategoryInfo> CategoryList;
    CategoryInfo Category;
    int CurPositon;

    static int TimeInterval = 3;

    Button CategoryAddBtn, PlusInterval, MinusInterval;
    TextView TimeIntervalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        SavedSettings.isRefreshed = false;
        ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();
                            String Name = intent.getStringExtra("Name");
                            long Color = intent.getLongExtra("Color", 0xffffffff);
                            int Stat = intent.getIntExtra("Stat", 1);
                            int GoalType = intent.getIntExtra("GoalType", 1);
                            int Goal = intent.getIntExtra("Goal", 0);
                            int ActivitySet = intent.getIntExtra("ActivityType", 0);
                            int Order;
                            if(SavedSettings.Order.size()>0){
                                Order = SavedSettings.Order.get(SavedSettings.Order.size()-1)+1;
                            }else
                                Order = 1;

                            Log.v("getExtra", ""+Name+" "+Color+" "+Stat+" "+GoalType+" "+Goal);
                            if(ActivitySet == 0){
                                adapter.addItem(new CategoryInfo(Name, Color, Stat, GoalType, Goal, Order));
                                adapter.notifyItemInserted(SetCategoryAdapter.CategoryItem.size());

                                SavedSettings.CategoryList.add(Name);
                                SavedSettings.ColorList.add(Color);
                                SavedSettings.AffectingStat.add(Stat);
                                SavedSettings.GoalType.add(GoalType);
                                SavedSettings.Goal.add(Goal);
                                SavedSettings.Order.add(Order);

                                if(SetCategoryAdapter.CategoryItem.size()>=5){
                                    CategoryAddBtn.setEnabled(false);
                                }

                                Log.v("현재 리스트 이름", ""+SavedSettings.CategoryList);
                                Log.v("현재 리스트 색", ""+SavedSettings.ColorList);
                                Log.v("현재 리스트 순서", ""+SavedSettings.Order);
                                Log.v("현재 리스트 스탯", ""+SavedSettings.AffectingStat);

                            }else{
                                CategoryInfo editCategory = new CategoryInfo(Name, Color, Stat, GoalType, Goal, Order);
                                adapter.editItem(CurPositon, editCategory);
                                recyclerViewRefresh();
                                Log.v("수정 값", ""+Name);
                                SavedSettings.CategoryList.set(CurPositon, Name);
                                SavedSettings.ColorList.set(CurPositon, Color);
                                SavedSettings.AffectingStat.set(CurPositon, Stat);
                                SavedSettings.GoalType.set(CurPositon, GoalType);
                                SavedSettings.Goal.set(CurPositon, Goal);

                                Log.v("현재 리스트 이름", ""+SavedSettings.CategoryList);
                                Log.v("현재 리스트 색", ""+SavedSettings.ColorList);
                                Log.v("현재 리스트 순서", ""+SavedSettings.Order);
                            }
                            SettingsDB.DatabaseWriteExecutor.execute(() -> {
                                SettingsDB db = SettingsDB.getDatabase(getApplicationContext());
                                SettingsDAO mSettingsDao = db.settingDao();
                                mSettingsDao.deleteAll();
                            });
                            for(int i = 0; i < SavedSettings.CategoryList.size(); i++) {
                                String category = SavedSettings.CategoryList.get(i);
                                long color = SavedSettings.ColorList.get(i);
                                int goalType = SavedSettings.GoalType.get(i);
                                int goal = SavedSettings.Goal.get(i);
                                int affectingStat = SavedSettings.AffectingStat.get(i);
                                int order = SavedSettings.Order.get(i);
                                SettingsDB.DatabaseWriteExecutor.execute(() -> {
                                    SettingsDB db = SettingsDB.getDatabase(getApplicationContext());
                                    SettingsDAO mSettingsDao = db.settingDao();
                                    Settings settings = new Settings();
                                    settings.setCategory(category);
                                    settings.setColor(color);
                                    settings.setGoalType(goalType);
                                    settings.setGoal(goal);
                                    settings.setAffectingStat(affectingStat);
                                    settings.setOrder(order);
                                    mSettingsDao.insert(settings);
                                });
                            }
                        }
                        Log.i("in SettingActivity", "" + WidgetSettings.AppWidgetId);
                        AppWidgetManager.getInstance(getApplicationContext()).notifyAppWidgetViewDataChanged(WidgetSettings.AppWidgetId, R.id.widgetLayout);
                    }
                });


        TimeIntervalText = findViewById(R.id.CurrentTimeInterval);
        TimeIntervalText.setText(Integer.toString(TimeInterval));
        TimeInterval = Integer.parseInt(TimeIntervalText.getText().toString());
        PlusInterval = findViewById(R.id.PlusInterval);
        MinusInterval = findViewById(R.id.MinusInterval);
        CategoryAddBtn = findViewById(R.id.CategoryAddBtn);

        recyclerView = findViewById(R.id.CategoryList);

        adapter = new SetCategoryAdapter(getApplicationContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new SetCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                CurPositon = position;

                Intent intent = new Intent(getApplicationContext(), SetCategoryActivity.class);

//                intent.putExtra("CurName", SetCategoryAdapter.CategoryItem.get(position).getName());
//                intent.putExtra("CurColor", SetCategoryAdapter.CategoryItem.get(position).getColor());
//                intent.putExtra("CurStat", SetCategoryAdapter.CategoryItem.get(position).getStat());
//                intent.putExtra("CurGoalType", SetCategoryAdapter.CategoryItem.get(position).getGoalType());
//                intent.putExtra("CurGoal", SetCategoryAdapter.CategoryItem.get(position).getGoal());

                intent.putExtra("CurName", SetCategoryAdapter.CategoryItem.get(position).getName());
                intent.putExtra("CurColor", SetCategoryAdapter.CategoryItem.get(position).getColor());
                intent.putExtra("CurStat", SetCategoryAdapter.CategoryItem.get(position).getStat());
                intent.putExtra("CurGoalType", SetCategoryAdapter.CategoryItem.get(position).getGoalType());
                intent.putExtra("CurGoal", SetCategoryAdapter.CategoryItem.get(position).getGoal());

                startActivityResult.launch(intent);
            }

        });

        adapter.setOnItemLongClickListener(new SetCategoryAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int pos) {

                SettingsDB.DatabaseWriteExecutor.execute(() -> {
                    SettingsDB db = SettingsDB.getDatabase(getApplicationContext());
                    SettingsDAO mSettingsDao = db.settingDao();
                    mSettingsDao.deleteByOrder(SavedSettings.Order.get(pos));
                });
                adapter.delItem(pos);
                recyclerViewRefresh();

                SavedSettings.CategoryList.remove(pos);
                SavedSettings.ColorList.remove(pos);
                SavedSettings.AffectingStat.remove(pos);
                SavedSettings.GoalType.remove(pos);
                SavedSettings.Goal.remove(pos);
                SavedSettings.Order.remove(pos);

                for(int i = pos; i < SavedSettings.Order.size(); i++){
                    SavedSettings.Order.set(i, SavedSettings.Order.get(i)-1);
                }

                SetCategoryAdapter.CategoryItem.clear();
                for(int i = 0; i < SavedSettings.CategoryList.size(); i++){
                    SetCategoryAdapter.CategoryItem.add(new CategoryInfo(SavedSettings.CategoryList.get(i), SavedSettings.ColorList.get(i),
                            SavedSettings.AffectingStat.get(i), SavedSettings.GoalType.get(i), SavedSettings.Goal.get(i)));
                }

                if(SetCategoryAdapter.CategoryItem.size() < 5){
                    CategoryAddBtn.setEnabled(true);
                }

            }
        });

        if(SetCategoryAdapter.CategoryItem.size()>=5){
            CategoryAddBtn.setEnabled(false);
        }else{
            CategoryAddBtn.setEnabled(true);
        }


        //시간 간격 설정

        if(TimeInterval >= 3){
            PlusInterval.setEnabled(false);
        }else if(TimeInterval <= 1){
            MinusInterval.setEnabled(false);
        }

        PlusInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TimeInterval < 3){
                    TimeInterval++;
                    MinusInterval.setEnabled(true);
                    TimeIntervalText.setText(Integer.toString(TimeInterval));
                    PieChartView.TimeInterval = TimeInterval;
                    if(TimeInterval == 3){
                        PlusInterval.setEnabled(false);
                    }
                }
            }
        });

        MinusInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TimeInterval > 1){
                    TimeInterval--;
                    PlusInterval.setEnabled(true);
                    TimeIntervalText.setText(Integer.toString(TimeInterval));
                    PieChartView.TimeInterval = TimeInterval;
                    if(TimeInterval == 1){
                        MinusInterval.setEnabled(false);
                    }
                }
            }
        });


        //카테고리 추가

        CategoryAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MakeCategory = new Intent(getApplicationContext(), SetCategoryActivity.class);
                startActivityResult.launch(MakeCategory);
                adapter.notifyItemInserted(SetCategoryAdapter.CategoryItem.size());
            }
        });

    }

    public void recyclerViewRefresh(){
        recyclerView.removeAllViewsInLayout();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        outState.putInt("timeinterval", TimeInterval);
        super.onSaveInstanceState(outState);
    }
}