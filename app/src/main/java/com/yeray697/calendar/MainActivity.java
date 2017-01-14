package com.yeray697.calendar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.yeray697.calendarview.CalendarEvent;
import com.yeray697.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalendarView calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.addEvents(getDays(), Color.BLUE,5);


    }

    public ArrayList<CalendarEvent> getDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        ArrayList<CalendarEvent> dates = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            CalendarDay day = CalendarDay.from(calendar);
            CalendarEvent aux = new CalendarEvent("Title: "+i,day.getYear(),day.getMonth(),day.getDay(),"Description: "+i);
            dates.add(aux);
            calendar.add(Calendar.DATE, 5);
        }
        return dates;
    }
}
