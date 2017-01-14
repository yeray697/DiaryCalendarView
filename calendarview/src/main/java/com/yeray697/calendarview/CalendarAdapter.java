package com.yeray697.calendarview;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * Created by yeray697 on 14/01/17.
 */

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.Holder> {

    private  Context context;
    ArrayList<CalendarEvent> events;
    private int selectedEvent = -1;
    private int currentEvent = -1;

    public CalendarAdapter(Context context, ArrayList<CalendarEvent> events) {
        this.events = events;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.diary_recycler_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        CalendarEvent event = events.get(position);
        holder.tvTitle.setText(event.getTitle());
        holder.tvDate.setText(event.getDate());
        holder.tvDescription.setText(event.getDescription());

        if (checkDates(event.getDate())) {
            this.currentEvent = position;
            holder.rlHead.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.current_event));
        } else {
            if (position == selectedEvent)
                holder.rlHead.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.selected_day_diary));
            else
                holder.rlHead.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.background_item_not_expanded));
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public int getSelectedEvent() {
        return selectedEvent;
    }
    private boolean checkDates(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");

        boolean result = false;
        try {
            if (calendar.getTime().equals(dfDate.parse(date))) {
                result = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return result;
    }
    public void setSelectedEvent(int lastPosition, View lastCurrentView, int selectedEvent, View currentView) {
        if (lastCurrentView != null && this.selectedEvent != this.currentEvent)
            lastCurrentView.setBackgroundColor(ContextCompat.getColor(lastCurrentView.getContext(),R.color.background_item_not_expanded));
        this.selectedEvent = selectedEvent;
        if (currentView != null && selectedEvent != this.currentEvent)
            currentView.setBackgroundColor(ContextCompat.getColor(currentView.getContext(),R.color.selected_day_diary));
        notifyItemChanged(lastPosition);
        notifyItemChanged(selectedEvent);
    }
    public int getCurrentEvent() {
        return currentEvent;
    }

    class Holder extends RecyclerView.ViewHolder {
        RelativeLayout rlHead,rlBody;
        TextView tvTitle, tvDate, tvDescription;
        ImageView btExpandCollapse;

        public Holder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvCalendarTitle_item);
            tvDate = (TextView) itemView.findViewById(R.id.tvCalendarDate_item);
            tvDescription = (TextView) itemView.findViewById(R.id.tvCalendarDescription_item);
            btExpandCollapse = (ImageView) itemView.findViewById(R.id.btCalendarExpand_item);
            rlHead = (RelativeLayout) itemView.findViewById(R.id.header_item);
            rlBody = (RelativeLayout) itemView.findViewById(R.id.body_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int rotation = 180;
                    if (btExpandCollapse.getRotation() == rotation) {
                        rotation = 0;
                        ViewAnimationUtils.collapse(rlBody,200);
                    } else
                        ViewAnimationUtils.expand(rlBody,200);
                    btExpandCollapse.animate().rotation(rotation).start();
                }
            });
            rlBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
    public void sortEvents() {
        Collections.sort(this.events,CalendarEvent.comparator);
        notifyDataSetChanged();
    }
}
