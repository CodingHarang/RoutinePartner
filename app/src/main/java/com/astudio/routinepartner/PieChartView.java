package com.astudio.routinepartner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.jar.Attributes;

public class PieChartView extends View {

    Paint paint = new Paint();
    Paint ppaint = new Paint();
    Paint SmallPaint = new Paint();
    ArrayList<Float> Data = new ArrayList<Float>();
    ArrayList<Float> YesterdayData = new ArrayList<>();
    String[] PColors = {"#527FCD","#7FC4FF","#7FF3FF","#36CEB5","#19CE80","#61B585","#1ECE18","#EEA333","#FF774D"};

    public PieChartView(Context context, AttributeSet attrs){
        super(context, attrs);
        setBackgroundColor(Color.WHITE);
    }

    protected void onDraw(Canvas canvas){
        ppaint.setColor(Color.parseColor("#35475B"));
        ppaint.setStyle(Paint.Style.STROKE);
        ppaint.setStrokeWidth(5.0f);
        paint.setColor(Color.parseColor("#DFF4FF"));
        RectF rect = new RectF();
        rect.set(100, 400, 1000, 1300);
        RectF arcrect = new RectF();
        arcrect.set(100, 400, 1000, 1300);
        canvas.drawArc(rect, 0, 360, true, paint);
        SmallPaint.setColor(Color.parseColor("#DFF4FF"));

//        if(YesterdayData != null){ //전날에 걸친 시간 표시 / style: 원 밖
//            RectF smallarcrect = new RectF();
//            smallarcrect.set(50, 350, 1050, 1350);
//            for(int i = 0; i < YesterdayData.size()/2; i++) {
//                SmallPaint.setColor(Color.parseColor(PColors[0]));
//                canvas.drawArc(smallarcrect, YesterdayData.get(2*i), YesterdayData.get(2*i+1), true, SmallPaint);
//            }
//        }

        for(int i = 0; i < Data.size()/2; i++){
            Log.d("데이터","추가"+i);
            paint.setColor(Color.parseColor(PColors[i])); //색은 추후에 카테고리에 따라 바뀌게 설정
            canvas.drawArc(arcrect, Data.get(2*i), Data.get(2*i+1), true, paint);
        }


        if(YesterdayData != null){ //전날에 걸친 시간 표시 / style: 원 안
            SmallPaint.setStyle(Paint.Style.STROKE);
            SmallPaint.setStrokeWidth(40.0f);
            RectF smallarcrect = new RectF();
            smallarcrect.set(120, 420, 980, 1280);
            for(int i = 0; i < YesterdayData.size()/2; i++) {
                SmallPaint.setColor(Color.parseColor(PColors[0]));
                canvas.drawArc(smallarcrect, YesterdayData.get(2*i), YesterdayData.get(2*i+1), false, SmallPaint);
            }
        }

        canvas.drawArc(rect, 0, 360, true, ppaint);

    }

}