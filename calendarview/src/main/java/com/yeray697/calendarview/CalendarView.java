package com.yeray697.calendarview;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.yeray697.calendarview.CalendarDecorator.CurrentDayDecorator;
import com.yeray697.calendarview.CalendarDecorator.EventDecorator;
import com.yeray697.calendarview.CalendarDecorator.SelectedDayDecorator;

import java.util.ArrayList;

/**
 * Created by yeray697 on 9/01/17.
 */

public class CalendarView extends RelativeLayout implements OnDateSelectedListener {

    private final SelectedDayDecorator selectedDayDecorator = new SelectedDayDecorator((Activity)this.getContext());


    private ImageView ivExpandCollapse;
    MaterialCalendarView calendar;
    RecyclerView rvEvents;

    private float initialYimageView;

    public CalendarView(Context context) {
        super(context,null);
    }

    private void inflateView() {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.calendarview,this,true);
        rvEvents = (RecyclerView) view.findViewById(R.id.rvCalendar);
        ivExpandCollapse = (ImageView) findViewById(R.id.ivExpandCollapse);
        calendar = (MaterialCalendarView) findViewById(R.id.calendarview);
        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        calendar.setOnDateChangedListener(this);
        calendar.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        calendar.addDecorators(
                selectedDayDecorator,
                new CurrentDayDecorator((Activity)this.getContext())
        );
        calendar.setTopbarVisible(false);
        calendar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                calculateImagePosition();
                ivExpandCollapse.setY(initialYimageView);
                calendar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        ivExpandCollapse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int rotation = 180;
                if (ivExpandCollapse.getRotation() == rotation) {
                    rotation = 0;
                    ViewAnimationUtils.expandCalendar(calendar,ivExpandCollapse,initialYimageView);
                } else
                    ViewAnimationUtils.collapseCalendar(calendar,ivExpandCollapse);
                ivExpandCollapse.animate().rotation(rotation).start();
            }
        });
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
        }
    }

    private void calculateImagePosition() {
        initialYimageView = calendar.getHeight() - (ivExpandCollapse.getHeight() / 2);
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

    public void addEvents(ArrayList<CalendarEvent> events, int color, int radius) {
        ArrayList<CalendarDay> days = new ArrayList<>();
        for(CalendarEvent event:events) {
            days.add(new CalendarDay(event.getYear(),event.getMonth(),event.getDay()));
        }

        calendar.addDecorator(new EventDecorator(color,radius, days));

        CalendarAdapter adapter = new CalendarAdapter(getContext(), events);
        rvEvents.setAdapter(adapter);
    }
    public void setCalendar(MaterialCalendarView calendar) {
        this.calendar = calendar;
    }
}
