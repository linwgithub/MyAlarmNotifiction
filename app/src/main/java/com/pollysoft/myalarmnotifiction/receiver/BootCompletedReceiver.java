package com.pollysoft.myalarmnotifiction.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompletedReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = "BootCompletedReceiver";

    public BootCompletedReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(LOG_TAG, "》》》》》》》》》监听到开机，服务启动");

        intent.setComponent(new ComponentName("com.pollysoft.myalarmnotifiction", "com.pollysoft.myalarmnotifiction.service.MyAlarmService"));
        context.startService(intent);
    }
}
