package com.yeray697.calendarview.CalendarDecorator;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.yeray697.calendarview.R;

import java.util.Date;

/**
 * Decorate a day by making the text big and bold
 */
public class SelectedDayDecorator implements DayViewDecorator {

    private CalendarDay date;
    private final Drawable drawable;

    public SelectedDayDecorator(Activity context) {
        date = CalendarDay.today();
        drawable = context.getResources().getDrawable(R.drawable.circle_selected_day);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new StyleSpan(Typeface.BOLD));
        view.addSpan(new RelativeSizeSpan(1.4f));
        view.setSelectionDrawable(drawable);
    }

    /**
     * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
     */
    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}