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
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
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

    static int TimeInterval = 3;

    TextView TimeIntervalText;
    ImageButton PlusInterval, MinusInterval, CategoryAddBtn;

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
                            adapter.notifyItemInserted(SetCategoryAdapter.CategoryItem.size());
                        }
                    }
                });


        TimeIntervalText = findViewById(R.id.CurrentTimeInterval);
        TimeIntervalText.setText(Integer.toString(TimeInterval));
        TimeInterval = Integer.parseInt(TimeIntervalText.getText().toString());
        PlusInterval = findViewById(R.id.PlusInterval);
        MinusInterval = findViewById(R.id.MinusInterval);
        CategoryAddBtn = findViewById(R.id.CategoryAddBtn);

//        if(SetCategoryAdapter.CategoryItem.size()>=5){
//            CategoryAddBtn.setEnabled(false);
//        }else{
//            CategoryAddBtn.setEnabled(true);
//        }

        recyclerView = findViewById(R.id.CategoryList);

        adapter = new SetCategoryAdapter(getApplicationContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new SetCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), SetCategoryActivity.class);
                startActivityResult.launch(intent);
            }
        });

        adapter.setOnItemLongClickListener(new SetCategoryAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int pos) {
                Toast.makeText(getApplicationContext(),"롱클릭 성공" + pos, Toast.LENGTH_SHORT).show();
                adapter.delItem(pos);
            }
        });


        //시간 간격 설정

        PlusInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TimeInterval < 3){
                    TimeInterval++;
                    TimeIntervalText.setText(Integer.toString(TimeInterval));
                    PieChartView.TimeInterval = TimeInterval;
                }
            }
        });

        MinusInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TimeInterval > 1){
                    TimeInterval--;
                    TimeIntervalText.setText(Integer.toString(TimeInterval));
                    PieChartView.TimeInterval = TimeInterval;
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        outState.putInt("timeinterval", TimeInterval);
        super.onSaveInstanceState(outState);
    }
}