package com.snaptech.quadeia.UI.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.snaptech.quadeia.DownLoader.FileDownloader;
import com.snaptech.quadeia.R;
import com.snaptech.quadeia.Utility.Constants;
import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;

import java.io.File;
import java.io.IOException;

import static java.lang.String.format;

public class PDFViewer extends Activity implements OnPageChangeListener {

    PDFView pdfView;

TextView pdfPageCount;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        pdfPageCount = (TextView)findViewById(R.id.pdfPageCount);

        pdfView = (PDFView) findViewById(R.id.pdfview);

       // pdfView.
       mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading");
       mProgressDialog.setIndeterminate(true);
      //  mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
      //  mProgressDialog.setCancelable(true);

        String pdfUrl = getIntent().getExtras().getString("PdfUrl");

        String pdfFile = getIntent().getExtras().getString("PdfName");

        view(pdfUrl,pdfFile);
    }
    

    public void download(String url,String fileName)
    {
        Uri uri = Uri.parse(url);
        //String fileName = uri.getLastPathSegment();
        new DownloadFile().execute(url, fileName);
    }

    public void view(String url,String filename)
    {
        Uri uri = Uri.parse(url);
     //   String fileName = uri.getLastPathSegment();

        String fileName = filename;


        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/Sircle/" + fileName);  // -> filename
        long len = pdfFile.length();
        if(pdfFile.exists() && pdfFile.canRead() && pdfFile.length() != 0) {
            pdfView.fromFile(pdfFile).defaultPage(1).onPageChange(this).load();
            pdfPageCount.setText(pdfView.getCurrentPage()+"/"+pdfView.getPageCount());
        }
        else {
            download(url,fileName);
        }

    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pdfPageCount.setText(page+"/"+pageCount);
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
                FileDownloader.downloadFile(fileUrl, pdfFile);
            }catch (IOException e){
                Toast.makeText(PDFViewer.this, "Error occurred", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }catch (Exception e){
                Toast.makeText(PDFViewer.this, Constants.NO_NET_CONNECTIVITY_MESSAGE, Toast.LENGTH_SHORT).show();
            }
            try {
                Class.forName("android.os.AsyncTask");
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }


         @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//                        if (aVoid == null) {
//
//            }
//            else
//            {
                mProgressDialog.dismiss();

                File pdfFile = new File(Environment.getExternalStorageDirectory() + "/Sircle/" + fileName);  // -> filename
                if(pdfFile.exists() && pdfFile.length() != 0) {
                   // pdfView.fromFile(pdfFile).defaultPage(1).load();
                    pdfView.fromFile(pdfFile).defaultPage(1).onPageChange(PDFViewer.this).load();
                    pdfPageCount.setText(pdfView.getCurrentPage() + "/" + pdfView.getPageCount());
                }

         //   }
           // return;

        }
        }


//        @Override
//        protected void onPostExecute(Void result) {
//            //  progressDialog.dismiss();

 //}


}
