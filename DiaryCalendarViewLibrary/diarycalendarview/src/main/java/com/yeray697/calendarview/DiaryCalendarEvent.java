package com.yeray697.calendarview;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Event used in {@link DiaryCalendarView}
 * It has a title, a date and a description
 * It is parcelable
 * @author yeray697
 */
public class DiaryCalendarEvent implements Parcelable {
    static Comparator<? super DiaryCalendarEvent> comparator = new Comparator<DiaryCalendarEvent>() {
        @Override
        public int compare(DiaryCalendarEvent o1, DiaryCalendarEvent o2) {
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

    public DiaryCalendarEvent(String title, int year, int month, int day, String description) {
        this.title = title;
        this.year = year;
        this.month = month;
        this.day = day;
        this.description = description;
        this.expanded = false;
    }

    public DiaryCalendarEvent(Parcel in) {
        title = in.readString();
        year = in.readInt();
        month = in.readInt();
        day = in.readInt();
        description = in.readString();
        expanded = in.readByte() != 0;
    }

    public static final Creator<DiaryCalendarEvent> CREATOR = new Creator<DiaryCalendarEvent>() {
        @Override
        public DiaryCalendarEvent createFromParcel(Parcel in) {
            return new DiaryCalendarEvent(in);
        }

        @Override
        public DiaryCalendarEvent[] newArray(int size) {
            return new DiaryCalendarEvent[size];
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
