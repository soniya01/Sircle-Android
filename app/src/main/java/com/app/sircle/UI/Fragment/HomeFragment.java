package com.app.sircle.UI.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.sircle.R;
import com.app.sircle.UI.SlidingPane.SlidingPaneInterface;

/**
 * Created by soniya on 7/22/15.
 */
public class HomeFragment extends Fragment {

    private ImageButton drawerImageButton;
    private TextView emailLabel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewFragment = inflater.inflate(R.layout.fragment_home,
                null, true);

        drawerImageButton = (ImageButton) viewFragment.findViewById(R.id.fragment_home_drawer_icon);

        drawerImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SlidingPaneInterface) getActivity()).tappedDrawerIcon();
            }
        });

        emailLabel = (TextView)viewFragment.findViewById(R.id.activity_login_email_address_label);
        // underlines the email address
        SpannableString content = new SpannableString(getResources().getString(R.string.activity_login_email_address).toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        emailLabel.setText(content);

        return viewFragment;
    }
}
