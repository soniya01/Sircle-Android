package com.app.sircle.UI.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sircle.R;
import com.app.sircle.UI.CustomView.TouchImageView;
import com.app.sircle.UI.Model.AlbumDetails;
import com.app.sircle.Utility.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class AlbumImagePagerAdapter extends PagerAdapter {

    private Context context;
    private List<AlbumDetails> albumDetailsList = new ArrayList<AlbumDetails>();
    private LayoutInflater layoutInflater;
    public int position;


    public AlbumImagePagerAdapter(Context context, List<AlbumDetails> albumDetailsList) {
        this.context = context;
        this.albumDetailsList = albumDetailsList;
        this.layoutInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return albumDetailsList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        TouchImageView photoImageView;
        ImageButton download;
        TextView titleLabel, countLabel;
        this.position = position;

        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = layoutInflater.inflate(R.layout.view_pager_album, container,
                false);

        photoImageView = (TouchImageView) viewLayout.findViewById(R.id.album_pager_image);
        download = (ImageButton) viewLayout.findViewById(R.id.album_image_download_button);
        titleLabel = (TextView)viewLayout.findViewById(R.id.album_image_title_label);
        countLabel = (TextView)viewLayout.findViewById(R.id.albums_image_no_label);

        countLabel.setText((position+1)+"/"+albumDetailsList.size());

        Picasso.with(context)
                .load(albumDetailsList.get(position).getPhotoLargeURL())
                .into(photoImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: code to download image to gallery
                ImageDownloader imageDownloader = new ImageDownloader();
                imageDownloader.execute("http://img.youtube.com/vi/aAQy0r_6h2w/0.jpg");
            }
        });

        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((LinearLayout) object);
    }

    public class ImageDownloader  extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(context, "Downloading image",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf

            try {
                String extStorageDirectory = Environment.getExternalStorageDirectory().toString() + "/Sircle/Images/%d.jpg"+albumDetailsList.get(position).getPhotoID();
                URL url = new URL(fileUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);

                //String data1 = String.valueOf(String.format("/sdcard/koen/%d.jpg", System.currentTimeMillis()));

                FileOutputStream stream = new FileOutputStream(extStorageDirectory);

                ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                myBitmap.compress(Bitmap.CompressFormat.JPEG, 85, outstream);
                byte[] byteArray = outstream.toByteArray();

                stream.write(byteArray);
                stream.flush();
                stream.getFD().sync();
                scanFile(extStorageDirectory);
                stream.close();

            }catch (IOException e){
                e.printStackTrace();
                Toast.makeText(context, "Path not found!", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                 Toast.makeText(context, Constants.NO_NET_CONNECTIVITY_MESSAGE, Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        public void scanFile(String path){
            MediaScannerConnection.scanFile(context,
                    new String[]{path.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            // Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(context,"Download complete", Toast.LENGTH_SHORT).show();
        }
    }

}
