package com.snaptech.poorishaadi.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.snaptech.poorishaadi.R;
import com.snaptech.poorishaadi.UI.Model.NewsLetter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mosesafonso on 16/05/16.
 */
public class DocumentsViewAdapter extends BaseAdapter {
    private Context context;
    private List<NewsLetter> newsLettersList = new ArrayList<NewsLetter>();
    private LayoutInflater inflater;

    public DocumentsViewAdapter(Context context, List<NewsLetter> newsLettersList) {
        this.context = context;
        this.newsLettersList = newsLettersList;
        inflater  = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return newsLettersList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsLettersList.get(position);
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
            convertView = inflater.inflate(R.layout.list_row_documents,
                    parent, false);

            viewHolder.pdfTitleLabel = (TextView) convertView.findViewById(R.id.pdfTitle);
            viewHolder.pdfDateLabel = (TextView) convertView.findViewById(R.id.pdfDate);
            viewHolder.pdfTimeLabel = (TextView) convertView.findViewById(R.id.pdfTime);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.pdfTitleLabel.setText(newsLettersList.get(position).getDocName());

        String str = newsLettersList.get(position).getDate();
        String[] splited = str.split("\\s+");

        viewHolder.pdfDateLabel.setText(splited[0]);
        viewHolder.pdfTimeLabel.setText(splited[1]);
        return convertView;

    }



    static class ViewHolder {


        private TextView pdfTitleLabel;
        private TextView pdfDateLabel;
        private TextView pdfTimeLabel;
    }
}

