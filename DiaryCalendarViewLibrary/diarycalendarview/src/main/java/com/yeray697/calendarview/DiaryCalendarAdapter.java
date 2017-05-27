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
 * Adapter for {@link DiaryCalendarView}
 * @author yeray697
 */

class DiaryCalendarAdapter extends RecyclerView.Adapter<DiaryCalendarAdapter.Holder> {

    private  Context context;
    private ArrayList<DiaryCalendarEvent> events;
    private int selectedEvent = -1;
    private int currentEvent = -1;

    DiaryCalendarAdapter(Context context, ArrayList<DiaryCalendarEvent> events) {
        this.events = events;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.diary_recycler_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        final DiaryCalendarEvent event = events.get(holder.getAdapterPosition());
        holder.tvTitle.setText(event.getTitle());
        holder.tvDate.setText(event.getDate());
        holder.tvDescription.setText(event.getDescription());

        if (isToday(event.getDate())) {
            this.currentEvent = holder.getAdapterPosition();
            holder.rlHead.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.current_event));
        } else {
            if (holder.getAdapterPosition() == selectedEvent)
                holder.rlHead.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.selected_day_diary));
            else
                holder.rlHead.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.background_item_not_expanded));
        }


        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rotation = 180;
                if (event.isExpanded()) {
                    rotation = 0;
                    ViewAnimationUtils.collapse(holder.rlBody,200);
                } else
                    ViewAnimationUtils.expand(holder.rlBody,ViewAnimationUtils.getMaxHeight(holder.rlBody),200);
                event.invertExpand();
                holder.btExpandCollapse.animate().rotation(rotation).start();
            }
        });
        if (event.isExpanded()) {
            holder.rlBody.setVisibility(View.VISIBLE);
            holder.btExpandCollapse.setRotation(180);
            holder.rlBody.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        else {
            holder.rlBody.setVisibility(View.GONE);
            holder.btExpandCollapse.setRotation(0);
            holder.rlBody.getLayoutParams().height = 0;
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    /**
     * Get selected event
     * @return Return selected event. -1 if there is no selected event
     */
    int getSelectedEvent() {
        return selectedEvent;
    }

    /**
     * Check if passed date is from today
     * @param date Date to check
     * @return Return true if the date is from today
     */
    private boolean isToday(String date) {
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

    /**
     * Set selected event from calendar into recyclerview, higlighting the event if exists
     * @param lastPosition Last selected event{@link DiaryCalendarAdapter#getSelectedEvent()}
     * @param lastCurrentView Last view of selected event
     * @param selectedEvent Selected event
     * @param currentView View of selected event
     */
    void setSelectedEvent(int lastPosition, View lastCurrentView, int selectedEvent, View currentView) {
        if (lastCurrentView != null && this.selectedEvent != this.currentEvent)
            lastCurrentView.setBackgroundColor(ContextCompat.getColor(lastCurrentView.getContext(),R.color.background_item_not_expanded));
        this.selectedEvent = selectedEvent;
        if (currentView != null && selectedEvent != this.currentEvent)
            currentView.setBackgroundColor(ContextCompat.getColor(currentView.getContext(),R.color.selected_day_diary));
        notifyItemChanged(lastPosition);
        notifyItemChanged(selectedEvent);
    }

    /**
     * Sort events by date
     */
    void sortEvents() {
        Collections.sort(this.events, DiaryCalendarEvent.comparator);
        notifyDataSetChanged();
    }

    /**
     * Recyclerview' holder
     */
    class Holder extends RecyclerView.ViewHolder {
        View root;
        RelativeLayout rlHead,rlBody;
        TextView tvTitle, tvDate, tvDescription;
        ImageView btExpandCollapse;

        public Holder(View itemView) {
            super(itemView);
            root = itemView;
            tvTitle = (TextView) itemView.findViewById(R.id.tvCalendarTitle_item);
            tvDate = (TextView) itemView.findViewById(R.id.tvCalendarDate_item);
            tvDescription = (TextView) itemView.findViewById(R.id.tvCalendarDescription_item);
            btExpandCollapse = (ImageView) itemView.findViewById(R.id.btCalendarExpand_item);
            rlHead = (RelativeLayout) itemView.findViewById(R.id.header_item);
            rlBody = (RelativeLayout) itemView.findViewById(R.id.body_item);

            rlBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
