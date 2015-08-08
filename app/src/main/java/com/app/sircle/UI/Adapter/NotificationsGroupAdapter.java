package com.app.sircle.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.sircle.R;
import com.app.sircle.UI.Model.NewsLetter;
import com.app.sircle.UI.Model.NotificationGroups;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mosesafonso on 28/07/15.
 */
public class NotificationsGroupAdapter extends BaseAdapter {
    private Context context;
    private List<NotificationGroups> notificationsGroupList = new ArrayList<NotificationGroups>();
    private LayoutInflater inflater;

    public NotificationsGroupAdapter(Context context, List<NotificationGroups> notificationsGroupList) {
        this.context = context;
        this.notificationsGroupList = notificationsGroupList;
        inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return notificationsGroupList.size();
    }

    @Override
    public Object getItem(int position) {
        return notificationsGroupList.get(position);
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
            convertView = inflater.inflate(R.layout.notifications_settings_list_item,
                    parent, false);

            viewHolder.notificationGroupTitle = (TextView) convertView.findViewById(R.id.notificationGroupTitle);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.notificationGroupTitle.setText(notificationsGroupList.get(position).groupName);
        return convertView;

    }


    static class ViewHolder {
        private TextView notificationGroupTitle;
    }
}
