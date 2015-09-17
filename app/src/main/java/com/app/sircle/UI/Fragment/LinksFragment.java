package com.app.sircle.UI.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.sircle.Manager.LinksManager;
import com.app.sircle.R;
import com.app.sircle.UI.Activity.AddLinksActivity;
import com.app.sircle.UI.Adapter.LinksListViewAdapter;
import com.app.sircle.UI.Model.Links;
import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.LinksResponse;
import com.app.sircle.WebService.LinksResponseData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LinksFragment extends Fragment {

    private ListView linksListView;
    private FloatingActionButton floatingActionButton;
    public  List<Links> linksList = new ArrayList<Links>();
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
        linksListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        return viewFragment;
    }


    private void populateDummyData(){

        HashMap object = new HashMap();
        object.put("regId", "id");
        object.put("groupId","1");
        object.put("page", 1);

        LinksManager.getSharedInstance().getAllLinks(object, new LinksManager.LinksManagerListener() {
            @Override
            public void onCompletion(LinksResponse response, AppError error) {
                if (error == null || error.getErrorCode() == AppError.NO_ERROR){
                    if (response != null){
                        if (response.getData().getLinks().size() > 0){
                            if (LinksFragment.this.linksList.size() == 0){
                                LinksFragment.this.linksList = linksList;
                                linksListViewAdapter = new LinksListViewAdapter( LinksFragment.this.linksList, getActivity());
                                linksListView.setAdapter(linksListViewAdapter);
                            }else {
                                LinksFragment.this.linksList.addAll(response.getData().getLinks());
                                linksListViewAdapter.notifyDataSetChanged();
                            }
                        }else {
                            Toast.makeText(getActivity(), "Sorry no data available",Toast.LENGTH_SHORT).show();
                        }
                    }

                }else {
                    Toast.makeText(getActivity(), "Sorry some error encountered while fetching data.Please check your internet connection",Toast.LENGTH_SHORT).show();
                }
            }
        });



//        Links links = new Links();
//        links.setLink("www.google.co.in");
//        links.setLinkTitle("Google");
//
//        linksList.add(links);
//
//        Links links1 = new Links();
//        links1.setLink("www.facebook.com");
//        links1.setLinkTitle("Facebook");
//
//        linksList.add(links1);
//        linksList.add(links);
//        linksList.add(links1);
//        linksList.add(links);
//        linksList.add(links1);
//        linksList.add(links);
//        linksList.add(links1);
//        linksList.add(links);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (LinksFragment.this.linksList.size() > 0){
            linksListViewAdapter.notifyDataSetChanged();
        }

    }
}
