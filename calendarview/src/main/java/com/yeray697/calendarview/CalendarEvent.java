package com.yeray697.calendarview;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by yeray697 on 14/01/17.
 */

public class CalendarEvent {
    public static Comparator<? super CalendarEvent> comparator = new Comparator<CalendarEvent>() {
        @Override
        public int compare(CalendarEvent o1, CalendarEvent o2) {
            int result = Float.compare(o1.getYear(),o2.getYear());
            if (result == 0) {
                result = Float.compare(o1.getMonth(), o2.getMonth());
                if (result == 0)
                    result = Float.compare(o1.getDay(),o2.getDay());
            }
            return result;
        }
    };
    private String title;
    private int year,month,day;
    private String description;

    public CalendarEvent(String title, int year,int month, int day, String description) {
        this.title = title;
        this.year = year;
        this.month = month;
        this.day = day;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return String.format("%02d", day) + "/" + String.format("%02d", (month + 1)) + "/" + year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
