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
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog;
import com.github.dhaval2404.colorpicker.listener.ColorListener;
import com.github.dhaval2404.colorpicker.model.ColorShape;
import com.github.dhaval2404.colorpicker.model.ColorSwatch;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.jar.Attributes;

public class SetCategoryActivity extends AppCompatActivity {

    TextView Title;
    EditText SetCategoryName, GoalData;
    TextView GoalText;
    Spinner SetStat;
    Button SetColor, CheckButton;
    RadioGroup RadioGroup;
    LinearLayout NameLayout, StatLayout;
    View Divider1, Divider2;
    String NameSet, ColorSet = "#ffffff", StatSet, GoalTypeSet;
    int ColorInt = 0xffffff, GoalSet, ParseColor, ActivityValue = 0, SpinnerSelect, GoalKey;
    Long ColorLong = 0xffffffffL;
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
        Title = findViewById(R.id.Category);
        NameLayout = findViewById(R.id.linearLayout2);
        StatLayout = findViewById(R.id.linearLayout3);
        Divider1 = findViewById(R.id.divider);
        Divider2 = findViewById(R.id.divider2);
        NameLayout.setVisibility(View.VISIBLE);
        StatLayout.setVisibility(View.VISIBLE);
        
        Title.setText("카테고리 생성");
        

        Intent GetIntent = getIntent();

        String CurName = GetIntent.getStringExtra("CurName");
        Long CurColor = GetIntent.getLongExtra("CurColor", 0);
        int CurStat = GetIntent.getIntExtra("CurStat", 2);
        int CurGoalType = GetIntent.getIntExtra("CurGoalType",0);
        int CurGoal = GetIntent.getIntExtra("CurGoal", 0);
        int CurPosition = GetIntent.getIntExtra("CurPosition", 6);

        if(CurName != null){
            if(CurPosition == 0 || CurPosition == 1){
                NameLayout.setVisibility(View.GONE);
                StatLayout.setVisibility(View.GONE);
                Divider1.setVisibility(View.GONE);
                Divider2.setVisibility(View.GONE);
            }else{
                NameLayout.setVisibility(View.VISIBLE);
                StatLayout.setVisibility(View.VISIBLE);
                Divider1.setVisibility(View.VISIBLE);
                Divider2.setVisibility(View.VISIBLE);
            }
            ActivityValue = 1;
            CheckButton.setText("저장");
            Title.setText("카테고리 수정");
            SetCategoryName.setText(CurName);
            SetColor.setBackgroundColor(CurColor.intValue());
            ColorLong = CurColor;
            SpinnerSelect = CurStat;
            GoalKey = CurGoalType;
            if(GoalKey == 1){
                RadioGroup.check(R.id.RbuttonNum);
                GoalText.setText("번");
            }else{
                RadioGroup.check(R.id.RbuttonTime);
                GoalText.setText("시간");
            }

            GoalData.setVisibility(View.VISIBLE);
            GoalText.setVisibility(View.VISIBLE);
            GoalData.setText(Integer.toString(CurGoal));
        }

        
        //스탯 설정

        ArrayAdapter<String> StatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, SavedSettings.StatList);
        StatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        SetStat.setAdapter(StatAdapter);
        SetStat.setSelection(SpinnerSelect-1);
        SetStat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                StatSet = SavedSettings.StatList.get(i);
                SpinnerSelect = i+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                StatSet = null;
            }
        });

        //색상 선택

        //다이얼로그에 보여지는 색 리스트

        String[] colorArray = new String[]{"#FF5675", "#FF88A7", "#FF9E9B",
                "#FFA500", "#FFCD00", "#FFDC46", "#FFF064", "#B2FA5C", "#9EF048", "#80E12A","#40A940", "#28B4B4", "#41CDCD",
                "#9DF0E1", "#32F1FF", "#00D7FF", "#93DAFF", "#00BFFF", "#00AFFF", "#BECDFF","#90AFFF", "#6495ED", "#148CFF",
                "#E19B50", "#CD853F", "#D27D32"};

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
                        .setColors(colorArray)
                        .setColorShape(ColorShape.CIRCLE)
                        .setColorSwatch(ColorSwatch._300)
//                        .setDefaultColor(Color.parseColor(ColorSet))
                        .setDefaultColor(ColorLong.intValue())
                        .setColorListener(new ColorListener() {
                            @Override
                            public void onColorSelected(int color, @NotNull String colorHex) {
                                SetColor.setBackgroundColor(color);
                                ColorSet = colorHex;
                                ColorInt = color;
                                ColorLong = Long.valueOf(color);
                                ParseColor = Color.parseColor(colorHex);
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
                        GoalKey = 1;
                        break;
                    case R.id.RbuttonTime:
                        GoalText.setText("시간");
                        GoalTypeSet = "시간";
                        GoalKey = 2;
                        break;
                }
            }
        });



        //완료

        CheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CurPosition < 2 &&(ColorLong == 0||GoalKey == 0||GoalData.getText().length() == 0)){
                    Toast.makeText(getApplicationContext(), "설정을 모두 완료해주세요", Toast.LENGTH_SHORT).show();
                } else if(CurPosition >= 2 && (SetCategoryName.getText().length() == 0 || ColorLong == 0 || StatSet == null || GoalKey == 0 || GoalData.getText().length() == 0)){
                    Toast.makeText(getApplicationContext(), "설정을 모두 완료해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    NameSet = SetCategoryName.getText().toString();
                    GoalSet = Integer.parseInt(GoalData.getText().toString());
                    Intent Cheak = new Intent(getApplicationContext(), SettingActivity.class);
                    Cheak.putExtra("Name", NameSet);
//                    Cheak.putExtra("Color", ColorSet);
//                    Cheak.putExtra("GoalType", GoalTypeSet);
//                    Cheak.putExtra("Goal", GoalSet);
//                    Cheak.putExtra("Stat", StatSet);
//                    Cheak.putExtra("Stat", StatSet);
                    Cheak.putExtra("Color", ColorLong);
                    Cheak.putExtra("GoalType", GoalKey);
                    Cheak.putExtra("Goal", GoalSet);
                    Cheak.putExtra("Stat", SpinnerSelect);
                    Cheak.putExtra("ActivityType", ActivityValue);
                    setResult(Activity.RESULT_OK, Cheak);
                    finish();
                }
            }
        });

    }
}