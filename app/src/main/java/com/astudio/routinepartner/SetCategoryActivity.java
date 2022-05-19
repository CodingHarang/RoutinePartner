package com.astudio.routinepartner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog;
import com.github.dhaval2404.colorpicker.listener.ColorListener;
import com.github.dhaval2404.colorpicker.model.ColorShape;
import com.github.dhaval2404.colorpicker.model.ColorSwatch;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SetCategoryActivity extends AppCompatActivity {

    EditText SetCategoryName, GoalData;
    TextView GoalText;
    Spinner SetStat;
    Button SetColor, CheckButton;
    RadioGroup RadioGroup;
    String NameSet, ColorSet, StatSet, GoalTypeSet;
    int ColorInt, GoalSet;
    static ArrayList<CategoryInfo> CategoryInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_category);

        SetCategoryName = findViewById(R.id.EditCategoryName);
        SetStat = findViewById(R.id.StatSpinner);
        SetColor = findViewById(R.id.SetColorBtn);
        RadioGroup = findViewById(R.id.RadioGroup);
        GoalData = findViewById(R.id.GoalData);
        GoalText = findViewById(R.id.GoalText);
        CheckButton = findViewById(R.id.CheckBtn);
        GoalData.setVisibility(View.GONE);
        GoalText.setVisibility(View.GONE);

        
        //스탯 설정

        ArrayAdapter<String> StatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, SavedSettings.StatList);
        StatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        SetStat.setAdapter(StatAdapter);
        SetStat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                StatSet = SavedSettings.StatList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                StatSet = null;
            }
        });

        //색상 선택

//        String[] colorArray = new String[]{"#f6e58d", "#ffbe76", "#ff7979",
//                "#badc58", "#dff9fb", "#7ed6df", "#e056fd", "#686de0", "#30336b", "#95afc0","#f6e58d", "#ffbe76", "#ff7979",
//                "#badc58", "#dff9fb", "#7ed6df", "#e056fd", "#686de0", "#30336b", "#95afc0","#f6e58d", "#ffbe76", "#ff7979",
//                "#badc58", "#dff9fb", "#7ed6df", "#e056fd", "#686de0", "#30336b", "#95afc0"};

        //Hue

//        SetColor.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceType")
//            @Override
//            public void onClick(View view) {
//                new ColorPickerDialog
//                        .Builder(SetCategoryActivity.this)
//                        .setTitle("Pick Theme")
//                        .setColorShape(ColorShape.SQAURE)
//                        .setDefaultColor(Color.parseColor("#ffffff"))
//                        .setColorListener(new ColorListener() {
//                            @Override
//                            public void onColorSelected(int color, @NotNull String colorHex) {
//                                SetColor.setBackgroundColor(color);
//                            }
//                        })
//                        .show();
//            }
//        });

        //팔레트

        SetColor.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                new MaterialColorPickerDialog
                        .Builder(SetCategoryActivity.this)
                        .setTitle("색 선택")
//                        .setColors(colorArray)
                        .setColorShape(ColorShape.CIRCLE)
                        .setColorSwatch(ColorSwatch._300)
                        .setDefaultColor(Color.parseColor("#ffffff"))
                        .setColorListener(new ColorListener() {
                            @Override
                            public void onColorSelected(int color, @NotNull String colorHex) {
                                SetColor.setBackgroundColor(color);
                                ColorSet = colorHex;
                                ColorInt = color;
                            }
                        })
                        .show();
            }
        });

        
        //목표 설정

        GoalTypeSet = null;
        RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(android.widget.RadioGroup radioGroup, int i) {
                GoalData.setVisibility(View.VISIBLE);
                GoalText.setVisibility(View.VISIBLE);
                switch(i){
                    case R.id.RbuttonNum:
                        GoalText.setText("번");
                        GoalTypeSet = "횟수";
                        break;
                    case R.id.RbuttonTime:
                        GoalText.setText("시간");
                        GoalTypeSet = "시간";
                        break;
                }
            }
        });
        //완료

        CheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SetCategoryName.getText().length() == 0 || ColorSet == null || StatSet == null || GoalTypeSet == null || GoalData.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "설정을 모두 완료해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    NameSet = SetCategoryName.getText().toString();
                    GoalSet = Integer.parseInt(GoalData.getText().toString());
                    //SetCategoryAdapter.addItem(new CategoryInfo(NameSet, ColorSet, StatSet, GoalTypeSet, GoalSet));
                    for(int i = 0; i < SetCategoryAdapter.CategoryItem.size(); i++){
                        Log.v("어레이", ""+SetCategoryAdapter.CategoryItem.get(i).getName());
                    }
                    Intent Cheak = new Intent(getApplicationContext(), SettingActivity.class);
                    setResult(Activity.RESULT_OK, Cheak);
                    finish();
                }
            }
        });

    }
}