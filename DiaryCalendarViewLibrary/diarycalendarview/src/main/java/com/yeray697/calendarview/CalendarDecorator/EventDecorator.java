package com.yeray697.calendarview.CalendarDecorator;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;
import com.yeray697.calendarview.DiaryCalendarEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Decorate event days with a dot
 * @author yeray697
 */
public class EventDecorator implements DayViewDecorator {

    private int color;
    private int radius;
    private HashSet<CalendarDay> dates;

    public EventDecorator(int color, int radius, ArrayList<DiaryCalendarEvent> dates) {
        this.color = color;
        this.radius = radius;
        this.dates = new HashSet<>();
        for (DiaryCalendarEvent event: dates) {
            this.dates.add(new CalendarDay(event.getYear(),event.getMonth(),event.getDay()));
        }
    }

    public EventDecorator(int color, int radius, Collection<CalendarDay> dates) {
        this.color = color;
        this.radius = radius;
        this.dates = new HashSet<>(dates);
    }

    /**
     * Remove an event from the list
     * @param event Event that will be removed
     */
    public void remove(DiaryCalendarEvent event){
        this.dates.remove(new CalendarDay(event.getYear(),event.getMonth(),event.getDay()));
    }

    /**
     * Add an event into the list
     * @param event Event that will be added
     */
    public void add(DiaryCalendarEvent event){
        this.dates.add(new CalendarDay(event.getYear(),event.getMonth(),event.getDay()));
    }

    /**
     * Change the dot color
     * @param color Color that will be used
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Change the dot radius
     * @param radius Radius that will be used
     */
    public void setRadius(int radius) {
        this.radius = radius;
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
