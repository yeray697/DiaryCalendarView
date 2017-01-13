package com.yeray697.calendarview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.yeray697.calendarview.CalendarDecorator.CurrentDayDecorator;
import com.yeray697.calendarview.CalendarDecorator.DayDecorator;
import com.yeray697.calendarview.CalendarDecorator.EventDecorator;
import com.yeray697.calendarview.CalendarDecorator.NotCurrentMonthDecorator;
import com.yeray697.calendarview.CalendarDecorator.SelectedDayDecorator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by yeray697 on 9/01/17.
 */

public class CalendarView extends RelativeLayout implements OnDateSelectedListener, OnMonthChangedListener {

    private final SelectedDayDecorator selectedDayDecorator = new SelectedDayDecorator((Activity)this.getContext());
    private final NotCurrentMonthDecorator notCurrentMonthDecorator = new NotCurrentMonthDecorator((Activity)this.getContext());

    private RecyclerView recyclerView;
    private CalendarAdapter adapter;

    MaterialCalendarView calendar;
    RecyclerView rvEvents;


    public CalendarView(Context context) {
        super(context,null);
    }

    private void inflateView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.calendarview,this,true);
        rvEvents = (RecyclerView) view.findViewById(R.id.rvCalendar);
        calendar = (MaterialCalendarView) findViewById(R.id.calendarview);
        //calendar.setSelectionColor(Color.YELLOW);
        calendar.setOnDateChangedListener(this);
        calendar.setOnMonthChangedListener(this);
        calendar.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        calendar.addDecorators(
                new DayDecorator((Activity)this.getContext()),
                notCurrentMonthDecorator,
                selectedDayDecorator,
                new CurrentDayDecorator((Activity)this.getContext())
        );
        calendar.day
        calendar.setTopbarVisible(false);
        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
    }

    public MaterialCalendarView getCalendar() {
        return calendar;
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
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        //If you change a decorate, you need to invalidate decorators
        selectedDayDecorator.setDate(date.getDate());
        widget.invalidateDecorators();
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        //If you change a decorate, you need to invalidate decorators
        notCurrentMonthDecorator.setDate(date.getDate());
        widget.invalidateDecorators();
    }

    /**
     * Simulate an API call to show how to add decorators
     */
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -2);
            ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
                calendar.add(Calendar.DATE, 5);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (((Activity)getContext()).isFinishing()) {
                return;
            }

            calendar.addDecorator(new EventDecorator(Color.RED, calendarDays));
        }
    }
}
