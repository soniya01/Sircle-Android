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
import android.widget.Toast;

import com.app.sircle.R;
import com.app.sircle.UI.Activity.PDFViewer;
import com.app.sircle.UI.Adapter.NewsLettersViewAdapter;
import com.app.sircle.UI.Model.NewsLetter;
import com.app.sircle.UI.SlidingPane.SlidingPaneInterface;

import java.util.ArrayList;
import java.util.List;


public class DocumentFragment extends Fragment {

    private ListView newsLetterListView;
    private NewsLettersViewAdapter newsLetterListViewAdapter;
    private List<NewsLetter> newsLetterList = new ArrayList<NewsLetter>();
    private View footerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View viewFragment = inflater.inflate(R.layout.fragment_document,
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

                Toast.makeText(getActivity(), "File downloaded " + selectedItem.pdfUrl, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), PDFViewer.class);
                intent.putExtra("PdfUrl",selectedItem.pdfUrl);
                startActivity(intent);
            }
        });


        return viewFragment;
    }

    public void populateDummyData(){
        NewsLetter newsLetter = new NewsLetter();
        newsLetter.setPdfURL("http://fzs.sve-mo.ba/sites/default/files/dokumenti-vijesti/sample.pdf");
        newsLetter.setPdfTitle("PDF SAMPLE");
        newsLetter.setPdfDate("Wednesday 27 May 2015");
        newsLetter.setPdfTime("11:00");

        newsLetterList.add(newsLetter);
        newsLetterList.add(newsLetter);
        newsLetterList.add(newsLetter);
        newsLetterList.add(newsLetter);
        newsLetterList.add(newsLetter);
        newsLetterList.add(newsLetter);
        newsLetterList.add(newsLetter);
    }

}
