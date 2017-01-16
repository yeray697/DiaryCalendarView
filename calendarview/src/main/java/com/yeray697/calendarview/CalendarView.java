package com.yeray697.calendarview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
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

/**
 * Created by yeray697 on 9/01/17.
 */

public class CalendarView extends RelativeLayout {

    private final SelectedDayDecorator selectedDayDecorator = new SelectedDayDecorator((Activity)this.getContext());


    private MaterialCalendarView calendar;
    private RecyclerView rvEvents;
    private Toolbar toolbar;
    private LinearLayout llDate;
    private ImageView ivDate;
    private TextView tvDate;
    private boolean expanded;

    private float initialYimageView;
    private ArrayList<CalendarEvent> events;

    CalendarAdapter adapter;

    public CalendarView(Context context) {
        super(context,null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        inflateView();
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflateView();
    }

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
                //If you change a decorate, you need to invalidate decorators
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

        adapter = new CalendarAdapter(getContext(), events);
        rvEvents.setAdapter(adapter);
    }

    private int checkDates(CalendarDay dateClicked) {
        SimpleDateFormat dfDate = new SimpleDateFormat("d/m/yyyy");

        boolean isToday;
        int result = -1;
        int position = 0;
        for (CalendarEvent event: events) {
            try {
                Calendar cal = Calendar.getInstance();
                cal.setTime(dfDate.parse(event.getDate()));
                isToday = dateClicked.getYear() == event.getYear() && dateClicked.getMonth() == event.getMonth() && dateClicked.getDay() == event.getDay();
                if (isToday) {
                    result = position;  // If start date is before end date.
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

    public void addEvent(ArrayList<CalendarEvent> newEvents, int color, int radius) {
        this.events.addAll(newEvents);
        ArrayList<CalendarDay> days = new ArrayList<>();
        for(CalendarEvent event:events) {
            days.add(new CalendarDay(event.getYear(),event.getMonth(),event.getDay()));
        }

        calendar.addDecorator(new EventDecorator(color,radius, days));

        adapter.sortEvents();
    }

    public void addEvent(CalendarEvent newEvent, int color, int radius) {
        ArrayList<CalendarEvent> event = new ArrayList<>();
        event.add(newEvent);
        addEvent(event, color, radius);
    }

    public void setCalendar(MaterialCalendarView calendar) {
        this.calendar = calendar;
    }

    private void currentMonth() {
        CalendarDay date = calendar.getCurrentDate();
        tvDate.setText(new DateFormatSymbols().getMonths()[date.getMonth()]+", "+date.getYear());
    }

    public MaterialCalendarView getCalendar() {
        return calendar;
    }

    public Toolbar getToolbar(){
        return toolbar;
    }
}
