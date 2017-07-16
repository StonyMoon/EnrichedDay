package com.meo.stonymoon.enrichedday.ui.discovery.child;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.bean.BangumiDetailBean;
import com.meo.stonymoon.enrichedday.util.HandleResponseUtil;
import com.meo.stonymoon.enrichedday.util.HttpUtil;

import java.io.IOException;

import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BangumiDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView coverImage;
    private ImageView bgImage;
    private TextView stuffText;
    private TextView evaluateText;
    private CollapsingToolbarLayout toolbar;
    private ActionBar actionBar;
    private Toolbar tb;
    private String id;
    private FloatingActionButton playButton;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("bangumiId");

        setContentView(R.layout.activity_bangumi_detail);
        coverImage = (ImageView) findViewById(R.id.bangumi_detail_cover_image);
        bgImage = (ImageView) findViewById(R.id.bangumi_detail_bg);
        stuffText = (TextView) findViewById(R.id.bangumi_staff);
        evaluateText = (TextView) findViewById(R.id.bangumi_detail_evaluate);
        playButton = (FloatingActionButton) findViewById(R.id.bangumi_detail_play);
        //设置actionbar
        toolbar = (CollapsingToolbarLayout) findViewById(R.id.bangumi_detail_toolbar);
        tb = (Toolbar) findViewById(R.id.bangumi_detail_tb);
        setSupportActionBar(tb);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }

        playButton.setOnClickListener(this);

        initDetail(id);

    }

    private void initDetail(String id) {
        HttpUtil.sendOkHttpRequest("http://bangumi.bilibili.com/jsonp/seasoninfo/" + id + ".ver?callback=seasonListCallback&jsonp=jsonp&_=1499651889555", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final BangumiDetailBean bean = HandleResponseUtil.handleBangumiDetailResponse(response.body().string());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stuffText.setText(bean.result.staff);
                        Glide.with(BangumiDetailActivity.this)
                                .load(bean.result.cover)
                                .into(coverImage);
                        Glide.with(BangumiDetailActivity.this)
                                .load(bean.result.cover)
                                .bitmapTransform(new BlurTransformation(BangumiDetailActivity.this, 14, 5))
                                .into(bgImage);
                        evaluateText.setText(bean.result.evaluate);
                        toolbar.setTitle(bean.result.title);

                    }
                });

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bangumi_detail_play:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://bangumi.bilibili.com/anime/" + id));
                startActivity(intent);
                break;


        }

    }
}
