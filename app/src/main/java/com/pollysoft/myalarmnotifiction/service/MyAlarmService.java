package com.pollysoft.myalarmnotifiction.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.text.SimpleDateFormat;

public class MyAlarmService extends Service {

    private static final String LOG_TAG = "MyAlarmService";
    public static final String IN_EXTRA_TIME = "starttime";
    public MyAlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(LOG_TAG, "service is startCommand");

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        long starttime = intent.getLongExtra(IN_EXTRA_TIME, 0);
        Log.e(LOG_TAG, "alarm starttime:" + starttime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        Log.e(LOG_TAG, "alarm starttime:" + sdf.format(starttime));
        if (starttime > 0) {
            Intent intent1 = new Intent();
            intent1.setAction("com.pollysoft.myalarmnotifiction.service.ResultService");
            PendingIntent pi = PendingIntent.getService(this, 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            am.setRepeating(AlarmManager.RTC_WAKEUP, starttime, 8000, pi);
            Log.e(LOG_TAG, "set Alarm");
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
