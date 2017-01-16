package com.yeray697.calendarview.CalendarDecorator;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

/**
 * Decorate event days with a dot
 * @author yeray697
 */
public class EventDecorator implements DayViewDecorator {

    private int color;
    private int radius;
    private HashSet<CalendarDay> dates;

    public EventDecorator(int color,int radius, Collection<CalendarDay> dates) {
        this.color = color;
        this.radius = radius;
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(radius, color));
    }
}
