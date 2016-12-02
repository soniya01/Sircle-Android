package com.snaptech.institutoverdemolina.UI.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import com.snaptech.institutoverdemolina.R;
import com.snaptech.institutoverdemolina.Utility.Common;
import com.snaptech.institutoverdemolina.Utility.Constants;

public class VideoActivity extends Activity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, View.OnTouchListener{

    String videoUrl = "";
    private VideoView videoView;
    private boolean isPlaying = false;
    private Uri uriYouTube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoUrl = getIntent().getStringExtra("videoUrl");
        videoView = (VideoView)findViewById(R.id.videoPlayingView);
        videoView.setOnCompletionListener(this);
        videoView.setOnPreparedListener(this);
        videoView.setOnTouchListener(this);

        if (Common.isConnectingToInternet(this)){
            checkIfVideoIsPlaying(savedInstanceState);
           // playVideo();

            videoView.start();
        } else {
            // display no net connectivity message
            Toast.makeText(VideoActivity.this, Constants.NO_NET_CONNECTIVITY_MESSAGE, Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    /**
     * checks if video was already started
     *
     * @param savedInstanceState
     */
    public void checkIfVideoIsPlaying(Bundle savedInstanceState){
        if (savedInstanceState != null) {
            int loc = savedInstanceState.getInt("Loc");
            Log.i("Location: ", loc + "");
            uriYouTube = Uri.parse(savedInstanceState.getString("url"));
            videoView.setVideoURI(uriYouTube);
            videoView.seekTo(loc);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.v("onPrepared", "ok");
                    mp.start();
                }
            });
        } else {
            RSAsyncTask asyncTask = new RSAsyncTask();
            asyncTask.execute(videoUrl);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Loc", videoView.getCurrentPosition());
        outState.putString("url", uriYouTube.toString());
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        //finish();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
            mp.setLooping(true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        stopPlaying();
        return false;
    }

    /**
     * start playing the video
     * @param url
     */
    public void startPlaying(String url){
        uriYouTube = Uri.parse(url);
        videoView.setVideoURI(uriYouTube);
        videoView.start();
    }

    /**
     * stop playing the video
     */
    public void stopPlaying(){
        videoView.stopPlayback();
        this.finish();
    }

    /**
     * check if video is playing if so then pause it else play it
     */
    public void playVideo(){

        if (isPlaying) {
            stopPlaying();
        } else {
            startPlaying(videoUrl);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //stopPlaying();
        videoView.stopPlayback();
    }

    @Override
    public void onBackPressed() {
        //stopPlaying();
        videoView.stopPlayback();
        finish();
    }

    /**
     * class to fetch the video and play it simultaneously
     */
    public class RSAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String response = "";
            try{
                String url = params[0];
                response = Common.getUrlVideoRTSP(url);
            }catch (Exception e){
                Toast.makeText(VideoActivity.this, "Compruebe internet", Toast.LENGTH_SHORT).show();
                VideoActivity.this.finish();
            }

            return response;

        }

        @Override
        protected void onPostExecute(String result) {
            startPlaying(result);
        }
    }
}
