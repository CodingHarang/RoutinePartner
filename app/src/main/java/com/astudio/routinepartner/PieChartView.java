package com.astudio.routinepartner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
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
    Paint StrokePaint = new Paint();
    Paint TextPaint = new Paint();
    Paint LinePaint = new Paint();
    ArrayList<Float> Data = new ArrayList<Float>();
    ArrayList<Float> YesterdayData = new ArrayList<>();
    String[] PColors = {"#527FCD","#7FC4FF","#7FF3FF","#36CEB5","#19CE80","#61B585","#1ECE18","#EEA333","#FF774D","#527FCD"};
    ArrayList<String> CategoryList = new ArrayList<>();
    Boolean YsData;

    public PieChartView(Context context, AttributeSet attrs){
        super(context, attrs);
        setBackgroundColor(Color.WHITE);
    }

    public void reset() {
        Data.clear();
        YesterdayData.clear();
        YsData = false;
    }
    protected void onDraw(Canvas canvas){
        ppaint.setColor(Color.parseColor("#35475B"));
        ppaint.setStyle(Paint.Style.STROKE);
        ppaint.setStrokeWidth(5.0f);
        paint.setColor(Color.parseColor("#FFFFFF"));
        TextPaint.setColor(Color.parseColor("#000000"));
        TextPaint.setTextSize(40);
        RectF rect = new RectF();
        rect.set((getWidth()/2)-430, getHeight()/2-630, (getWidth()/2)+ 470, getHeight()/2 +270);
        RectF arcrect = new RectF();
        arcrect.set((getWidth()/2)-430, getHeight()/2-630, (getWidth()/2)+ 470, getHeight()/2 +270);
        canvas.drawArc(rect, 0, 360, true, paint);
        SmallPaint.setColor(Color.parseColor("#DFF4FF"));

        //파이차트 바깥쪽에 시간 텍스트 표시

        float TextStartPoint = -90;
        for(int i = 0; i < 24; i++){
            float radius = 500;
            float x = (float)(radius * Math.cos(TextStartPoint * Math.PI / 180F)) + getWidth()/2;
            float y = (float)(radius * Math.sin(TextStartPoint * Math.PI / 180F)) + getHeight()/2 -160;
            canvas.drawText(""+i , x, y , TextPaint);
            TextStartPoint += 15;
        }

//        if(YesterdayData != null){ //전날에 걸친 시간 표시 / style: 원 밖
//            RectF smallarcrect = new RectF();
//            smallarcrect.set(50, 350, 1050, 1350);
//            for(int i = 0; i < YesterdayData.size()/2; i++) {
//                SmallPaint.setColor(Color.parseColor(PColors[0]));
//                canvas.drawArc(smallarcrect, YesterdayData.get(2*i), YesterdayData.get(2*i+1), true, SmallPaint);
//            }
//        }

        for(int i = 0; i < Data.size()/2; i++){
            switch (CategoryList.get(i)){
                case "Sleep":
                    paint.setColor(Color.parseColor(PColors[0]));
                    break;
                case "Etc":
                    paint.setColor(Color.parseColor(PColors[1]));
                    break;
                case "Study":
                    paint.setColor(Color.parseColor(PColors[2]));
                    break;
                case "Eat":
                    paint.setColor(Color.parseColor(PColors[3]));
                    break;
                default:
                    paint.setColor(Color.parseColor(PColors[4]));
            }
//            paint.setColor(Color.parseColor(PColors[i]));
            canvas.drawArc(arcrect, Data.get(2*i), Data.get(2*i+1), true, paint);
            Log.d("데이터",Data.get(2*i) + " to "+ Data.get(2*i+1));
        }

        if(YesterdayData != null) { //전날과 이어지는 시간 표시 / style: 원 안

            //테두리 그리기
            LinePaint.setStyle(Paint.Style.STROKE);
            LinePaint.setStrokeWidth(5f);
            LinePaint.setColor(Color.parseColor("#FFFFFF"));
            StrokePaint.setStyle(Paint.Style.STROKE);
            StrokePaint.setStrokeWidth(54.0f);
            RectF StrokeArcRect = new RectF();
            StrokeArcRect.set((getWidth() / 2) - 405, getHeight() / 2 - 605, (getWidth() / 2) + 445, getHeight() / 2 + 245);
            for (int i = 0; i < YesterdayData.size() / 2; i++) {
                StrokePaint.setColor(Color.parseColor("#FFFFFF"));
                canvas.drawArc(StrokeArcRect, YesterdayData.get(2 * i), YesterdayData.get(2 * i + 1), false, StrokePaint);
                canvas.drawLine(getWidth()/2+20, getHeight()/2-583, getWidth()/2+20, getHeight()/2-180, LinePaint);
            }

            //데이터 영역 그리기
            SmallPaint.setStyle(Paint.Style.STROKE);
            SmallPaint.setStrokeWidth(45.0f);
            String YdCategory = "null";
            if(CategoryList.size() > 0){
                YdCategory = CategoryList.get(0);
            }
            switch (YdCategory) {
                case "Sleep":
                    SmallPaint.setColor(Color.parseColor(PColors[0]));
                    break;
                case "Etc":
                    SmallPaint.setColor(Color.parseColor(PColors[1]));
                    break;
                case "Study":
                    SmallPaint.setColor(Color.parseColor(PColors[2]));
                    break;
                case "Eat":
                    SmallPaint.setColor(Color.parseColor(PColors[3]));
                    break;
                default:
                    SmallPaint.setColor(Color.parseColor(PColors[4]));
            }
            RectF smallarcrect = new RectF();
            smallarcrect.set((getWidth() / 2) - 405, getHeight() / 2 - 606, (getWidth() / 2) + 445, getHeight() / 2 + 246);
            for (int i = 0; i < YesterdayData.size() / 2; i++) {
                SmallPaint.setColor(Color.parseColor(PColors[0]));
                canvas.drawArc(smallarcrect, YesterdayData.get(2 * i)+0.8f, YesterdayData.get(2 * i + 1)-0.8f, false, SmallPaint);
            }
        }
        canvas.drawArc(rect, 0, 360, true, ppaint);
    }

    public void update() {
        invalidate();
    }

}