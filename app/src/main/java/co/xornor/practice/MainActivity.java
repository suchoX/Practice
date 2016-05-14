package co.xornor.practice;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    VideoView view;

    Toolbar toolbar;

    String link="https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
    Button change,changeland;
    Boolean landscape = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        changeland = (Button)findViewById(R.id.changeland);
        changeland.setVisibility(View.GONE);
        change = (Button)findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(landscape)
                    potraitMode();
                else
                    landscapeMode();
            }
        });
        changeland.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("VIDEOX","Clicked");
                potraitMode();
                changeland.setVisibility(View.GONE);
            }
        });
        if(savedInstanceState==null)
            playVideo();
        else
            playVideo(savedInstanceState.getInt("Video"));
    }

    private void playVideo()
    {
        view = (VideoView) findViewById(R.id.video_view);
        //getWindow().setFormat(PixelFormat.TRANSLUCENT);
        MediaController mc = new MediaController(this);
        mc.setMediaPlayer(view);
        view.setMediaController(mc);
        view.setVideoURI(Uri.parse(link));
        view.requestFocus();
        view.start();
    }

    private void playVideo(int time)
    {
        view = (VideoView) findViewById(R.id.video_view);
        //getWindow().setFormat(PixelFormat.TRANSLUCENT);
        MediaController mc = new MediaController(this);
        mc.setMediaPlayer(view);
        view.setMediaController(mc);
        view.setVideoURI(Uri.parse(link));
        view.requestFocus();
        view.seekTo(time);
        view.start();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("Video",view.getCurrentPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
            landscapeMode();
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
            potraitMode();
    }

    private void landscapeMode()
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        landscape = true;
        toolbar.setVisibility(View.GONE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        changeland.setVisibility(View.VISIBLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        DisplayMetrics metrics = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) view.getLayoutParams();
        params.width =  metrics.widthPixels;
        params.height = metrics.heightPixels;
        params.leftMargin = 0;
        view.setLayoutParams(params);
    }

    private void potraitMode()
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        landscape = false;
        toolbar.setVisibility(View.VISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        DisplayMetrics metrics = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) view.getLayoutParams();
        params.width =  (int) (300*metrics.density);
        params.height = (int) (250*metrics.density);
        params.leftMargin = 30;
        view.setLayoutParams(params);
    }


}
