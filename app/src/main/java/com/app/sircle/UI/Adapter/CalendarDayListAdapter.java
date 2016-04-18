package com.app.sircle.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.sircle.R;
import com.app.sircle.UI.Model.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mosesafonso on 15/04/16.
 */
public class CalendarDayListAdapter extends BaseAdapter {
    private Context context;
    private List<Event> calendarMonthList = new ArrayList<Event>();
    private LayoutInflater inflater;

    public CalendarDayListAdapter(Context context, List<Event> calendarMonthList) {
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
            convertView = inflater.inflate(R.layout.list_view_calendar_day,
                    parent, false);

            viewHolder.eventTitleLabel = (TextView) convertView.findViewById(R.id.eventTitle);
            viewHolder.eventDateLabel = (TextView) convertView.findViewById(R.id.startTime);
            viewHolder.eventTimeLabel = (TextView) convertView.findViewById(R.id.endTime);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.eventTitleLabel.setText(calendarMonthList.get(position).getTitle());
        viewHolder.eventDateLabel.setText(calendarMonthList.get(position).getStartTime());
        viewHolder.eventTimeLabel.setText(calendarMonthList.get(position).getEndTime());

        return convertView;
    }

    static class ViewHolder {
        private TextView eventTitleLabel;
        private TextView eventDateLabel;
        private TextView eventTimeLabel;
    }
}
