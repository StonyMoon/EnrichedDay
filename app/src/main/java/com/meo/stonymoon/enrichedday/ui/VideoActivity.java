package com.meo.stonymoon.enrichedday.ui;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.util.HttpUtil;

import java.io.IOException;
import java.util.HashMap;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.meo.stonymoon.enrichedday.util.HttpUtil.sendBilibiliRequest;


public class VideoActivity extends AppCompatActivity {
    JCVideoPlayerStandard playerStandard;

    @Override
    protected void onResume() {
        super.onResume();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        playerStandard = (JCVideoPlayerStandard) findViewById(R.id.video);
        playerStandard.setVisibility(View.GONE);

        String cid = getIntent().getStringExtra("id");
        init(cid);


        playerStandard.headData = new HashMap<>();
        playerStandard.headData.put("Referer", "http://www.bilibili.com/video/av17008/?from=search");
        playerStandard.headData.put("user-agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");

    }

    private void init(String cid) {
        sendBilibiliRequest("http://120.24.238.200:9999/api/url/bangumi/" + cid, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String url = response.body().string();
                playerStandard.setUp(url, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "bilibili");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        playerStandard.setVisibility(View.VISIBLE);


                    }
                });

            }
        });


    }


    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

}
