package com.app.sircle.UI.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sircle.R;
import com.app.sircle.UI.Model.CalendarMonthlyListData;
import com.app.sircle.UI.Model.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mosesafonso on 09/08/15.
 */
public class CalendarMonthListAdapter extends BaseAdapter {
    private Context context;
    private List<Event> calendarMonthList = new ArrayList<Event>();
    private LayoutInflater inflater;

    public CalendarMonthListAdapter(Context context, List<Event> calendarMonthList) {
        this.context = context;
        this.calendarMonthList = calendarMonthList;
        inflater  = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return calendarMonthList.size();
    }

    @Override
    public Object getItem(int position) {
        return calendarMonthList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_view_calendar_month,
                    parent, false);

            viewHolder.eventTitleLabel = (TextView) convertView.findViewById(R.id.eventTitle);
            viewHolder.eventDateLabel = (TextView) convertView.findViewById(R.id.eventDate);
            viewHolder.eventTimeLabel = (TextView) convertView.findViewById(R.id.eventTime);
            viewHolder.eventImage = (ImageView) convertView.findViewById(R.id.eventImage);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.eventTitleLabel.setText(calendarMonthList.get(position).getTitle());
        viewHolder.eventDateLabel.setText(calendarMonthList.get(position).getStartDate());
        viewHolder.eventTimeLabel.setText(calendarMonthList.get(position).getStartTime());

        if (calendarMonthList.get(position).getIconId()==1)
        {
         // .setBackgroundResource(R.drawable.arts);
            viewHolder.eventImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.arts));
        }
        else if (calendarMonthList.get(position).getIconId()==2)
        {
           // viewHolder.eventImage.setBackgroundResource(R.drawable.sports);
            viewHolder.eventImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.sports));
        }
        else if (calendarMonthList.get(position).getIconId()==3)
        {
           // viewHolder.eventImage.setBackgroundResource(R.drawable.event_image);
            viewHolder.eventImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.event_image));
        }
        else if (calendarMonthList.get(position).getIconId()==4)
        {
            viewHolder.eventImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.academics));
        }
        else if (calendarMonthList.get(position).getIconId()==5)
        {
            viewHolder.eventImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.performance));
        }
        else if (calendarMonthList.get(position).getIconId()==6)
        {
            viewHolder.eventImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.others));
        }




        return convertView;
    }

    static class ViewHolder {
        private TextView eventTitleLabel;
        private TextView eventDateLabel;
        private TextView eventTimeLabel;
        private ImageView eventImage;
    }
}

