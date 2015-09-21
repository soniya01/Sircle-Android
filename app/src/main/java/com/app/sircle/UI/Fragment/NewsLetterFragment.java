package com.app.sircle.UI.Fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
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


public class NewsLetterFragment extends Fragment {

    private ListView newsLetterListView;
    private NewsLettersViewAdapter newsLetterListViewAdapter;
    private List<NewsLetter> newsLetterList = new ArrayList<NewsLetter>();
    private View footerView, viewFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewFragment = inflater.inflate(R.layout.fragment_news_letter,
                null, true);
        newsLetterListView = (ListView)viewFragment.findViewById(R.id.fragment_news_list_view);

        populateDummyData();

        footerView = View.inflate(getActivity(), R.layout.list_view_padding_footer, null);
        newsLetterListView.addFooterView(footerView);


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

        String[] grpIds = {"1", "2"};
        HashMap map = new HashMap();
        map.put("regId", "id");
        map.put("groupId", grpIds);
        map.put("page", 1);

        DocumentManager.getSharedInstance().getAllNewsLetters(map, new DocumentManager.GetNewsManagerListener() {
            @Override
            public void onCompletion(DocumentsResponse response, AppError error) {
                progressBar.setVisibility(View.GONE);
                if (error == null || error.getErrorCode() == AppError.NO_ERROR){
                    if (response != null){
                        if (response.getStatus() == 200){
                            if (response.getData().getDocs().size() > 0){
                                if (NewsLetterFragment.this.newsLetterList.size() == 0){
                                    NewsLetterFragment.this.newsLetterList.addAll(response.getData().getDocs());
                                    newsLetterListViewAdapter = new NewsLettersViewAdapter(getActivity(), response.getData().getDocs());
                                    newsLetterListView.setAdapter(newsLetterListViewAdapter);
                                }else {
                                    NewsLetterFragment.this.newsLetterList.clear();
                                    NewsLetterFragment.this.newsLetterList.addAll(response.getData().getDocs());
                                    newsLetterListViewAdapter.notifyDataSetChanged();
                                }
                            }else {
                                Toast.makeText(getActivity(), response.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getActivity(), response.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                }else {
                    Toast.makeText(getActivity(), "Sorry some error encountered while fetching data.Please check your internet connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
