package com.app.sircle.UI.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sircle.DownLoader.FetchAppData;
import com.app.sircle.R;
import com.app.sircle.UI.SlidingPane.SlidingPaneInterface;

/**
 * Created by soniya on 7/22/15.
 */
public class HomeFragment extends Fragment {

    private TextView emailLabel;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewFragment = inflater.inflate(R.layout.fragment_home,
                null, true);

        emailLabel = (TextView)viewFragment.findViewById(R.id.activity_home_email_address_label);
        // underlines the email address
        SpannableString content = new SpannableString(getResources().getString(R.string.activity_login_email_address).toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        emailLabel.setText(content);

        progressBar = new ProgressBar(getActivity(),null,android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams pbParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        //pb.setLayoutParams(pbParam);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100,100);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        ((LinearLayout)viewFragment).addView(progressBar, pbParam);

        fetchAppData();

        return viewFragment;
    }

    public void fetchAppData(){

        new FetchAppData().execute(fetchedDataDelegate);
    }

    private FetchAppData.FetchedDataDelegate fetchedDataDelegate = new FetchAppData.FetchedDataDelegate() {
        @Override
        public void fetchDataDone() {
            progressBar.setVisibility(View.GONE);
        }
    };
}
