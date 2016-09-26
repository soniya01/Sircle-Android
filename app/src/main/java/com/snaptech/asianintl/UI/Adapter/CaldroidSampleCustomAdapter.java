package com.snaptech.asianintl.UI.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snaptech.asianintl.R;
import com.snaptech.asianintl.Utility.Constants;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import hirondelle.date4j.DateTime;

public class CaldroidSampleCustomAdapter extends CaldroidGridAdapter {

	public CaldroidSampleCustomAdapter(Context context, int month, int year,HashMap<String, Object> caldroidData,HashMap<String, Object> extraData)
	{
		super(context, month, year, caldroidData, extraData);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View cellView = convertView;

		// For reuse
		if (convertView == null) {
			cellView = inflater.inflate(R.layout.custom_cell, null);
		}

		int topPadding = cellView.getPaddingTop();
		int leftPadding = cellView.getPaddingLeft();
		int bottomPadding = cellView.getPaddingBottom();
		int rightPadding = cellView.getPaddingRight();

		TextView tv1 = (TextView) cellView.findViewById(R.id.tv1);
		TextView tv2 = (TextView) cellView.findViewById(R.id.tv2);

		tv1.setTextColor(Color.BLACK);

		// Get dateTime of this cell
		DateTime dateTime = this.datetimeList.get(position);
		Resources resources = context.getResources();

		// Set color of the dates in previous / next month
		if (dateTime.getMonth() != month) {
			tv1.setTextColor(resources
					.getColor(com.caldroid.R.color.caldroid_darker_gray));
		}

		boolean shouldResetDiabledView = false;
		boolean shouldResetSelectedView = false;

		// Customize for disabled dates and date outside min/max dates
		if ((minDateTime != null && dateTime.lt(minDateTime))
				|| (maxDateTime != null && dateTime.gt(maxDateTime))
				|| (disableDates != null && disableDates.indexOf(dateTime) != -1)) {

			tv1.setTextColor(CaldroidFragment.disabledTextColor);
			if (CaldroidFragment.disabledBackgroundDrawable == -1) {
				cellView.setBackgroundResource(com.caldroid.R.drawable.disable_cell);
			} else {
				cellView.setBackgroundResource(CaldroidFragment.disabledBackgroundDrawable);
			}

			if (dateTime.equals(getToday())) {
				cellView.setBackgroundResource(com.caldroid.R.drawable.red_border_gray_bg);
			}

		} else {
			shouldResetDiabledView = true;
		}

		// Customize for selected dates
		if (selectedDates != null && selectedDates.indexOf(dateTime) != -1) {
//			cellView.setBackgroundColor(resources
//					.getColor(com.caldroid.R.color.caldroid_sky_blue));
			cellView.setBackgroundResource(R.drawable.circular_border);

			tv1.setTextColor(Color.BLACK);

		} else {
			shouldResetSelectedView = true;
		}

		if (shouldResetDiabledView && shouldResetSelectedView) {
			// Customize for today
			if (dateTime.equals(getToday())) {
				cellView.setBackgroundResource(R.drawable.circular);
				tv1.setTextColor(Color.WHITE);
			} else {
				cellView.setBackgroundResource(com.caldroid.R.drawable.cell_bg);
			}
		}

		tv1.setText("" + dateTime.getDay());




		if (!Constants.dateAvailabe.equals("")) {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date convertedDate = new Date();
			try {
				convertedDate = dateFormat.parse(Constants.dateAvailabe);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



			Calendar cal = Calendar.getInstance();
			cal.setTime(convertedDate);

			//args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
			//args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
if ((dateTime.getDay()==cal.get(Calendar.DATE))&&(dateTime.getMonth()==cal.get(Calendar.MONTH)+1)&&(dateTime.getYear()==cal.get(Calendar.YEAR)))

			//dateTime.toString();
			//Constants.dateAvailabe.toString();
			tv2.setText(Constants.term);

			if (Constants.termPeriod.equals("Start"))
			{
				tv2.setTextColor(Color.GREEN);
			}
			else
			{
				tv2.setTextColor(Color.RED);
			}
		}


		// Somehow after setBackgroundResource, the padding collapse.
		// This is to recover the padding
		//cellView.setPadding(leftPadding, topPadding, rightPadding,bottomPadding);

		// Set custom color if required
		setCustomResources(dateTime, cellView, tv1);

		return cellView;
	}

}
