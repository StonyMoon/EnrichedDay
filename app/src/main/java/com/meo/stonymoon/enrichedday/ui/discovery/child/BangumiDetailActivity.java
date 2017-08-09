package com.meo.stonymoon.enrichedday.ui.discovery.child;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.util.ExceptionCatchingInputStream;
import com.jaeger.library.StatusBarUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.adapter.EpisodeAdapter;
import com.meo.stonymoon.enrichedday.bean.BangumiBean;
import com.meo.stonymoon.enrichedday.bean.BangumiDetailBean;
import com.meo.stonymoon.enrichedday.ui.VideoActivity;
import com.meo.stonymoon.enrichedday.util.HandleResponseUtil;
import com.meo.stonymoon.enrichedday.util.HttpUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BangumiDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView coverImage;
    private RelativeLayout bgLayout;
    private ImageView bgImage;

    private TextView stuffText;
    private TextView evaluateText;
    private CollapsingToolbarLayout toolbar;
    private ActionBar actionBar;
    private Toolbar tb;
    private String id;
    private FloatingActionButton playButton;
    private XRecyclerView episodeList;
    private List<BangumiDetailBean.EpisodeBean> episodeBeanList = new ArrayList<>();
    private EpisodeAdapter adapter = new EpisodeAdapter(episodeBeanList);
    private View headView;

    public BangumiDetailActivity() {
    }

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
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("bangumiId");
        setContentView(R.layout.activity_bangumi_detail);
        StatusBarUtil.setTransparent(this);
        coverImage = (ImageView) findViewById(R.id.bangumi_detail_cover_image);
        bgLayout = (RelativeLayout) findViewById(R.id.bangumi_detail_bar_bg);
        bgImage = (ImageView) findViewById(R.id.bangumi_detail_bg);
        stuffText = (TextView) findViewById(R.id.bangumi_staff);
        playButton = (FloatingActionButton) findViewById(R.id.bangumi_detail_play);
        headView = LayoutInflater.from(this).inflate(R.layout.head_bangumi_detail, (ViewGroup) findViewById(R.id.bangumi_detail_layout), false);
        episodeList = (XRecyclerView) findViewById(R.id.episode_list);
        episodeList.setAdapter(adapter);
        episodeList.setLayoutManager(new LinearLayoutManager(this));
        episodeList.addHeaderView(headView);
        episodeList.setPullRefreshEnabled(false);
        evaluateText = (TextView) headView.findViewById(R.id.bangumi_detail_evaluate);
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
                int len = bean.result.episodes.size();
                for (int i = 0; i < len; i++) {
                    episodeBeanList.add(bean.result.episodes.get(len - i - 1));
                }
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
                        adapter.notifyDataSetChanged();

                    }
                });

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bangumi_detail_play:
                Intent intent = new Intent(this, VideoActivity.class);
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("https://bangumi.bilibili.com/anime/" + id));
                startActivity(intent);
                break;

        }

    }


}
