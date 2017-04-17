package com.snaptech.mslgroupindia.UI.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.snaptech.mslgroupindia.Manager.NotificationManager;
import com.snaptech.mslgroupindia.R;
import com.snaptech.mslgroupindia.UI.Model.NotificationGroups;
import com.snaptech.mslgroupindia.Utility.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mosesafonso on 28/07/15.
 */
public class NotificationsGroupAdapter extends BaseAdapter {
    private Context context;
    private List<NotificationGroups> notificationsGroupList = new ArrayList<NotificationGroups>();
    private LayoutInflater inflater;
    SharedPreferences.Editor editor ;


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
      final   SharedPreferences sharedPreferences;
        final ViewHolder viewHolder;
        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        //List<String> sentToken = sharedPreferences.getStringSet(Constants.SENT_TOKEN_TO_SERVER, false);
        //Constants.GCM_REG_ID = sharedPreferences.getString(Constants.TOKEN_TO_SERVER,"");
        if (convertView == null) {

           // NotificationManager.grpIds = (List<String>)sharedPreferences.getStringSet(Constants.GROUP_IDS,null);
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
//        if (Constants.isAllChecked == 1){
//
//            viewHolder.checkBox.setChecked(true);
//            NotificationManager.grpIds.add(notificationsGroupList.get(position).getId());
//        }else {
          //  if (Constants.isAllChecked == -1){
                if (notificationsGroupList.get(position).getActive() == Boolean.TRUE){
                   // NotificationManager.grpIds.add( notificationsGroupList.get(position).getId());
                    viewHolder.checkBox.setChecked(true);
                }
           // }
           else// if (Constants.isAllChecked == 0)
            {
                viewHolder.checkBox.setChecked(false);
            }

       // }
        editor = sharedPreferences.edit();
        Set<String> set = new HashSet<>();
        set.addAll(NotificationManager.grpIds);
        editor.putStringSet(Constants.GROUP_IDS, set).apply();
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {

                int pos = (Integer) buttonView.getTag();

                if (isChecked){
                  //  NotificationManager.grpIds.add( notificationsGroupList.get(pos).getId());
                    // Constants.isAllChecked = -1;
                    notificationsGroupList.get(pos).setActive(Boolean.TRUE);
                }else{
                   // if (SettingsActivity.allCheckBox!=null)
                   // SettingsActivity.allCheckBox.setChecked(false);
                    //Constants.isAllChecked = -1;
                    notificationsGroupList.get(pos).setActive(Boolean.FALSE);
                    //NotificationManager.grpIds.remove(notificationsGroupList.get(pos).getId());
                }


                editor = sharedPreferences.edit();
                Set<String> set = new HashSet<>();
                set.addAll(NotificationManager.grpIds);
                editor.putStringSet(Constants.GROUP_IDS, set).apply();
               // editor.putStringSet(Constants.GROUP_IDS, (Set<String>) NotificationManager.grpIds).apply();

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
