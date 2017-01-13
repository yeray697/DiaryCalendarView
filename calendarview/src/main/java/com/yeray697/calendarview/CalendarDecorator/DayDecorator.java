package com.yeray697.calendarview.CalendarDecorator;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.yeray697.calendarview.R;

/**
 * Use a custom selector
 */
public class DayDecorator implements DayViewDecorator {

    private final Drawable drawable;

    public DayDecorator(Activity context) {
        drawable = context.getResources().getDrawable(R.drawable.circle_day);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }
}
