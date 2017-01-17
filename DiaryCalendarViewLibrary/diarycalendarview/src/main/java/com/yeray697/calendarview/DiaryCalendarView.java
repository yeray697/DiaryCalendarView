package com.yeray697.calendarview;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.yeray697.calendarview.CalendarDecorator.CurrentDayDecorator;
import com.yeray697.calendarview.CalendarDecorator.EventDecorator;
import com.yeray697.calendarview.CalendarDecorator.SelectedDayDecorator;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * View that contains a calendar and a recyclerview.
 * You can add events, that will be shown in both calendar and recyclerview
 * @see <a href="https://github.com/yeray697/DiaryCalendarView">Github's repository</a>
 * @author yeray697
 */
public class DiaryCalendarView extends RelativeLayout {

    private final SelectedDayDecorator selectedDayDecorator = new SelectedDayDecorator((Activity)this.getContext());
    private EventDecorator eventDecorator;
    private MaterialCalendarView calendar;
    private RecyclerView rvEvents;
    private Toolbar toolbar;
    private LinearLayout llDate;
    private ImageView ivDate;
    private TextView tvDate;

    private boolean expanded = true;
    private ArrayList<DiaryCalendarEvent> events;
    private DiaryCalendarAdapter adapter;
    private int eventColor;
    private int eventRadius;


    public DiaryCalendarView(Context context) {
        super(context,null);
    }

