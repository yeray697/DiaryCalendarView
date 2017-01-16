package com.yeray697.calendar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.yeray697.calendarview.DiaryCalendarEvent;
import com.yeray697.calendarview.DiaryCalendarView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DiaryCalendarView calendar = (DiaryCalendarView) findViewById(R.id.calendar);
        calendar.addEvent(getDays(), Color.BLUE,5);
        calendar.addEvent(new DiaryCalendarEvent("Hoy",2017,0,14,""), Color.BLUE,5);


    }

    public ArrayList<CustomEvents> getDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        ArrayList<CustomEvents> dates = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            CalendarDay day = CalendarDay.from(calendar);
            CustomEvents aux = new CustomEvents("Title: "+i,day.getYear(),day.getMonth(),day.getDay(),"Description: "+i);
            dates.add(aux);
            calendar.add(Calendar.DATE, 5);
        }
        return dates;
    }
}
