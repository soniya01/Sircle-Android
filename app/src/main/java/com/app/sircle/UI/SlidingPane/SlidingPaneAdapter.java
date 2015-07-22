package com.app.sircle.UI.SlidingPane;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sircle.R;

/**
 * Created by soniya on 7/22/15.
 */
public class SlidingPaneAdapter extends ArrayAdapter<String>{

    private final Context context;
    private final String[] values;
    private Integer selectedIndex;

    public SlidingPaneAdapter(Context context, String[] values) {
        super(context, R.layout.listview_sliding_pane_row, values);
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

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_sliding_pane_row,
                    parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.sliding_pane_text_module_name);
        textView.setText(values[position]);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.sliding_pane_module_image_view);
        View selectedView = convertView.findViewById(R.id.sliding_pane_row_selected_view);

        if (getSelectedIndex() == position){
            selectedView.setVisibility(View.VISIBLE);
        }else {
            selectedView.setVisibility(View.GONE);
        }

        //Typeface fontDeclarationRegular = Typeface.createFromAsset(context.getAssets(), Constants.Font_DeclarationRegular);
        //textView.setTypeface(fontDeclarationRegular);

        return convertView;
    }

}
