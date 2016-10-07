package com.snaptech.asb.UI.SlidingPane;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snaptech.asb.R;
import com.snaptech.asb.Utility.Constants;

/**
 * Created by soniya on 7/22/15.
 */
public class SlidingPaneAdapter extends ArrayAdapter<String>{

    private final Context context;
    private final String[] values;
    private Integer selectedIndex;
    private SharedPreferences loginSharedPreferences;
   // private int[] menuIcons = {R.drawable.home, R.drawable.calendar, R.drawable.photos, R.drawable.notifications, R.drawable.newsletters, R.drawable.documents,R.drawable.videos, R.drawable.links, R.drawable.settings, R.drawable.email, R.drawable.signout};

   // private int[] menuIconsSelected = {R.drawable.homeselected, R.drawable.calendarselected, R.drawable.photosselected, R.drawable.notificationsselected, R.drawable.newslettersselected, R.drawable.documentsselected,R.drawable.videosselected, R.drawable.linksselected, R.drawable.settingsselected, R.drawable.email, R.drawable.signout};

    private int[] menuIcons ;


    private int[] menuIconsSelected;

    public SlidingPaneAdapter(Context context, String[] values) {
        super(context, R.layout.listview_sliding_pane_row, values);
        loginSharedPreferences = context.getSharedPreferences(Constants.LOGIN_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginSharedPreferences.edit();
        String userType = loginSharedPreferences.getString(Constants.LOGIN_LOGGED_IN_USER_TYPE,null);

        if (userType.equals("admin"))
        {
           menuIcons = new int[]{R.drawable.homeblack, R.drawable.calendarblack, R.drawable.photosblack, R.drawable.notificationblack, R.drawable.newslettersblack, R.drawable.documentsblack,R.drawable.videosblack, R.drawable.linksblack,R.drawable.instituteinfoblack, R.drawable.emailblack, R.drawable.signoutblack};
            menuIconsSelected = new int[]{R.drawable.homeselected, R.drawable.calendarselected, R.drawable.photosselected, R.drawable.notificationsselected, R.drawable.newslettersselected, R.drawable.documentsselected,R.drawable.videosselected, R.drawable.linksselected,R.drawable.instituteinfoselected, R.drawable.emailselected, R.drawable.signout};
        }
        else
        {
            menuIcons = new int[]{R.drawable.homeblack, R.drawable.calendarblack, R.drawable.photosblack, R.drawable.notificationblack, R.drawable.newslettersblack, R.drawable.documentsblack,R.drawable.videosblack, R.drawable.linksblack,R.drawable.instituteinfoblack ,R.drawable.settingsblack, R.drawable.emailblack, R.drawable.signoutblack};

            menuIconsSelected = new int[]{R.drawable.homeselected, R.drawable.calendarselected, R.drawable.photosselected, R.drawable.notificationsselected, R.drawable.newslettersselected, R.drawable.documentsselected,R.drawable.videosselected, R.drawable.linksselected,R.drawable.instituteinfoselected, R.drawable.settingsselected, R.drawable.emailselected, R.drawable.signout};
        }
        this.context = context;
        this.values = values;
        this.selectedIndex = 0;
    }

    public Integer getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(Integer selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView;
        ImageView imageView;


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_sliding_pane_row,
                    parent, false);
        }

        textView = (TextView) convertView.findViewById(R.id.sliding_pane_text_module_name);
        textView.setText(values[position]);

        imageView = (ImageView) convertView.findViewById(R.id.sliding_pane_module_image_view);
       // imageView.setImageResource(menuIcons[position]);
        View selectedView = convertView.findViewById(R.id.sliding_pane_row_selected_view);

        LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.mainPaneLayout);

        if (getSelectedIndex() == position){
            selectedView.setVisibility(View.VISIBLE);
            imageView.setImageResource(menuIconsSelected[position]);
            linearLayout.setBackgroundColor(Color.parseColor("#F7F7F7"));
        }else {
            selectedView.setVisibility(View.GONE);
            imageView.setImageResource(menuIcons[position]);
            linearLayout.setBackgroundColor(Color.parseColor("#ECECEC"));
        }

        //Typeface fontDeclarationRegular = Typeface.createFromAsset(context.getAssets(), Constants.Font_DeclarationRegular);
        //textView.setTypeface(fontDeclarationRegular);

        return convertView;
    }

}
