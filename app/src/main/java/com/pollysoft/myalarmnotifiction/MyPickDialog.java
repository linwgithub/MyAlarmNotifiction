package com.pollysoft.myalarmnotifiction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *预约时间选择器
 */
public class MyPickDialog implements DatePicker.OnDateChangedListener
        ,TimePicker.OnTimeChangedListener{

    private Activity activity;
    private SimpleDateFormat sdf;
    private DatePicker datePicker;
    private AlertDialog ad;
    private TimePicker timePicker;
    private TextView tvInput;

    private String datetimeStr = "";
    private long currTime = 0;
    private long selectTime = 0;
    private String[] weekDays = {"星期日", "星期一",
            "星期二", "星期三",
            "星期四", "星期五",
            "星期六"};

    private SelecTimeCallback onTimeSeleced = null;

    public interface SelecTimeCallback {
        public void getOrderTime(long selecttime);
    }

    public void init(DatePicker datePicker, TimePicker timePicker) {
        currTime = System.currentTimeMillis();
        selectTime = (selectTime <= 0) ? currTime : selectTime;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(selectTime);

        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));

        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), this);
    }

    /**
     * 弹出日期时间选择框方法
     *
     * @param tvInput:要设置的日期时间文本编辑框
     * @return
     */
    public AlertDialog ShowDialog(Activity activity,
                                  TextView tvInput, long inputTime,
                                  SelecTimeCallback ontimeselected) {
        this.activity = activity;
        this.onTimeSeleced = ontimeselected;
        this.tvInput = tvInput;
        LinearLayout dateTimeLayout = (LinearLayout) activity.getLayoutInflater()
                .inflate(R.layout.datetime_layout, null);
        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.picker_date);
        timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.picker_time);
        selectTime = inputTime;
        init(datePicker, timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(this);

        return ad = new AlertDialog.Builder(activity)
                .setView(dateTimeLayout)
                .setPositiveButton("设置", positiveListener)
                .setNegativeButton("取消", negativeListener)
                .show();
    }

    DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            datetimeStr = getSDFstring(selectTime) + "\n" + getWeekday(selectTime);
            tvInput.setTextColor(Color.rgb(50, 197, 249));
            tvInput.setText(datetimeStr);
            onTimeSeleced.getOrderTime(selectTime);
            dialog.dismiss();
        }
    };

    DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (tvInput.getText().equals("")) {
                tvInput.setTextColor(Color.RED);
                tvInput.setText("您还未选择时间");
                dialog.dismiss();
            }
        }
    };

    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        onDateChanged(null, 0, 0, 0);
    }

    public void onDateChanged(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
        // 获得日历实例
        Calendar calendar = Calendar.getInstance();
        //获取滚动时间结果
        calendar.set(datePicker.getYear(), datePicker.getMonth(),
                datePicker.getDayOfMonth(), timePicker.getCurrentHour(),
                timePicker.getCurrentMinute());
        selectTime = calendar.getTimeInMillis();
        datetimeStr = getSDFstring(selectTime) + "\n" + getWeekday(selectTime);
    }

    private String getSDFstring(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分 ");
        return sdf.format(new Date(time));
    }

    private String getWeekday(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int which = calendar.get(Calendar.DAY_OF_WEEK);
        return weekDays[which - 1];
    }

    public String getDatetimeStr(long time) {
        return getSDFstring(time) + "\n" + getWeekday(time);
    }

}
