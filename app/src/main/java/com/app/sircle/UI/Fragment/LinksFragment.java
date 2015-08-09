package com.app.sircle.UI.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.sircle.R;
import com.app.sircle.UI.Activity.AddLinksActivity;
import com.app.sircle.UI.Adapter.LinksListViewAdapter;
import com.app.sircle.UI.Model.Links;

import java.util.ArrayList;
import java.util.List;


public class LinksFragment extends Fragment {

    private ListView linksListView;
    private FloatingActionButton floatingActionButton;
    public static List<Links> linksList = new ArrayList<Links>();
    private LinksListViewAdapter linksListViewAdapter;
    private View footerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewFragment =  inflater.inflate(R.layout.fragment_links, container, false);

        linksListView = (ListView)viewFragment.findViewById(R.id.fragment_links_listview);
        floatingActionButton = (FloatingActionButton)viewFragment.findViewById(R.id.fab);

        footerView = View.inflate(getActivity(), R.layout.list_view_padding_footer, null);
        linksListView.addFooterView(footerView);

        populateDummyData();

        // add button on click to open respective view - only for admin
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addLinkIntent = new Intent(getActivity(), AddLinksActivity.class);
                startActivity(addLinkIntent);
            }
        });
        // set up custom listview
        linksListViewAdapter = new LinksListViewAdapter(linksList, getActivity());
        linksListView.setAdapter(linksListViewAdapter);

        return viewFragment;
    }


    private void populateDummyData(){
        Links links = new Links();
        links.setLink("www.google.co.in");
        links.setLinkTitle("Google");

        linksList.add(links);

        Links links1 = new Links();
        links1.setLink("www.facebook.com");
        links1.setLinkTitle("Facebook");

        linksList.add(links1);
        linksList.add(links);
        linksList.add(links1);
        linksList.add(links);
        linksList.add(links1);
        linksList.add(links);
        linksList.add(links1);
        linksList.add(links);

    }

    @Override
    public void onResume() {
        super.onResume();
        linksListViewAdapter.notifyDataSetChanged();
    }
}
