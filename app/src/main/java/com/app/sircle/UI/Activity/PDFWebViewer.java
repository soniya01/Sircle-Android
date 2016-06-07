package com.app.sircle.UI.Activity;

import android.app.ProgressDialog;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.sircle.R;

public class PDFWebViewer extends AppCompatActivity {

    ProgressDialog progDailog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfweb_viewer);

        WebView webView = (WebView) findViewById(R.id.webview);

        progDailog = ProgressDialog.show(this, "Loading","Please wait...", true);
        progDailog.setCancelable(false);



       // webView = (WebView) findViewById(R.id.webview_compontent);



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
                progDailog.show();
                view.loadUrl(url);

                return true;
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
                progDailog.dismiss();
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // Ignore SSL certificate errors
            }

        });

//        webView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int progress) {
//                if (progress == 100) {
//                   // progressBar.setVisibility(View.INVISIBLE);
//                   // progressBar.setProgress(0);
//                } else {
//                   // progressBar.setVisibility(View.VISIBLE);
//                  //  progressBar.setProgress(progress);
//                }
//            }
//
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//             //   titleView.setText(title);
//            }
//
//
//        });

        webView.loadUrl("http://54.251.157.35/event/index.php?route=tool/upload/view&code=916a22c70a507d7791f4969c6ac6f41f5dab1edf&view_type=newspaper");

      //  browser.loadUrl("");
    }
}
