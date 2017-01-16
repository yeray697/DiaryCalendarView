package com.yeray697.calendarview;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Parcel;
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

    private MaterialCalendarView calendar;
    private RecyclerView rvEvents;
    private Toolbar toolbar;
    private LinearLayout llDate;
    private ImageView ivDate;
    private TextView tvDate;

    private boolean expanded;
    private ArrayList<DiaryCalendarEvent> events;
    private DiaryCalendarAdapter adapter;



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
        expanded = true;
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
                if (position != -1)
                    rvEvents.getLayoutManager().scrollToPosition(position);
                setSelectedEvent(position);
            }
        });
        this.events = new ArrayList<>();
        calendar.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                currentMonth();
            }
        });
        calendar.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        calendar.addDecorators(
                selectedDayDecorator,
                new CurrentDayDecorator((Activity)this.getContext())
        );
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
    }

    /**
     * Add a list of events to the calendary and the diary that will be marked into the calendary as a dot
     * @param newEvents Events to add
     * @param color Dot color
     * @param radius Dot radius
     */
    public void addEvent(List<? extends DiaryCalendarEvent> newEvents, int color, int radius) {
        this.events.addAll(newEvents);
        ArrayList<CalendarDay> days = new ArrayList<>();
        for(DiaryCalendarEvent event:events) {
            days.add(new CalendarDay(event.getYear(),event.getMonth(),event.getDay()));
        }

        calendar.addDecorator(new EventDecorator(color,radius, days));

        adapter.sortEvents();
    }

    /**
     * Add an event to the calendary and the diary that will be marked into the calendary as a dot
     * @param newEvent Event to add
     * @param color Dot color
     * @param radius Dot radius
     */
    public void addEvent(DiaryCalendarEvent newEvent , int color, int radius) {
        ArrayList<DiaryCalendarEvent> event = new ArrayList<>();
        event.add(newEvent);
        addEvent(event, color, radius);
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
}
