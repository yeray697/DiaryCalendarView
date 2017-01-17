/*
	Copyright (c) 2017 Yeray Ruiz Juárez, https://github.com/yeray697

	Permission is hereby granted, free of charge, to any person obtaining
	a copy of this software and associated documentation files (the
	"Software"), to deal in the Software without restriction, including
	without limitation the rights to use, copy, modify, merge, publish,
	distribute, sublicense, and/or sell copies of the Software, and to
	permit persons to whom the Software is furnished to do so, subject to
	the following conditions:

	The above copyright notice and this permission notice shall be
	included in all copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
	EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
	MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
	NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
	LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
	OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
	WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
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
