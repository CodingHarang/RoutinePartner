package com.astudio.routinepartner;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class WidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        Log.i("in onUpdate", "Widget Updated");
        for (int i=0; i<N; i++) {
            int MyAppWidgetId = appWidgetIds[i];
            Intent intent1 = new Intent(context, getClass());
            Intent intent2 = new Intent(context, getClass());
            Intent intent3 = new Intent(context, getClass());
            Intent intent4 = new Intent(context, getClass());
            Intent intent5 = new Intent(context, getClass());
            intent1.setAction("CONTROL_SERVICE1");
            intent2.setAction("CONTROL_SERVICE2");
            intent3.setAction("CONTROL_SERVICE3");
            intent4.setAction("CONTROL_SERVICE4");
            intent5.setAction("CONTROL_SERVICE5");
            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            PendingIntent pendingIntent3 = PendingIntent.getBroadcast(context, 1, intent3, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            PendingIntent pendingIntent4 = PendingIntent.getBroadcast(context, 1, intent4, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            PendingIntent pendingIntent5 = PendingIntent.getBroadcast(context, 1, intent5, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            int CategoryNum = SavedSettings.CategoryList.size();

            ArrayList<Integer> CategoryBtnId = new ArrayList<Integer>(Arrays.asList(R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5));
            for(int j = 0; j < 5; j++) {
                if(j < CategoryNum) {
                    views.setInt(CategoryBtnId.get(j), "setVisibility", View.VISIBLE);
                    views.setTextViewText(CategoryBtnId.get(j), SavedSettings.CategoryList.get(j));
                    views.setInt(CategoryBtnId.get(j), "setBackgroundColor", SavedSettings.ColorList.get(j));
                    views.setTextViewTextSize(CategoryBtnId.get(j), 0, 75f);
                    if(j == 0) views.setOnClickPendingIntent(CategoryBtnId.get(j), pendingIntent1);
                    else if(j == 1) views.setOnClickPendingIntent(CategoryBtnId.get(j), pendingIntent2);
                    else if(j == 2) views.setOnClickPendingIntent(CategoryBtnId.get(j), pendingIntent3);
                    else if(j == 3) views.setOnClickPendingIntent(CategoryBtnId.get(j), pendingIntent4);
                    else if(j == 4) views.setOnClickPendingIntent(CategoryBtnId.get(j), pendingIntent5);
                } else {
                    views.setInt(CategoryBtnId.get(j), "setVisibility", View.GONE);
                }
            }
            if(WidgetSettings.IsServiceRunning == true) {
                for(int j = 0; j < 5; j++) {
                    if(j == WidgetSettings.ClickedWidgetButton - 1) {
                        views.setInt(CategoryBtnId.get(j), "setVisibility", View.VISIBLE);
                    } else {
                        views.setInt(CategoryBtnId.get(j), "setVisibility", View.GONE);
                    }
                }
            }
            appWidgetManager.updateAppWidget(MyAppWidgetId, views);
        }
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.i("in onReceive actionName : ", intent.getAction());
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        if (intent.getAction() == "CONTROL_SERVICE1" || intent.getAction() == "CONTROL_SERVICE2" || intent.getAction() == "CONTROL_SERVICE3" || intent.getAction() == "CONTROL_SERVICE4" || intent.getAction() == "CONTROL_SERVICE5") {
            if(intent.getAction() == "CONTROL_SERVICE1")
                WidgetSettings.ClickedWidgetButton = 1;
            if(intent.getAction() == "CONTROL_SERVICE2")
                WidgetSettings.ClickedWidgetButton = 2;
            if(intent.getAction() == "CONTROL_SERVICE3")
                WidgetSettings.ClickedWidgetButton = 3;
            if(intent.getAction() == "CONTROL_SERVICE4")
                WidgetSettings.ClickedWidgetButton = 4;
            if(intent.getAction() == "CONTROL_SERVICE5")
                WidgetSettings.ClickedWidgetButton = 5;

            if (WidgetSettings.IsServiceRunning == false) {
                context.startForegroundService(new Intent(context, WidgetService.class));
                WidgetSettings.IsServiceRunning = true;
            } else {
                context.stopService(new Intent(context, WidgetService.class));
                WidgetSettings.IsServiceRunning = false;
            }
            WidgetSettings.IsWidgetUpdated = false;
            this.onUpdate(context, AppWidgetManager.getInstance(context), AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, getClass())));
        }
    }
}
