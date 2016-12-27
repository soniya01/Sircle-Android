package com.snaptech.msb.UI.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.snaptech.msb.DownLoader.ImageManager;
import com.snaptech.msb.R;
import com.snaptech.msb.UI.Activity.AddSelectedPhoto;
import com.snaptech.msb.UI.Adapter.CamerarollAdapter;
import com.snaptech.msb.UI.Model.ImageData;

import java.util.List;


public class GalleryFragment extends android.support.v4.app.Fragment {

    private GridView gridViewCameraImages;
    private List<ImageData> imageData;
    private CamerarollAdapter cameraRollAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View viewFragment = inflater.inflate(R.layout.fragment_gallery, container, false);
        gridViewCameraImages = (GridView)viewFragment.findViewById(R.id.camera_roll_grid_view);

        gridViewCameraImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), AddSelectedPhoto.class);
                intent.putExtra("data", imageData.get(position));
                startActivity(intent);
            }
        });

        imageData  = ImageManager.getInstance().getCameraImagePaths(getActivity());
        cameraRollAdapter = new CamerarollAdapter(getActivity(), imageData);
        gridViewCameraImages.setAdapter(cameraRollAdapter);

        return viewFragment;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (BaseActivity.jumpToFragment){
//            getActivity().finish();
//        }

    }
}
