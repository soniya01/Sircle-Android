package com.app.sircle.UI.Activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.VideoView;

import com.app.sircle.R;

public class VideoActivity extends Activity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, View.OnTouchListener{

    String videoUrl = "";
    private VideoView videoView;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoUrl = getIntent().getStringExtra("videoUrl");
        videoView = (VideoView)findViewById(R.id.videoPlayingView);
        videoView.setOnCompletionListener(this);
        videoView.setOnPreparedListener(this);
        videoView.setOnTouchListener(this);

        playVideo();

        videoView.start();
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

    public void stopPlaying(){
        videoView.stopPlayback();
        this.finish();
    }

    public void playVideo(){

        if (isPlaying) {
            stopPlaying();
        } else {
            videoView.setVideoURI(Uri.parse(videoUrl));
        }
    }
}
