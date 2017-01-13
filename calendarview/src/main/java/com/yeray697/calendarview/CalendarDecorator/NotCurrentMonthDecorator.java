package com.yeray697.calendarview.CalendarDecorator;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.yeray697.calendarview.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Highlight Saturdays and Sundays with a background
 */
public class NotCurrentMonthDecorator implements DayViewDecorator {

    private CalendarDay date;

    private final Calendar calendar = Calendar.getInstance();
    private final Drawable highlightDrawable;

    public NotCurrentMonthDecorator(Activity context) {
        date = CalendarDay.today();
        highlightDrawable = context.getResources().getDrawable(R.drawable.circle_not_current_month_day);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.getMonth() != date.getMonth();
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(highlightDrawable);
    }


    /**
     * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
     */
    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}
