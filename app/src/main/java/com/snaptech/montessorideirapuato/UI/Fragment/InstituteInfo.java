package com.snaptech.montessorideirapuato.UI.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snaptech.montessorideirapuato.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstituteInfo extends Fragment {

//    private ImageButton imageButton_youtube;
//    private ImageButton imageButton_facebook;
//    private ImageButton imageButton_instagram;
    public InstituteInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view=inflater.inflate(R.layout.fragment_institute_info,null);
//        imageButton_youtube=(ImageButton)view.findViewById(R.id.btn_youtube);
//        imageButton_facebook=(ImageButton)view.findViewById(R.id.btn_facebook);
//        imageButton_instagram=(ImageButton)view.findViewById(R.id.btn_instagram);
//        imageButton_facebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//
//                    getActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0);
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/Instituto-Kipling-de-Morelia-197420127095464/")));
//                   // startActivity(new Intent("com.facebook.katana", Uri.parse("")));
//                }catch (Exception e){
//
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Instituto-Kipling-de-Morelia-197420127095464/")));
//                }
//            }
//        });
//        imageButton_instagram.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//
//                    Uri uri = Uri.parse("http://instagram.com/kipling_morelia/");
//                    Intent insta = new Intent(Intent.ACTION_VIEW, uri);
//                    insta.setPackage("com.instagram.android");
//
//                    if (isIntentAvailable(getActivity(), insta)){
//                        startActivity(insta);
//                    } else{
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/kipling_morelia/")));
//                    }
//                    //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/kipling_morelia/")).setPackage("com.instagram.android"));
//                }catch (Exception e){
//                    Toast.makeText(getActivity(),"Su teléfono no es compatible con esta función",Toast.LENGTH_SHORT).show();
//                    //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/kipling_morelia/")));
//
//                }
//            }
//        });
//        imageButton_youtube.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//
//
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCQQnqJpgn3k25AO3CCN59Bw")));
//            }catch (Exception e){
//
//                    Toast.makeText(getActivity(),"Su teléfono no es compatible con esta función",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        return view;
    }
    private boolean isIntentAvailable(Context ctx, Intent intent) {
        final PackageManager packageManager = ctx.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

}
