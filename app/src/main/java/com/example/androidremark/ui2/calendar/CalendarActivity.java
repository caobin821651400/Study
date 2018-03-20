package com.example.androidremark.ui2.calendar;

import android.os.Bundle;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;
import com.example.androidremark.utils.MyUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日历
 */
public class CalendarActivity extends BaseActivity {

    XCalendarView calendarView;
    private List<Date> markList;

    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        calendarView = (XCalendarView) findViewById(R.id.calendar_view);

        markList = new ArrayList<>();
        Calendar calendar1 = (Calendar) calendar.clone();
        //(9-1) Calendar跟我们正常说的月份少一
        calendar1.set(2017, (9-1), 20, 0, 0, 0);
        markList.add(calendar1.getTime());

        calendarView.setMarkList(markList);

        calendarView.setOnDateSelectedListener(new XCalendarView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                toast(MyUtils.getFormatDateTime(date.getTime(), "yyyy-MM-dd"));
            }
        });

        calendarView.setOnMonthChangeListener(new XCalendarView.OnMonthChangeListener() {
            @Override
            public void onPreMonthChange(Date date) {
                toast(MyUtils.getFormatDateTime(date.getTime(), "yyyy-MM"));
            }

            @Override
            public void onNextMonthChange(Date date) {
                toast(MyUtils.getFormatDateTime(date.getTime(), "yyyy-MM"));
            }
        });
    }
}
