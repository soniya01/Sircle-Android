package com.app.sircle.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.R;
import com.app.sircle.UI.Activity.SettingsActivity;
import com.app.sircle.UI.Model.NotificationGroups;
import com.app.sircle.Utility.AppError;
import com.app.sircle.Utility.Constants;
import com.app.sircle.WebService.GroupResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.notifications_settings_list_item,
                    parent, false);

            viewHolder.notificationGroupTitle = (TextView) convertView.findViewById(R.id.notificationGroupTitle);
            viewHolder.checkBox = (CheckBox)convertView.findViewById(R.id.checkItem);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.checkBox.setTag(position);

        viewHolder.notificationGroupTitle.setText(notificationsGroupList.get(position).name);
        if (SettingsActivity.isAllChecked){
            viewHolder.checkBox.setChecked(true);
            NotificationManager.grpIds.add( notificationsGroupList.get(position).getId());
        }else {
            if (notificationsGroupList.get(position).getActive() == 1){
                NotificationManager.grpIds.add( notificationsGroupList.get(position).getId());
                viewHolder.checkBox.setChecked(true);
            }

            else viewHolder.checkBox.setChecked(false);
        }

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {

                int pos = (Integer) buttonView.getTag();

                if (isChecked){
                    NotificationManager.grpIds.add( notificationsGroupList.get(pos).getId());
                    notificationsGroupList.get(pos).setActive(1);
                }else{
                    SettingsActivity.allCheckBox.setChecked(false);
                    SettingsActivity.isAllChecked = false;
                    notificationsGroupList.get(pos).setActive(0);
                    NotificationManager.grpIds.remove(notificationsGroupList.get(pos).getId());
                }



//                int status =  isChecked ? 1 : 0;
//                HashMap map = new HashMap();
//                map.put("regId", Constants.GCM_REG_ID);
//                map.put("val",String.valueOf(status));
//                map.put("groupId",notificationsGroupList.get(position).getId());
//                NotificationManager.getSharedInstance().updateGroupNotification(map, new NotificationManager.GroupsManagerListener() {
//                    @Override
//                    public void onCompletion(GroupResponse groupResponse, AppError error) {
//                        if (error == null && groupResponse != null){
//                            if (groupResponse.getData().get(0).getActive() == 1){
//                                viewHolder.checkBox.setChecked(true);
//                            }
//                        }else {
//                            viewHolder.checkBox.setChecked(false);
//                            Toast.makeText(context,groupResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
            }
        });
        return convertView;

    }


    static class ViewHolder {
        private TextView notificationGroupTitle;
        private CheckBox checkBox;
    }
}
