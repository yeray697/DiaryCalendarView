package com.yeray697.calendarview.CalendarDecorator;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.yeray697.calendarview.R;

import java.util.Date;

/**
 * Decorate a day by making the text big and bold and circled if it is the selected day
 * @author yeray697
 */
public class SelectedDayDecorator implements DayViewDecorator {

    private CalendarDay date;
    private final Drawable drawable;

    public SelectedDayDecorator(Activity context) {
        date = CalendarDay.today();
        drawable = ContextCompat.getDrawable(context,R.drawable.circle_selected_day);
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
     * Set selected date
     */
    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}