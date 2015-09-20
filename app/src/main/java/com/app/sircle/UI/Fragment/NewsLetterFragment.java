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
    private View footerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewFragment = inflater.inflate(R.layout.fragment_news_letter,
                null, true);
        newsLetterListView = (ListView)viewFragment.findViewById(R.id.fragment_news_list_view);

        populateDummyData();

        footerView = View.inflate(getActivity(), R.layout.list_view_padding_footer, null);
        newsLetterListView.addFooterView(footerView);


        newsLetterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             NewsLetter selectedItem = newsLetterList.get(position);

                Toast.makeText(getActivity(), "File downloaded " + selectedItem.pdfUrl, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), PDFViewer.class);
                intent.putExtra("PdfUrl",selectedItem.pdfUrl);
                startActivity(intent);
            }
        });


        return viewFragment;
    }

    public void populateDummyData(){

        String[] grpIds = {"1", "2"};
        HashMap map = new HashMap();
        map.put("regId", "id");
        map.put("groupId", grpIds);
        map.put("page", 1);

        DocumentManager.getSharedInstance().getAllNewsLetters(map, new DocumentManager.GetNewsManagerListener() {
            @Override
            public void onCompletion(DocumentsResponse response, AppError error) {

                if (error == null || error.getErrorCode() == AppError.NO_ERROR){
                    if (response != null){
                        if (response.getData().getLinks().size() > 0){
                            if (NewsLetterFragment.this.newsLetterList.size() == 0){
                                NewsLetterFragment.this.newsLetterList.addAll(response.getData().getLinks());
                                newsLetterListViewAdapter = new NewsLettersViewAdapter(getActivity(), response.getData().getLinks());
                                newsLetterListView.setAdapter(newsLetterListViewAdapter);
                            }else {
                                NewsLetterFragment.this.newsLetterList.clear();
                                NewsLetterFragment.this.newsLetterList.addAll(response.getData().getLinks());
                                newsLetterListViewAdapter.notifyDataSetChanged();
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
//        NewsLetter newsLetter = new NewsLetter();
//        newsLetter.setPdfURL("http://fzs.sve-mo.ba/sites/default/files/dokumenti-vijesti/sample.pdf");
//        newsLetter.setPdfTitle("PDF SAMPLE");
//        newsLetter.setPdfDate("Wednesday 27 May 2015");
//        newsLetter.setPdfTime("11:00");
//
//        newsLetterList.add(newsLetter);
//        newsLetterList.add(newsLetter);
//        newsLetterList.add(newsLetter);
//        newsLetterList.add(newsLetter);
//        newsLetterList.add(newsLetter);
//        newsLetterList.add(newsLetter);
//        newsLetterList.add(newsLetter);
    }
}
