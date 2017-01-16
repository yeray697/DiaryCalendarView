package com.yeray697.calendarsample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.yeray697.calendarview.DiaryCalendarEvent;
import com.yeray697.calendarview.DiaryCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DiaryCalendarView calendar = (DiaryCalendarView) findViewById(R.id.calendar);
        if (savedInstanceState == null) {
            //
            //Setting color of the event dot
            calendar.setEventColor(Color.RED);
            //Setting radius of the event dot
            calendar.setEventRadius(8);
            //Adding a list of custom events
            calendar.addEvent(getDays());
            //Adding a single event (DiaryCalendarEvent)
            Calendar today = Calendar.getInstance();
            calendar.addEvent(new DiaryCalendarEvent("Today", today.get(Calendar.YEAR),
                    today.get(Calendar.MONTH), today.get(Calendar.DATE), ""));
        }
        //Setting DiaryCalendarView toolbar
        setSupportActionBar(calendar.getToolbar());
        //Removing title
        setTitle("");

        //Use this methods if you want to customize the calendar
        //      More info about this class: https://github.com/prolificinteractive/material-calendarview/
        //calendar.getCalendar();
        //calendar.setCalendar(new MaterialCalendarView(this));


        findViewById(R.id.btRemoveLastEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.remove(calendar.getEventAtPosition(calendar.getEvents().size() - 1));
            }
        });
        findViewById(R.id.btAddEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = Calendar.getInstance().get(Calendar.YEAR);
                int month = randBetween(0, 11);
                GregorianCalendar gc = new GregorianCalendar(year, month, 1);
                int day = randBetween(1, gc.getActualMaximum(gc.DAY_OF_MONTH));
                calendar.addEvent(new DiaryCalendarEvent("New event",year,month,day,"New event description"));
                Toast.makeText(MainActivity.this, "Event added at: "+year+"/"+(month+1)+"/"+day, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
    /**
     * Generates 30 dates
     * @return
     */
    public ArrayList<CustomEvents> getDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        ArrayList<CustomEvents> dates = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            CalendarDay day = CalendarDay.from(calendar);
            CustomEvents aux = new CustomEvents("Another properties","Title: " + i, day.getYear(), day.getMonth(), day.getDay(), "Description: " + i);
            dates.add(aux);
            calendar.add(Calendar.DATE, 5);
        }
        return dates;
    }
}