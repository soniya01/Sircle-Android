package com.snaptech.naharInt.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.snaptech.naharInt.R;
import com.snaptech.naharInt.UI.Model.Links;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soniya on 08/08/15.
 */
public class LinksListViewAdapter extends BaseAdapter {

    private List<Links> linksList = new ArrayList<Links>();
    private Context context;
    private LayoutInflater inflater;

    public LinksListViewAdapter(List<Links> linksList, Context context) {
        this.linksList = linksList;
        this.context = context;
        inflater  = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return linksList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_view_links,
                    parent, false);

          //  viewHolder.linksImageView = (ImageView) convertView.findViewById(R.id.links_row_image);
            viewHolder.linksTitleLabel = (TextView) convertView.findViewById(R.id.notification_row_title_label_name);
            viewHolder.linksLabel = (TextView) convertView.findViewById(R.id.notification_row_desc_label);
            viewHolder.linkDateLabel = (TextView) convertView.findViewById(R.id.links_row_publish_label_day);
            viewHolder.linkTimeLabel = (TextView) convertView.findViewById(R.id.links_row_publish_label_time);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.linksTitleLabel.setText(linksList.get(position).getName());
        viewHolder.linksLabel.setText(linksList.get(position).getUrl());

        // get screen dimensions
//        if (linksList.get(position).getFavIcon() != null){
//            Picasso.with(context)
//                    .load(linksList.get(position).getFavIcon())
//                    .into(viewHolder.linksImageView, new Callback() {
//                        @Override
//                        public void onSuccess() {
//
//                        }
//
//                        @Override
//                        public void onError() {
//
//                        }
//                    });
//        }

        String str = linksList.get(position).getCreatedOn();
        String[] splited = str.split("\\s+");

        viewHolder.linkTimeLabel.setText(splited[1]);
        viewHolder.linkDateLabel.setText(splited[0]);
        return convertView;
    }

    /**
     * returns image drawable resource id to set for the link image view
     * @param linkTitle - link for specific title
     * @return drawable resource for link icon corresponding to the link title
     */


    static class ViewHolder {
       // private ImageView linksImageView;
        private TextView linksTitleLabel;
        private TextView linksLabel;
        private TextView linkDateLabel;
        private TextView linkTimeLabel;
    }
}
