package com.yeray697.calendarview;

/**
 * Created by yeray697 on 14/01/17.
 */

public class CalendarEvent {
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
        return day + "/" + month + "/" + year;
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
