package com.app.sircle.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.sircle.R;
import com.app.sircle.UI.Model.CalendarMonthlyListData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mosesafonso on 09/08/15.
 */
public class CalendarMonthListAdapter extends BaseAdapter {
    private Context context;
    private List<CalendarMonthlyListData> calendarMonthList = new ArrayList<CalendarMonthlyListData>();
    private LayoutInflater inflater;

    public CalendarMonthListAdapter(Context context, List<CalendarMonthlyListData> calendarMonthList) {
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

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // viewHolder.videoSourceLabel.setText("Youtube");
        return convertView;

    }



    static class ViewHolder {


        private TextView eventTitleLabel;
        private TextView eventDateLabel;
        private TextView eventTimeLabel;
    }
}

