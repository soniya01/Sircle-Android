package com.snaptech.hasanaths.UI.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.snaptech.hasanaths.R;


public class PDFViewer extends Activity {


    private ProgressDialog pDialog;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

//        pdfPageCount = (TextView)findViewById(R.id.pdfPageCount);
//
//        pdfView = (PDFView) findViewById(R.id.pdfview);

        // pdfView.
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading");
        mProgressDialog.setIndeterminate(true);
        //  mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //  mProgressDialog.setCancelable(true);

        if(getIntent()!=null) {

            if(getIntent().getExtras()!=null) {

                if(getIntent().getExtras().getString("PdfUrl")!=null&&getIntent().getExtras().getString("PdfName")!=null) {

                    String pdfUrl = getIntent().getExtras().getString("PdfUrl");
                    String pdfFile = getIntent().getExtras().getString("PdfName");
                    view(pdfUrl, pdfFile);

                }
            }
        }
    }


//    public void download(String url,String fileName)
//    {
//        Uri uri = Uri.parse(url);
//        //String fileName = uri.getLastPathSegment();
//        new DownloadFile().execute(url, fileName);
//    }

    public void view(String url,String filename)
    {
        Uri uri = Uri.parse(url);
        //   String fileName = uri.getLastPathSegment();

        String fileName = filename;
        WebView webView = (WebView) findViewById(R.id.webview);

        pDialog= new ProgressDialog(this,R.style.MyTheme);
        pDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pDialog.setCancelable(false);
        pDialog.show();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                pDialog.show();
                view.loadUrl(url);

                return true;
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
                pDialog.dismiss();
            }

            @Override
            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
                pDialog.dismiss();
                final AlertDialog.Builder builder = new AlertDialog.Builder(PDFViewer.this);
                builder.setMessage("Do you want to continue?");
                builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.proceed();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.cancel();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();// Ignore SSL certificate errors
            }

        });
        webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url="+url);
        //File pdfFile = new File(Environment.getExternalStorageDirectory() + "/Sircle/" + fileName);  // -> filename
        //long len = pdfFile.length();
        //if(pdfFile.exists() && pdfFile.canRead() && pdfFile.length() != 0) {
        //  pdfView.fromFile(pdfFile).defaultPage(1).onPageChange(this).load();
        //pdfPageCount.setText(pdfView.getCurrentPage()+"/"+pdfView.getPageCount());


        //}
        //else {
        // download(url,fileName);
        //}

    }

//    @Override
//    public void onPageChanged(int page, int pageCount) {
//        pdfPageCount.setText(page+"/"+pageCount);
//    }

//    private class DownloadFile extends AsyncTask<String, Void, Void> {
//
//        String fileName;
//
//        @Override
//        protected Void doInBackground(String... strings) {
//
//            //  Toast.makeText(MainActivity.this,"File download started", Toast.LENGTH_SHORT).show();
//
//            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
//            fileName = strings[1];  // -> maven.pdf
//            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
//            File folder = new File(extStorageDirectory, "Sircle");
//            folder.mkdir();
//
//            File pdfFile = new File(folder, fileName);
//
//            try{
//                pdfFile.createNewFile();
//                FileDownloader.downloadFile(fileUrl, pdfFile);
//            }catch (IOException e){
//                //Toast.makeText(PDFViewer.this, "Error occurred", Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }catch (Exception e){
//                //Toast.makeText(PDFViewer.this, Constants.NO_NET_CONNECTIVITY_MESSAGE, Toast.LENGTH_SHORT).show();
//            }
//            try {
//                Class.forName("android.os.AsyncTask");
//            } catch (ClassNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//
//         @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            mProgressDialog.show();
//
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
////                        if (aVoid == null) {
////
////            }
////            else
////            {
//                mProgressDialog.dismiss();
//
//                File pdfFile = new File(Environment.getExternalStorageDirectory() + "/Sircle/" + fileName);  // -> filename
//                if(pdfFile.exists() && pdfFile.length() != 0) {
//                   // pdfView.fromFile(pdfFile).defaultPage(1).load();
//                    pdfView.fromFile(pdfFile).defaultPage(1).onPageChange(PDFViewer.this).load();
//                    pdfPageCount.setText(pdfView.getCurrentPage() + "/" + pdfView.getPageCount());
//                }
//
//         //   }
//           // return;
//
//        }
//        }
//
//
////        @Override
////        protected void onPostExecute(Void result) {
////            //  progressDialog.dismiss();
//
// //}


}