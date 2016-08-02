package com.snaptech.rqk.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.snaptech.rqk.R;
import com.snaptech.rqk.UI.Fragment.CalendarTodayFragment;
import com.snaptech.rqk.UI.Model.Terms;
import com.snaptech.rqk.Utility.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mosesafonso on 09/08/15.
 */
public class TermsAdapter extends BaseAdapter {

    private Context context;
    private List<Terms> termsList = new ArrayList<Terms>();
    private LayoutInflater inflater;
    private CalendarTodayFragment termsFragment;

    public TermsAdapter(Context context, List<Terms> termsList,CalendarTodayFragment calendarTodayFragment) {
        this.context = context;
        this.termsFragment = calendarTodayFragment;
        this.termsList = termsList;
        inflater  = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return termsList.size();
    }

    @Override
    public Object getItem(int position) {
        return termsList.get(position);
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
            convertView = inflater.inflate(R.layout.list_view_terms,
                    parent, false);

            viewHolder.termsTitleLabel = (TextView) convertView.findViewById(R.id.termTitle);
            viewHolder.termsStartDateLabel = (TextView) convertView.findViewById(R.id.termStartDate);
            viewHolder.termsEndDateLabel = (TextView) convertView.findViewById(R.id.termEndDate);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.termsTitleLabel.setText("Term"+termsList.get(position).getTermTitle());
        viewHolder.termsStartDateLabel.setText(termsList.get(position).getTermStartDate().substring(0, 10));
         viewHolder.termsEndDateLabel.setText(termsList.get(position).getTermEndDate().substring(0,10));

        viewHolder.termsStartDateLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                termsFragment.showDialogCalendar(viewHolder.termsStartDateLabel.getText().toString());

                int termNumber = position+1;

                Constants.term = "T"+termNumber;
                Constants.termPeriod = "Start";

          // String s = viewHolder.termsStartDateLabel.getText().toString();

            //    System.out.println(s);

            }
        });

        viewHolder.termsEndDateLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(context,,Toast.LENGTH_SHORT).show();
                termsFragment.showDialogCalendar(viewHolder.termsEndDateLabel.getText().toString());

                int termNumber = position+1;


                Constants.term = "T"+termNumber;
                Constants.termPeriod = "End";

            }
        });


        return convertView;

    }



    static class ViewHolder {


        private TextView termsTitleLabel;
        private TextView termsStartDateLabel;
        private TextView termsEndDateLabel;
    }



}
