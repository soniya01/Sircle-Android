package com.snaptech.asb.UI.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;

import com.snaptech.asb.R;

public class VimeoWebviewActivity extends AppCompatActivity {

    WebView myWebView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vimeo_webview);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        myWebView = (WebView)findViewById(R.id.myWebView);

        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setAppCacheEnabled(true);
        myWebView.getSettings().setDomStorageEnabled(true);

        String videoUrl = "";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            videoUrl = extras.getString("VideoUrl");
        }

//        // how plugin is enabled change in API 8
//        if (Build.VERSION.SDK_INT < 8) {
//           // myWebView.getSettings().setPluginsEnabled(true);
//        } else {
//            myWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
//        }
       // String venkat="<iframe src=\"http://player.vimeo.com/video/88806672?portrait=0&color=333\" width=\"WIDTH\" height=\"HEIGHT\" frameborder=\"0\" webkitAllowFullScreen mozallowfullscreen allowFullScreen></iframe>";
       // myWebView.loadData(venkat, "text/html", "UTF-8");

        videoUrl = "https://player.vimeo.com/video/"+videoUrl;

       myWebView.loadUrl(videoUrl);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode==event.KEYCODE_BACK)
        {
            myWebView.loadUrl("");
            myWebView.stopLoading();

            finish();

        }
        return super.onKeyDown(keyCode, event);
    }


}
