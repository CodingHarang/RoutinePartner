package com.example.routinepartner;

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
    ArrayList<Float> Data = new ArrayList<Float>();
    String[] PColors = {"#527FCD","#7FC4FF","#7FF3FF","#36CEB5","#19CE80","#61B585","#1ECE18","#EEA333","#FF774D"};

    public PieChartView(Context context, AttributeSet attrs){
        super(context, attrs);
        setBackgroundColor(Color.WHITE);
    }

    protected void onDraw(Canvas canvas){
        paint.setColor(Color.GRAY);
        RectF rect = new RectF();
        rect.set(100, 400, 1000, 1300);
        canvas.drawArc(rect, 0, 360, true, paint);

        for(int i = 0; i < Data.size()/2; i++){
            Log.d("데이터","추가"+i);
            paint.setColor(Color.parseColor(PColors[i])); //색은 추후에 카테고리에 따라 바뀌게 설정
            canvas.drawArc(rect, Data.get(2*i), Data.get(2*i+1), true, paint);
        }

    }
}
