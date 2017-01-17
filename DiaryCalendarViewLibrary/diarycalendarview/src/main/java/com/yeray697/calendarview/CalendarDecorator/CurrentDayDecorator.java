package com.yeray697.calendarview.CalendarDecorator;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.yeray697.calendarview.R;

/**
 * Decorate a day by making the text bold and circled if date = today
 * @author yeray697
 */
public class CurrentDayDecorator implements DayViewDecorator {

    private final Drawable drawable;

    public CurrentDayDecorator(Activity context) {
        drawable = ContextCompat.getDrawable(context,R.drawable.current_day);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.equals(CalendarDay.today());
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
        view.addSpan(new StyleSpan(Typeface.BOLD));
    }
}