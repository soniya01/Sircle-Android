package com.snaptech.ezeego1.UI.Activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.snaptech.ezeego1.Manager.PhotoManager;
import com.snaptech.ezeego1.R;
import com.snaptech.ezeego1.UI.Adapter.AlbumImagePagerAdapter;
import com.snaptech.ezeego1.UI.Fragment.PhotosFragment;
import com.snaptech.ezeego1.UI.Model.AlbumDetails;
import com.snaptech.ezeego1.Utility.Constants;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class AlbumFullScreenActivity extends ActionBarActivity {

    private ViewPager imageViewPager;
    private AlbumImagePagerAdapter albumImagePagerAdapter;
    private List<AlbumDetails> albumDetailsList = new ArrayList<AlbumDetails>();
    ProgressDialog mProgressDialog;
    int position;
    boolean flag_download=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        albumDetailsList.addAll(PhotoManager.getSharedInstance().albumDetailsList);

        populateDummyData();
        setContentView(R.layout.activity_album_full_screen);
        imageViewPager = (ViewPager)findViewById(R.id.pager);

        position = getIntent().getIntExtra("position",0);
        getSupportActionBar().setTitle("");


        albumImagePagerAdapter = new AlbumImagePagerAdapter(this, albumDetailsList);
        imageViewPager.setAdapter(albumImagePagerAdapter);
        imageViewPager.setCurrentItem(position);
    }

    void populateDummyData(){


    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // If the nav drawer is open, hide action items related to the content view
//
//
//
//        // boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_settings).setVisible(false);
//
//        return super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_album_full_screen, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // Activate the navigation drawer toggle
        if (item.getItemId() == R.id.action_download) {
            //TODO: download
            int position = imageViewPager.getCurrentItem();
            String imageUrl = albumDetailsList.get(position).getFilePath();
            new ImageDownloader().execute(imageUrl);
            return true;
        }if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(AlbumFullScreenActivity.this);
            // Set progress dialog title
            //mProgressDialog.setTitle("Download Image Tutorial");
            // Set progress dialog message
            mProgressDialog.setMessage("Downloading...");
            mProgressDialog.setIndeterminate(false);
            // Show progress dialog
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String imageURL = params[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                URI uri = new URI(imageURL.replace(" ", "%20"));
                System.out.println("Image url hit is "+uri.toString());
                InputStream input = new java.net.URL(uri.toString()).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
                flag_download=false;
            }
            return bitmap;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mProgressDialog.dismiss();


            // save image to gallery
            storeImage(bitmap);
            if(flag_download){

                Toast.makeText(AlbumFullScreenActivity.this,"Image downloaded successfully",Toast.LENGTH_LONG).show();
            }
            else{

                Toast.makeText(AlbumFullScreenActivity.this,"Something went wrong, please check internet connection.",Toast.LENGTH_LONG).show();
                flag_download=true;
            }
        }


        public void storeImage(Bitmap bitmap){
                //get path to external storage (SD card)
                String iconsStoragePath = Constants.PHOTO_SAVE_GALLERY_DIR_IMAGE_PATH  + PhotosFragment.albumName;
                File sdIconStorageDir = new File(iconsStoragePath);

                //create storage directories, if they don't exist
            if (!sdIconStorageDir.mkdir()){
                sdIconStorageDir.mkdirs();
            }

                try {
                    String filePath = sdIconStorageDir.toString() + "/image-" + position +".jpeg";
                    FileOutputStream fileOutputStream = new FileOutputStream(filePath);

                    BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

                    //choose another format if PNG doesn't suit you
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);

                    addImageToGallery(filePath);

                    bos.flush();
                    bos.close();

                } catch (FileNotFoundException e) {
                    Log.w("TAG", "Error saving image file: " + e.getMessage());
                    flag_download=false;

                } catch (IOException e) {
                    Log.w("TAG", "Error saving image file: " + e.getMessage());
                    flag_download=false;

                }
                catch (Exception e){

                    Log.w("TAG", "Error saving image file: " + e.getMessage());
                    flag_download=false;
                }
        }


        public  void addImageToGallery(final String filePath) {

            ContentValues values = new ContentValues();

            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.MediaColumns.DATA, filePath);

            getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
    }
}
