package com.astudio.routinepartner;


import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class WidgetService extends Service {
    int NOTIFICATION_ID = 10;
    String CHANNEL_ID = "primary_notification_channel";
    int SYear, SMonth, SDate, SHour, SMinute, EYear, EMonth, EDate, EHour, EMinute;
    int CategoryNum;

    @Override // 서비스가 최초 생성될 때만 호출
    public void onCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("MyService is running")
                    .setContentText("MyService is running")
                    .build();
            startForeground(NOTIFICATION_ID, notification);
        }
    }

    private void createNotificationChannel() {
        NotificationChannel notificationChannel = new NotificationChannel(
                CHANNEL_ID,
                "MyApp notification",
                NotificationManager.IMPORTANCE_HIGH
        );
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setDescription("AppApp Tests");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    @Override // startService()로 서비스를 시작할 때 호출
    public int onStartCommand(Intent intent, int flags, int startId) {
        CategoryNum = WidgetSettings.ClickedWidgetButton;
        //Log.i("InWidgetService", "Service " + CategoryNum + " Started");
        //Log.i("SavedSettings", SavedSettings.CategoryName.get(1));
        Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_SHORT).show();

        Calendar cal = Calendar.getInstance();
        SYear = cal.get(Calendar.YEAR);
        SMonth = cal.get(Calendar.MONTH) + 1;
        SDate = cal.get(Calendar.DATE);
        SHour = cal.get(Calendar.HOUR);
        SMinute = cal.get(Calendar.MINUTE);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onRebind(Intent intent) {

    }

    @Override // 서비스가 소멸될 때 호출
    public void onDestroy() {
        //Log.i("End Service", "End service and notification");
        Calendar cal = Calendar.getInstance();
        EYear = cal.get(Calendar.YEAR);
        EMonth = cal.get(Calendar.MONTH) + 1;
        EDate = cal.get(Calendar.DATE);
        EHour = cal.get(Calendar.HOUR);
        EMinute = cal.get(Calendar.MINUTE);

        ActInfoDB db = ActInfoDB.getDatabase(getApplicationContext());
        ActInfoDAO mActInfoDao = db.actInfoDao();
        ActInfo actInfo = new ActInfo();
        actInfo.setCategory(SavedSettings.CategoryList.get(CategoryNum - 1));
        actInfo.setYear(SYear);
        actInfo.setMonth(SMonth);
        actInfo.setDate(SDate);
        actInfo.setStartHour(SHour);
        actInfo.setStartMinute(SMinute);
        actInfo.setEndHour(EHour);
        actInfo.setEndMinute(EMinute);
        mActInfoDao.insert(actInfo);
        super.onDestroy();
    }

}