    public DiaryCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        inflateView();
    }

    public DiaryCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DiaryCalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflateView();
    }



    /**
     * Inflate the view and set up the view
     */
    private void inflateView() {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.calendarview,this,true);
        rvEvents = (RecyclerView) view.findViewById(R.id.rvCalendar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_calendar);
        llDate = (LinearLayout) findViewById(R.id.llDate_calendar);
        tvDate = (TextView) findViewById(R.id.tvDate_calendar);
        ivDate = (ImageView) findViewById(R.id.ivDate_calendar);
        calendar = (MaterialCalendarView) findViewById(R.id.calendarview);
        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                selectedDayDecorator.setDate(date.getDate());
                widget.invalidateDecorators();
                int position = checkDates(date);
                setSelectedEvent(position);
            }
        });
        this.eventColor = Color.BLUE;
        this.eventRadius = 5;
        this.events = new ArrayList<>();
        this.eventDecorator = new EventDecorator(eventColor,eventRadius,events);
        calendar.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                currentMonth();
            }
        });
        calendar.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        calendar.removeDecorators();
        calendar.addDecorators(
                selectedDayDecorator,
                new CurrentDayDecorator((Activity)this.getContext()));
        calendar.setTopbarVisible(false);
        llDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int rotation = 0;
                if (expanded) {
                    rotation = 180;
                    ViewAnimationUtils.collapse(calendar);
                } else
                    ViewAnimationUtils.expand(calendar);
                expanded = !expanded;
                ivDate.animate().rotation(rotation).start();
            }
        });
        currentMonth();

        adapter = new DiaryCalendarAdapter(getContext(), events);
        rvEvents.setAdapter(adapter);
        calendar.addDecorator(eventDecorator);

        manageScreenConfiguration();
    }

    /**
     * Hide calendar if it is not portrait mode
     * Show calendar it it is portrait mode
     */
    private void manageScreenConfiguration() {
        if (Configuration.ORIENTATION_PORTRAIT == getResources().getConfiguration().orientation) {
            llDate.setVisibility(VISIBLE);
            calendar.setVisibility(VISIBLE);
        } else {
            llDate.setVisibility(GONE);
            calendar.setVisibility(GONE);
        }
    }

    /**
     * Check if there is an event on the selected date
     * @param dateClicked Date clicked
     * @return Return event's position
     */
    private int checkDates(CalendarDay dateClicked) {
        SimpleDateFormat dfDate = new SimpleDateFormat("d/m/yyyy");

        boolean isToday;
        int result = -1;
        int position = 0;
        for (DiaryCalendarEvent event: events) {
            try {
                Calendar cal = Calendar.getInstance();
                cal.setTime(dfDate.parse(event.getDate()));
                isToday = dateClicked.getYear() == event.getYear() && dateClicked.getMonth() == event.getMonth() && dateClicked.getDay() == event.getDay();
                if (isToday) {
                    result = position;
                    break;
                } else {
                    position ++;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        return result;
    }

    /**
     * Set into the recyclerview the date selected from calendar, if it is an event
     * @param position Recyclerview item position. -1 if it is not an event
     */
    private void setSelectedEvent(int position){
        int lastPosition = adapter.getSelectedEvent();
        RecyclerView.ViewHolder lastViewHolder = rvEvents.findViewHolderForAdapterPosition(lastPosition);
        View lastView = null;
        if (lastViewHolder != null)
            lastView = lastViewHolder.itemView;

        RecyclerView.ViewHolder currentViewHolder = rvEvents.findViewHolderForAdapterPosition(position);
        View currentView = null;
        if (currentViewHolder != null)
            currentView = currentViewHolder.itemView;
        adapter.setSelectedEvent(lastPosition, lastView,position,currentView);
        if (position != -1)
            rvEvents.getLayoutManager().scrollToPosition(position);
    }

    /**
     * Add a list of events to the calendary and the diary that will be marked into the calendary as a dot
     * @param newEvents Events to add
     */
    public void addEvent(List<? extends DiaryCalendarEvent> newEvents) {
        if (events == null)
            this.events = new ArrayList<>();
        this.events.addAll(newEvents);
        ArrayList<CalendarDay> days = new ArrayList<>();
        for(DiaryCalendarEvent event:events) {
            days.add(new CalendarDay(event.getYear(),event.getMonth(),event.getDay()));
            eventDecorator.add(event);
        }
        calendar.invalidateDecorators();
        adapter.sortEvents();
    }

    /**
     * Add an event to the calendary and the diary that will be marked into the calendary as a dot
     * @param newEvent Event to add
     */
    public void addEvent(DiaryCalendarEvent newEvent) {
        ArrayList<DiaryCalendarEvent> event = new ArrayList<>();
        event.add(newEvent);
        addEvent(event);
    }

    /**
     * Set a MaterialCalendarView object as the calendar
     * @see <a href="https://github.com/prolificinteractive/material-calendarview">MaterialCalendarView</a>
     * @param calendar
     */
    public void setCalendar(MaterialCalendarView calendar) {
        this.calendar = calendar;
    }

    /**
     * Set the current date into the toolbar textview
     */
    private void currentMonth() {
        CalendarDay date = calendar.getCurrentDate();
        tvDate.setText(new DateFormatSymbols().getMonths()[date.getMonth()]+", "+date.getYear());
    }

    /**
     * Returns a MaterialCalendarView object, so you can modify it
     * @see <a href="https://github.com/prolificinteractive/material-calendarview">MaterialCalendarView</a>
     * @return
     */
    public MaterialCalendarView getCalendar() {
        return calendar;
    }

    /**
     * Returns the toolbar used in the view
     * @return
     */
    public Toolbar getToolbar(){
        return toolbar;
    }

    @Override
    public Parcelable onSaveInstanceState()
    {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putBoolean("expanded", this.expanded);
        bundle.putInt("selected_position", adapter.getSelectedEvent());
        bundle.putInt("scroll_position",rvEvents.computeVerticalScrollOffset());
        bundle.putInt("eventRadius", eventRadius);
        bundle.putInt("eventColor",eventColor);
        bundle.putParcelable("selected_day",calendar.getSelectedDate());
        bundle.putParcelableArrayList("events",events);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) // implicit null check
        {
            Bundle bundle = (Bundle) state;
            this.expanded = bundle.getBoolean("expanded");
            state = bundle.getParcelable("superState");
            if (!expanded) {
                ivDate.setRotation(180);
                calendar.getLayoutParams().height = 0;
            }
            setSelectedEvent(bundle.getInt("selected_position",-1));
            this.eventRadius = bundle.getInt("eventRadius",5);
            this.eventColor = bundle.getInt("eventColor",Color.BLUE);
            rvEvents.getLayoutManager().scrollToPosition(bundle.getInt("scroll_position",0));

            CalendarDay selected = bundle.getParcelable("selected_day");
            if (selected != null)
                selectedDayDecorator.setDate(selected.getDate());
            if (events == null)
                events = new ArrayList<>();
            addEvent(bundle.<DiaryCalendarEvent>getParcelableArrayList("events"));
        }
        super.onRestoreInstanceState(state);
    }

    /**
     * Get the dot color used to draw events
     * @return Return a color
     */
    public int getEventColor() {
        return eventColor;
    }

    /**
     * Set the dot color used to draw events
     * @param eventColor New color used
     */
    public void setEventColor(int eventColor) {
        if (this.eventColor != eventColor) {
            this.eventColor = eventColor;
            ArrayList<DiaryCalendarEvent> tmp = new ArrayList<>(events);
            clearEvents();
            addEvent(tmp);
            eventDecorator.setColor(eventColor);
        }
    }

    /**
     * Get the dot radius used to draw events
     * @return Return a radius int
     */
    public int getEventRadius() {
        return eventRadius;
    }

    /**
     * Set the dot radius used to draw events
     * @param eventRadius New radius used
     */
    public void setEventRadius(int eventRadius) {
        if (this.eventRadius != eventRadius) {
            this.eventRadius = eventRadius;
            ArrayList<DiaryCalendarEvent> tmp = new ArrayList<>(events);
            clearEvents();
            addEvent(tmp);
            eventDecorator.setRadius(eventRadius);
        }
    }

    /**
     * Clear events list
     */
    public void clearEvents() {
        if (events != null)
            events.clear();
    }

    /**
     * Get events drawn
     * @return ArrayList of events
     */
    public ArrayList<DiaryCalendarEvent> getEvents(){
        return events;
    }

    /**
     * Get an event by his position from the list
     * @param position Position of the event
     * @return Return an event
     */
    public DiaryCalendarEvent getEventAtPosition(int position){
        return events.get(position);
    }

    /**
     * Remove an event form the list
     * @param event Event that will be removed
     */
    public void remove(DiaryCalendarEvent event) {
        if(events.remove(event)) {
            adapter.sortEvents();
            eventDecorator.remove(event);
            calendar.invalidateDecorators();
        }
    }
}
