package com.app.sircle.UI.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.app.sircle.DownLoader.FileDownloader;
import com.app.sircle.R;
import com.joanzapata.pdfview.PDFView;

import java.io.File;
import java.io.IOException;

public class PDFViewer extends Activity {

    PDFView pdfView;


    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        pdfView = (PDFView) findViewById(R.id.pdfview);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
      //  mProgressDialog.setCancelable(true);

        String pdfUrl = getIntent().getExtras().getString("PdfUrl");

        view(pdfUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pdfviewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void download(String url)
    {
        Uri uri = Uri.parse(url);
        String fileName = uri.getLastPathSegment();
        new DownloadFile().execute(url, fileName);
    }

    public void view(String url)
    {


        Uri uri = Uri.parse(url);
        String fileName = uri.getLastPathSegment();

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/Sircle/" + fileName);  // -> filename
        if(pdfFile.exists())
        {
            pdfView.fromFile(pdfFile).defaultPage(1).load();

        }
        else
        {
            download("http://fzs.sve-mo.ba/sites/default/files/dokumenti-vijesti/sample.pdf");
        }

    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        String fileName;

        @Override
        protected Void doInBackground(String... strings) {

            //  Toast.makeText(MainActivity.this,"File download started", Toast.LENGTH_SHORT).show();

            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "Sircle");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }


        // @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();

        }



        @Override
        protected void onPostExecute(Void result) {
            //  progressDialog.dismiss();
            if (result != null) {

            }
            else
            {
                mProgressDialog.dismiss();

                File pdfFile = new File(Environment.getExternalStorageDirectory() + "/Sircle/" + fileName);  // -> filename
                if(pdfFile.exists()) {
                    pdfView.fromFile(pdfFile).defaultPage(1).load();
                }

            }
            return;

        }
    }


}
