package com.snaptech.centroeducativomapi.UI.cam.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.snaptech.centroeducativomapi.R;
import com.snaptech.centroeducativomapi.UI.cam.Config;

import java.io.File;

public class PhotoPreviewActivity extends AppCompatActivity {
    private static final String TAG = PhotoPreviewActivity.class.getSimpleName();
    private static final int REQUEST_CAMERA = 0x000FFF;

    private Context mContext, aContext;
   // private Toolbar toolbar;
    //private TextView lblHeader;
    private ImageView imgCapture;
    private ImageButton btnRemove;

    private int identifier;
    private Uri uri;
    private int width;
    private int height;
    private int compression;
    private Bitmap.CompressFormat format;
    private Config.CameraFace face;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);

        initVals();
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo_preview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_next) {
            if (null != uri) {
                File mediaFile = new File(uri.getPath());
                Intent intent = new Intent();
                intent.setData(Uri.fromFile(mediaFile));
                intent.putExtra(Config.KEY_DATA, mediaFile.getAbsolutePath());
                intent.putExtra(Config.KEY_MEDIAFILE, mediaFile);
                intent.putExtra(Config.KEY_IDENTIFIER, identifier);
                setResult(RESULT_OK, intent);
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CAMERA == requestCode && RESULT_OK == resultCode) {
            uri = data.getData();
            imgCapture.setScaleType(ImageView.ScaleType.FIT_XY);
            imgCapture.setAdjustViewBounds(true);
            imgCapture.setImageURI(uri);
        } else if (RESULT_CANCELED == resultCode){
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_remove:
                    startCamera();
                    break;
            }
        }
    };

    private void initVals() {
        mContext = PhotoPreviewActivity.this;
        aContext = mContext.getApplicationContext();

        width = 1080;
        height = 1080;
        compression = 90;
        format = Bitmap.CompressFormat.JPEG;
        face = Config.CameraFace.BACK;
        retrieveBundle();
        startCamera();
    }

    private void initViews() {

        imgCapture = (ImageView) findViewById(R.id.img_capture);
        btnRemove = (ImageButton) findViewById(R.id.btn_remove);
      //  toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
      //  lblHeader = (TextView) toolbar.findViewById(R.id.toolbar_title);

      //  setSupportActionBar(toolbar);
        if (null != getSupportActionBar())
            getSupportActionBar().setDisplayShowTitleEnabled(false);

       // lblHeader.setText("");
       // lblHeader.setTextColor(getResources().getColor(R.color.background));
      //  toolbar.setNavigationIcon(R.drawable.close);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                back();
//            }
//        });
        btnRemove.setOnClickListener(onClickListener);
    }

    private void retrieveBundle() {
        Bundle bundle = getIntent().getExtras();

        if (null != bundle && Bundle.EMPTY != bundle) {
            if (bundle.containsKey(Config.KEY_HEIGHT))
                height = bundle.getInt(Config.KEY_HEIGHT);
            if (bundle.containsKey(Config.KEY_WIDTH))
                width = bundle.getInt(Config.KEY_WIDTH);
            if (bundle.containsKey(Config.KEY_COMPRESSION))
                compression = bundle.getInt(Config.KEY_COMPRESSION);
            if (bundle.containsKey(Config.KEY_IMG_FORMAT))
                format = (Bitmap.CompressFormat) bundle.get(Config.KEY_IMG_FORMAT);
            if (bundle.containsKey(Config.KEY_CAM_FACE))
                face = (Config.CameraFace) bundle.get(Config.KEY_CAM_FACE);
            if (bundle.containsKey(Config.KEY_IDENTIFIER))
                identifier = bundle.getInt(Config.KEY_IDENTIFIER);
        }


    }

    private void startCamera() {
        Intent intent = new Intent(mContext, CameraActivity.class);
        intent.putExtra(Config.KEY_HEIGHT, height);
        intent.putExtra(Config.KEY_WIDTH, width);
        intent.putExtra(Config.KEY_COMPRESSION, compression);
        intent.putExtra(Config.KEY_IMG_FORMAT, format);
        intent.putExtra(Config.KEY_CAM_FACE, face);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void back() {
        finish();
    }
}
