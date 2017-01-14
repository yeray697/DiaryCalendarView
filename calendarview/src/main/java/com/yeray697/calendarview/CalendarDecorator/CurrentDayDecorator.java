package com.yeray697.calendarview.CalendarDecorator;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.yeray697.calendarview.R;

/**
 * Use a custom selector
 */
public class CurrentDayDecorator implements DayViewDecorator {

    private final Drawable drawable;

    public CurrentDayDecorator(Activity context) {
        drawable = context.getResources().getDrawable(R.drawable.current_day);
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
