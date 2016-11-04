package com.snaptech.fitness360amritsar.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.snaptech.fitness360amritsar.R;
import com.snaptech.fitness360amritsar.UI.Model.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soniya on 08/08/15.
 */
public class NotificationListviewAdapter extends BaseAdapter {

    private List<Notification> notificationList = new ArrayList<Notification>();
    private Context context;
    private LayoutInflater inflater;

    public NotificationListviewAdapter(List<Notification> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
        inflater  = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return notificationList.size();
    }

    @Override
    public Object getItem(int position) {
        return notificationList.get(position);
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
            convertView = inflater.inflate(R.layout.list_view_notification_row,
                    parent, false);

            viewHolder.notificationDescLabel = (TextView) convertView.findViewById(R.id.notification_row_desc_label);
            viewHolder.notificationTitleLabel = (TextView) convertView.findViewById(R.id.notification_row_title_label_name);
            viewHolder.notificationDateLabel = (TextView) convertView.findViewById(R.id.notification_row_publish_label_day);
            viewHolder.notificationTimeLabel = (TextView) convertView.findViewById(R.id.notification_row_publish_label_time);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String str = notificationList.get(position).getPublishDate();
        String[] splited = str.split("\\s+");

        viewHolder.notificationDescLabel.setText(notificationList.get(position).getMessage());
        viewHolder.notificationTitleLabel.setText(notificationList.get(position).getSubject());
        viewHolder.notificationTimeLabel.setText(splited[1]);
        viewHolder.notificationDateLabel.setText(splited[0]);

        return convertView;
    }

    static class ViewHolder {

        private TextView notificationTitleLabel;
        private TextView notificationDescLabel;
        private TextView notificationDateLabel;
        private TextView notificationTimeLabel;
    }
}
