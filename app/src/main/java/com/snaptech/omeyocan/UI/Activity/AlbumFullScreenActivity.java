package com.snaptech.omeyocan.UI.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.snaptech.omeyocan.Manager.PhotoManager;
import com.snaptech.omeyocan.R;
import com.snaptech.omeyocan.UI.Adapter.AlbumImagePagerAdapter;
import com.snaptech.omeyocan.UI.Fragment.PhotosFragment;
import com.snaptech.omeyocan.UI.Model.AlbumDetails;
import com.snaptech.omeyocan.Utility.Constants;

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
    private boolean flag_permission_ex_storage=false;
    private String imageUrl="";
    private boolean flag_download=true;

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
        getSupportActionBar().setTitle(albumDetailsList.get(position).getAlbumName());


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


            imageUrl = albumDetailsList.get(position).getFilePath();
            if(!checkExternalStoragePermission()) {
                flag_permission_ex_storage=false;
                ContextCompat.checkSelfPermission(AlbumFullScreenActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            else
                flag_permission_ex_storage=true;

            if(flag_permission_ex_storage) {
                position = imageViewPager.getCurrentItem();
                imageUrl = albumDetailsList.get(position).getFilePath();
                new ImageDownloader().execute(imageUrl);
            }
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
            if(flag_download)
                Toast.makeText(AlbumFullScreenActivity.this,"Imagen descargado con éxito",Toast.LENGTH_LONG).show();
            else{
                Toast.makeText(AlbumFullScreenActivity.this,"Compruebe la conexión a Internet",Toast.LENGTH_LONG).show();
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

                e.printStackTrace();
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
    private boolean checkExternalStoragePermission()
    {

        if (Build.VERSION.SDK_INT >= 23) {
            if (AlbumFullScreenActivity.this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v(TAG,"Permission is granted");
                System.out.println("First condition");
                return true;
            } else {

                System.out.println("Second condition");
                //Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(AlbumFullScreenActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            // Log.v(TAG,"Permission is granted");
            System.out.println("Third condition");
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        System.out.println("Called request permission");
        switch (requestCode) {


            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    System.out.println("Inside case 1");

                    if(imageUrl!=null)
                        if(!imageUrl.trim().equals(""))
                            new ImageDownloader().execute(imageUrl);
                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    Toast.makeText(AlbumFullScreenActivity.this,"Dé permiso de almacenamiento.",Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }
}