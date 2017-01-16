package com.yeray697.calendarsample;

import android.os.Parcel;
import android.os.Parcelable;

import com.yeray697.calendarview.DiaryCalendarEvent;

/**
 * Created by yeray697 on 16/01/17.
 */
public class CustomEvents extends DiaryCalendarEvent {
    private String anotherProperties;
    // . . .

    public CustomEvents(String anotherProperties, String title, int year, int month, int day, String description) {
        super(title, year, month, day, description);
        this.anotherProperties = anotherProperties;
    }
    public static final Parcelable.Creator<CustomEvents> CREATOR = new Parcelable.Creator<CustomEvents>() {
        public CustomEvents createFromParcel(Parcel in) {
            return new CustomEvents(in);
        }

        public CustomEvents[] newArray(int size) {
            return new CustomEvents[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(anotherProperties);
    }

    private CustomEvents (Parcel in) {
        super(in);
        anotherProperties = in.readString();
    }
}
