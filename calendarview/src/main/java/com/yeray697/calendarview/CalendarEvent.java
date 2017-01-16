package com.yeray697.calendarview;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by yeray697 on 14/01/17.
 */

public class CalendarEvent implements Parcelable {
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
    private boolean expanded;

    public CalendarEvent(String title, int year,int month, int day, String description) {
        this.title = title;
        this.year = year;
        this.month = month;
        this.day = day;
        this.description = description;
        this.expanded = false;
    }

    protected CalendarEvent(Parcel in) {
        title = in.readString();
        year = in.readInt();
        month = in.readInt();
        day = in.readInt();
        description = in.readString();
        expanded = in.readByte() != 0;
    }

    public static final Creator<CalendarEvent> CREATOR = new Creator<CalendarEvent>() {
        @Override
        public CalendarEvent createFromParcel(Parcel in) {
            return new CalendarEvent(in);
        }

        @Override
        public CalendarEvent[] newArray(int size) {
            return new CalendarEvent[size];
        }
    };

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

    boolean isExpanded() {
        return expanded;
    }

    void invertExpand() {
        this.expanded = !expanded;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeInt(day);
        dest.writeString(description);
        dest.writeByte((byte) (expanded ? 1 : 0));
    }
}
