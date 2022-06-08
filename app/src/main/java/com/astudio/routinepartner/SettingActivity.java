package com.astudio.routinepartner;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SetCategoryAdapter adapter;
    ArrayList<CategoryInfo> CategoryList;
    CategoryInfo Category;
    int CurPosition;


    ImageButton CategoryAddBtn, PlusInterval, MinusInterval;
    TextView TimeIntervalText;

    @Override
    protected void onStart() {
        super.onStart();
        SavedSettings.isRefreshed = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();
                            String Name = intent.getStringExtra("Name");
                            long GetColor = intent.getLongExtra("Color", 0xffffffff);
                            int Stat = intent.getIntExtra("Stat", 1);
                            int GoalType = intent.getIntExtra("GoalType", 1);
                            int Goal = intent.getIntExtra("Goal", 0);
                            int ActivitySet = intent.getIntExtra("ActivityType", 0);
                            int Order;
                            if(SavedSettings.Order.size()>0){
                                Order = SavedSettings.Order.get(SavedSettings.Order.size()-1)+1;
                            }else
                                Order = 1;

                            if(ActivitySet == 0){
                                adapter.addItem(new CategoryInfo(Name, GetColor, Stat, GoalType, Goal, Order));
                                adapter.notifyItemInserted(SetCategoryAdapter.CategoryItem.size());

                                SavedSettings.CategoryList.add(Name);
                                SavedSettings.ColorList.add(GetColor);
                                SavedSettings.AffectingStat.add(Stat);
                                SavedSettings.GoalType.add(GoalType);
                                SavedSettings.Goal.add(Goal);
                                SavedSettings.Order.add(Order);

                                if(SetCategoryAdapter.CategoryItem.size()>=5){
                                    CategoryAddBtn.setColorFilter(Color.parseColor("#C0C0C0"));
                                    CategoryAddBtn.setEnabled(false);
                                }

                            }else{

                                CategoryInfo editCategory = new CategoryInfo(Name, GetColor, Stat, GoalType, Goal, Order);
                                adapter.editItem(CurPosition, editCategory);

                                recyclerViewRefresh();
                                String OName = SavedSettings.CategoryList.get(CurPosition);
                                ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
                                ActInfoDAO mActInfoDao = db.actInfoDao();
                                mActInfoDao.updateCategoryName(OName, Name);

                                SavedSettings.CategoryList.set(CurPosition, Name);
                                SavedSettings.ColorList.set(CurPosition, GetColor);
                                SavedSettings.AffectingStat.set(CurPosition, Stat);
                                SavedSettings.GoalType.set(CurPosition, GoalType);
                                SavedSettings.Goal.set(CurPosition, Goal);
                            }
                            SettingsDB db = SettingsDB.getDatabase(getApplicationContext());
                            SettingsDAO mSettingsDao = db.settingDao();
                            mSettingsDao.deleteAll();
                            for(int i = 0; i < SavedSettings.CategoryList.size(); i++) {
                                String category = SavedSettings.CategoryList.get(i);
                                long color = SavedSettings.ColorList.get(i);
                                int goalType = SavedSettings.GoalType.get(i);
                                int goal = SavedSettings.Goal.get(i);
                                int affectingStat = SavedSettings.AffectingStat.get(i);
                                int order = SavedSettings.Order.get(i);
                                Settings settings = new Settings();
                                settings.setCategory(category);
                                settings.setColor(color);
                                settings.setGoalType(goalType);
                                settings.setGoal(goal);
                                settings.setAffectingStat(affectingStat);
                                settings.setOrder(order);
                                mSettingsDao.insert(settings);
                            }
                        }
                        AppWidgetManager.getInstance(getApplicationContext()).notifyAppWidgetViewDataChanged(WidgetSettings.AppWidgetId, R.id.widgetLayout);
                    }
                });


        TimeIntervalText = findViewById(R.id.CurrentTimeInterval);
        TimeIntervalText.setText(Integer.toString(SavedSettings.TimeInterval));
        SavedSettings.TimeInterval = Integer.parseInt(TimeIntervalText.getText().toString());
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
                CurPosition = position;

                Intent intent = new Intent(getApplicationContext(), SetCategoryActivity.class);

                intent.putExtra("CurPosition", CurPosition);
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
                if(pos == 0 || pos == 1){
                    Toast.makeText(getApplicationContext(), "기본 카테고리는 삭제할 수 없어요",Toast.LENGTH_SHORT).show();

                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);

                    builder.setTitle("정말 삭제하시겠습니까?");
                    builder.setMessage("카테고리를 삭제하면 저장되어있던 해당 카테고리의 모든 기록이 삭제됩니다");
                    builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            SettingsDB db = SettingsDB.getDatabase(getApplicationContext());
                            SettingsDAO mSettingsDao = db.settingDao();
                            mSettingsDao.deleteByOrder(SavedSettings.Order.get(pos));

                            adapter.delItem(pos);
                            recyclerViewRefresh();

                            String CatName = SavedSettings.CategoryList.get(pos);
                            SavedSettings.CategoryList.remove(pos);
                            SavedSettings.ColorList.remove(pos);
                            SavedSettings.AffectingStat.remove(pos);
                            SavedSettings.GoalType.remove(pos);
                            SavedSettings.Goal.remove(pos);
                            SavedSettings.Order.remove(pos);

                            for(int k = pos; k < SavedSettings.Order.size(); k++){
                                SavedSettings.Order.set(k, SavedSettings.Order.get(k)-1);
                            }

                            SetCategoryAdapter.CategoryItem.clear();
                            for(int k = 0; k < SavedSettings.CategoryList.size(); k++){
                                SetCategoryAdapter.CategoryItem.add(new CategoryInfo(SavedSettings.CategoryList.get(k), SavedSettings.ColorList.get(k),
                                        SavedSettings.AffectingStat.get(k), SavedSettings.GoalType.get(k), SavedSettings.Goal.get(k), SavedSettings.Order.get(k)));
                            }

                            Toast.makeText(SettingActivity.this, "삭제되었습니다", Toast.LENGTH_SHORT).show();


                            mSettingsDao.deleteAll();
                            ActInfoDB adb = ActInfoDB.getDatabase(getApplicationContext());
                            ActInfoDAO mActInfoDao = adb.actInfoDao();
                            mActInfoDao.deleteByCategory(CatName);
                            for(int j = 0; j < SavedSettings.CategoryList.size(); j++) {
                                String category = SavedSettings.CategoryList.get(j);
                                long color = SavedSettings.ColorList.get(j);
                                int goalType = SavedSettings.GoalType.get(j);
                                int goal = SavedSettings.Goal.get(j);
                                int affectingStat = SavedSettings.AffectingStat.get(j);
                                int order = SavedSettings.Order.get(j);
                                Settings settings = new Settings();
                                settings.setCategory(category);
                                settings.setColor(color);
                                settings.setGoalType(goalType);
                                settings.setGoal(goal);
                                settings.setAffectingStat(affectingStat);
                                settings.setOrder(order);
                                mSettingsDao.insert(settings);
                            }

                            if(SetCategoryAdapter.CategoryItem.size() < 5){
                                CategoryAddBtn.setColorFilter(Color.parseColor("#707070"));
                                CategoryAddBtn.setEnabled(true);
                            }
                        }
                    });

                    builder.setNegativeButton("취소", null);
                    builder.show();
                }
                
                if(SetCategoryAdapter.CategoryItem.size() < 5){
                    CategoryAddBtn.setEnabled(true);
                }

            }
        });

        if(SetCategoryAdapter.CategoryItem.size()>=5){
            CategoryAddBtn.setColorFilter(Color.parseColor("#C0C0C0"));
            CategoryAddBtn.setEnabled(false);
        }else{
            CategoryAddBtn.setColorFilter(Color.parseColor("#707070"));
            CategoryAddBtn.setEnabled(true);
        }


        //시간 간격 설정

        if(SavedSettings.TimeInterval >= 3){
            PlusInterval.setColorFilter(Color.parseColor("#C0C0C0"));
            PlusInterval.setEnabled(false);
        }else if(SavedSettings.TimeInterval <= 1){
            MinusInterval.setEnabled(false);
            MinusInterval.setColorFilter(Color.parseColor("#C0C0C0"));
        }

        PlusInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SavedSettings.TimeInterval < 3){
                    SavedSettings.TimeInterval++;
                    MinusInterval.setColorFilter(Color.parseColor("#707070"));
                    MinusInterval.setEnabled(true);
                    TimeIntervalText.setText(Integer.toString(SavedSettings.TimeInterval));
                    if(SavedSettings.TimeInterval == 3){
                        PlusInterval.setColorFilter(Color.parseColor("#C0C0C0"));
                        PlusInterval.setEnabled(false);
                    }
                }
                SettingsDB db = SettingsDB.getDatabase(getApplicationContext());
                TimeIntervalDAO mTimeIntervalDao = db.timeIntervalDao();
                mTimeIntervalDao.deleteAll();
                TimeInterval timeInterval = new TimeInterval();
                timeInterval.setInterval(SavedSettings.TimeInterval);
                mTimeIntervalDao.insert(timeInterval);

            }
        });

        MinusInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SavedSettings.TimeInterval > 1){
                    SavedSettings.TimeInterval--;
                    PlusInterval.setEnabled(true);
                    PlusInterval.setColorFilter(Color.parseColor("#707070"));
                    TimeIntervalText.setText(Integer.toString(SavedSettings.TimeInterval));
                    if(SavedSettings.TimeInterval == 1){
                        MinusInterval.setEnabled(false);
                        MinusInterval.setColorFilter(Color.parseColor("#C0C0C0"));
                    }
                }
                SettingsDB db = SettingsDB.getDatabase(getApplicationContext());
                TimeIntervalDAO mTimeIntervalDao = db.timeIntervalDao();
                mTimeIntervalDao.deleteAll();
                TimeInterval timeInterval = new TimeInterval();
                timeInterval.setInterval(SavedSettings.TimeInterval);
                mTimeIntervalDao.insert(timeInterval);
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