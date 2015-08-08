package com.app.sircle.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sircle.R;
import com.app.sircle.UI.Model.Links;

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

            viewHolder.linksImageView = (ImageView) convertView.findViewById(R.id.links_row_image);
            viewHolder.linksTitleLabel = (TextView) convertView.findViewById(R.id.links_row_link_label_name);
            viewHolder.linksLabel = (TextView) convertView.findViewById(R.id.links_row_link_url_label);
            viewHolder.linkDateLabel = (TextView) convertView.findViewById(R.id.links_row_publish_label_day);
            viewHolder.linkTimeLabel = (TextView) convertView.findViewById(R.id.links_row_publish_label_time);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.linksTitleLabel.setText(linksList.get(position).getLinkTitle());
        viewHolder.linksLabel.setText(linksList.get(position).getLink());

        viewHolder.linksImageView.setImageResource(getImageLink(linksList.get(position).getLinkTitle()));
        return convertView;
    }

    /**
     * returns image drawable resource id to set for the link image view
     * @param linkTitle - link for specific title
     * @return drawable resource for link icon corresponding to the link title
     */
    private int getImageLink(String linkTitle){

        int resourceId  = R.drawable.links_google;;
        switch (linkTitle){
            case "Google":
                resourceId = R.drawable.links_google;
                break;
            case "Facebook":
                resourceId = R.drawable.links_facebook;
                break;
            case "FlipKart":
                resourceId = R.drawable.links_google;
                break;
            case "Lanvin":
                resourceId = R.drawable.links_google;
              break;

        }

        return resourceId;
    }

    static class ViewHolder {
        private ImageView linksImageView;
        private TextView linksTitleLabel;
        private TextView linksLabel;
        private TextView linkDateLabel;
        private TextView linkTimeLabel;
    }
}
