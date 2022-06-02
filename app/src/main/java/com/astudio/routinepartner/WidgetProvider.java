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
import java.util.concurrent.CountDownLatch;

public class WidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        //Log.i("in onUpdate", "Widget Updated");
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
                    views.setInt(CategoryBtnId.get(j), "setBackgroundColor", SavedSettings.ColorList.get(j).intValue());
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
            WidgetSettings.AppWidgetId = MyAppWidgetId;

            Log.i("in WidgetProvider", "" + WidgetSettings.AppWidgetId);
        }
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.i("in onReceive actionName : ", intent.getAction());

        CountDownLatch CDL = new CountDownLatch(1);
        SettingsDB.DatabaseWriteExecutor.execute(() -> {
            SettingsDB db = SettingsDB.getDatabase(context);
            SettingsDAO mSettingsDao = db.settingDao();
            //Log.i("CategoryNum", "" + mSettingsDao.getAll().length);
            int CategoryNum = mSettingsDao.getAll().length;
            if(CategoryNum == 0) {
                Settings settings = new Settings();
                settings.setCategory("취침");
                settings.setColor(0xFFCCCCFFL);
                settings.setGoalType(2);
                settings.setGoal(7);
                settings.setAffectingStat(3);
                settings.setOrder(1);
                mSettingsDao.insert(settings);
            }
            SavedSettings.CategoryList.clear();
            SavedSettings.ColorList.clear();
            SavedSettings.GoalType.clear();
            SavedSettings.Goal.clear();
            SavedSettings.AffectingStat.clear();
            SavedSettings.Order.clear();
            for(int i = 0; i < CategoryNum; i++) {
                SavedSettings.CategoryList.add(mSettingsDao.getAll()[i].getCategory());
                SavedSettings.ColorList.add(mSettingsDao.getAll()[i].getColor());
                SavedSettings.GoalType.add(mSettingsDao.getAll()[i].getGoalType());
                SavedSettings.Goal.add(mSettingsDao.getAll()[i].getGoal());
                SavedSettings.AffectingStat.add(mSettingsDao.getAll()[i].getAffectingStat());
                SavedSettings.Order.add(mSettingsDao.getAll()[i].getOrder());
            }
            CDL.countDown();
            //Log.i("in Widget", "DB transaction End");
        });
        try {
            CDL.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Log.i("in Widget", "Next");
        if (intent.getAction() == "CONTROL_SERVICE1" || intent.getAction() == "CONTROL_SERVICE2" || intent.getAction() == "CONTROL_SERVICE3" || intent.getAction() == "CONTROL_SERVICE4" || intent.getAction() == "CONTROL_SERVICE5" || intent.getAction() == "SETTINGS_CHANGED") {
            if(intent.getAction() != "SETTINGS_CHANGED")
            {
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
            }
            //WidgetSettings.IsWidgetUpdated = false;
            this.onUpdate(context, AppWidgetManager.getInstance(context), AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, getClass())));
        }
    }
}
