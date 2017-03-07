package com.snaptech.institutocolombres.UI.SlidingPane;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.snaptech.institutocolombres.R;


public class SlidingPaneFragment extends Fragment implements AdapterView.OnItemClickListener{

    private static SlidingPaneAdapter slidingPaneArrayAdapter;
    private ListView listViewSlidingPane;


    public static void refreshSlidingPaneListView(int position) {
        slidingPaneArrayAdapter.setSelectedIndex(position);
        slidingPaneArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewFragment = inflater.inflate(R.layout.fragment_sliding_pane,
                container, true);

        listViewSlidingPane = (ListView) viewFragment
                .findViewById(R.id.list_sliding_pane);


        slidingPaneArrayAdapter = new SlidingPaneAdapter(
                getActivity(), getActivity().getResources().getStringArray(
                R.array.array_module_name));
      //  slidingPaneArrayAdapter.setSelectedIndex(BaseActivity.selectedModuleIndex);

        listViewSlidingPane.setAdapter(slidingPaneArrayAdapter);

        listViewSlidingPane.setDividerHeight(2);

        listViewSlidingPane.setOnItemClickListener(this);

        listViewSlidingPane.setItemChecked(0, true);

        return viewFragment;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view,
                            int position, long id) {

        listViewSlidingPane.setItemChecked(position, true);
        refreshSlidingPaneListView(position);
       // ((SlidingPanDelegate) getActivity()).didSelectListViewItemAtIndex(position);
    }

//    public interface SlidingPanDelegate {
//        public void didSelectListViewItemAtIndex(Integer index);
//    }
}
