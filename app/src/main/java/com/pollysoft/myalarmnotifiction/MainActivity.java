package com.pollysoft.myalarmnotifiction;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pollysoft.myalarmnotifiction.service.MyAlarmService;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";
    private TextView tv;
    private Button btn;
    private Context context;
    private long selectedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        tv = (TextView) findViewById(R.id.tv_main_settime);
        tv.setOnClickListener(tvClick);
        btn = (Button) findViewById(R.id.btn_main_set);
        btn.setOnClickListener(btnClick);
    }

    View.OnClickListener tvClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new MyPickDialog().ShowDialog(MainActivity.this, tv, selectedTime, callback);
        }
    };

    View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //启动服务
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.pollysoft.myalarmnotifiction",
                    "com.pollysoft.myalarmnotifiction.service.MyAlarmService"));
            intent.putExtra(MyAlarmService.IN_EXTRA_TIME, selectedTime);
            startService(intent);
            Log.e(LOG_TAG, "startServer at mainActivity");
        }
    };

    MyPickDialog.SelecTimeCallback callback = new MyPickDialog.SelecTimeCallback() {
        @Override
        public void getOrderTime(long selecttime) {
            selectedTime = selecttime;
        }
    };


}
