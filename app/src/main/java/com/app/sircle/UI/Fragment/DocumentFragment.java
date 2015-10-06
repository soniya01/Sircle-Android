package com.app.sircle.UI.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.sircle.Manager.DocumentManager;
import com.app.sircle.Manager.NotificationManager;
import com.app.sircle.R;
import com.app.sircle.UI.Activity.PDFViewer;
import com.app.sircle.UI.Adapter.NewsLettersViewAdapter;
import com.app.sircle.UI.Model.NewsLetter;
import com.app.sircle.UI.SlidingPane.SlidingPaneInterface;
import com.app.sircle.Utility.AppError;
import com.app.sircle.WebService.DocumentsResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DocumentFragment extends Fragment {

    private ListView newsLetterListView;
    private NewsLettersViewAdapter newsLetterListViewAdapter;
    private List<NewsLetter> newsLetterList = new ArrayList<NewsLetter>();
    private View footerView, viewFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        viewFragment = inflater.inflate(R.layout.fragment_document,
                null, true);
        footerView = View.inflate(getActivity(), R.layout.list_view_padding_footer, null);

        populateDummyData();

        newsLetterListView = (ListView)viewFragment.findViewById(R.id.fragment_news_list_view);
        newsLetterListView.addFooterView(footerView);
        newsLetterListViewAdapter = new NewsLettersViewAdapter(getActivity(), newsLetterList);

        newsLetterListView.setAdapter(newsLetterListViewAdapter);
        newsLetterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsLetter selectedItem = newsLetterList.get(position);

                Toast.makeText(getActivity(), "File downloaded " + selectedItem.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), PDFViewer.class);
                intent.putExtra("PdfUrl",selectedItem.getNewsFile());
                startActivity(intent);
            }
        });


        return viewFragment;
    }

    public void populateDummyData(){
        final ProgressBar progressBar = new ProgressBar(getActivity(),null,android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100,100);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        ((RelativeLayout)viewFragment).addView(progressBar, layoutParams);


        String grpIdString = "";
        for (int i = 0; i< NotificationManager.grpIds.size(); i++){
            if (i == 0){
                grpIdString = NotificationManager.grpIds.get(i);
            }else {
                grpIdString = grpIdString + "," + NotificationManager.grpIds.get(i) ;
            }
        }
        HashMap object = new HashMap();
        object.put("regId", "id");
        object.put("groupId",grpIdString);
        object.put("page", 1);

        DocumentManager.getSharedInstance().getAllDocs(object, new DocumentManager.GetNewsManagerListener() {
            @Override
            public void onCompletion(DocumentsResponse data, AppError error) {
                progressBar.setVisibility(View.GONE);
                if (data != null) {
                    if (data.getStatus() == 200){
                        if (data.getData().getDocs().size() > 0){
                            if (newsLetterList.size() > 0){
                                newsLetterList.clear();
                                newsLetterList.addAll(data.getData().getDocs());
                                newsLetterListViewAdapter.notifyDataSetChanged();
                            }else {
                                newsLetterList.addAll(data.getData().getDocs());
                                newsLetterListViewAdapter = new NewsLettersViewAdapter(getActivity(), newsLetterList);
                                newsLetterListView.setAdapter(newsLetterListViewAdapter);
                            }
                        }else {
                            Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getActivity(), data.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
