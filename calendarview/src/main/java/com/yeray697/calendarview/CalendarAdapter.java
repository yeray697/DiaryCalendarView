package com.yeray697.calendarview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yeray697 on 14/01/17.
 */

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.Holder> {

    private  Context context;
    ArrayList<CalendarEvent> events;

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
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        RelativeLayout rlBody;
        TextView tvTitle, tvDate, tvDescription;
        ImageView btExpandCollapse;

        public Holder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvCalendarTitle_item);
            tvDate = (TextView) itemView.findViewById(R.id.tvCalendarDate_item);
            tvDescription = (TextView) itemView.findViewById(R.id.tvCalendarDescription_item);
            btExpandCollapse = (ImageView) itemView.findViewById(R.id.btCalendarExpand_item);
            rlBody = (RelativeLayout) itemView.findViewById(R.id.body_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int rotation = 180;
                    if (btExpandCollapse.getRotation() == rotation) {
                        rotation = 0;
                        ViewAnimationUtils.collapseItemList(rlBody);
                    } else
                        ViewAnimationUtils.expandItemList(rlBody);
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
}
