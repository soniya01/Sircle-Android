package com.snaptech.institutoverdemolina.UI.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.snaptech.institutoverdemolina.R;
import com.snaptech.institutoverdemolina.UI.Model.Event;

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
            viewHolder.eventTimeLabel = (TextView) convertView.findViewById(R.id.startEventTime);
            viewHolder.eventEndTimeLabel = (TextView) convertView.findViewById(R.id.endEventTime);
            viewHolder.eventImage = (ImageView) convertView.findViewById(R.id.eventImage);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.eventTitleLabel.setText(calendarMonthList.get(position).getTitle());


        String str = calendarMonthList.get(position).getStartDate();
        String[] splited = str.split(" ");

        viewHolder.eventDateLabel.setText(splited[0]);
        if(calendarMonthList.get(position).getEvent_type().equalsIgnoreCase("N"))
            viewHolder.eventTimeLabel.setText(splited[1]);
        else
            viewHolder.eventTimeLabel.setText("");

        str = calendarMonthList.get(position).getEndDate();

        String[] splited1 = str.split(" ");

        if(calendarMonthList.get(position).getEvent_type().equalsIgnoreCase("N"))
            viewHolder.eventEndTimeLabel.setText(splited1[1]);
        else
            viewHolder.eventEndTimeLabel.setText("");


        if (calendarMonthList.get(position).getCategoryName().equals("Arts"))
        {
            // .setBackgroundResource(R.drawable.arts);
            viewHolder.eventImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.arts));
        }
        else if (calendarMonthList.get(position).getCategoryName().equals("Sports"))
        {
            // viewHolder.eventImage.setBackgroundResource(R.drawable.sports);
            viewHolder.eventImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.sports));
        }
        else if (calendarMonthList.get(position).getCategoryName().equals(""))
        {
            // viewHolder.eventImage.setBackgroundResource(R.drawable.event_image);
            viewHolder.eventImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.documentslist));
        }
        else if (calendarMonthList.get(position).getCategoryName().equals("Academics"))
        {
            viewHolder.eventImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.academics));
        }
        else if (calendarMonthList.get(position).getCategoryName().equals("Performance"))
        {
            viewHolder.eventImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.performance));
        }
        else if (calendarMonthList.get(position).getCategoryName().equals("Others"))
        {
            viewHolder.eventImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.others));
        }




        return convertView;
    }

    static class ViewHolder {
        private TextView eventTitleLabel;
        private TextView eventDateLabel;
        private TextView eventTimeLabel;
        private TextView eventEndTimeLabel;
        private ImageView eventImage;
    }
}
