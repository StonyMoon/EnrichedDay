package com.meo.stonymoon.enrichedday.ui.discovery.child;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.adapter.ChapterAdapter;
import com.meo.stonymoon.enrichedday.bean.ComicDetailBean;
import com.meo.stonymoon.enrichedday.util.HandleResponseUtil;
import com.meo.stonymoon.enrichedday.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ComicDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView coverImage;
    private ImageView bgImage;
    private TextView stuffText;
    private TextView evaluateText;
    private CollapsingToolbarLayout toolbar;
    private ActionBar actionBar;
    private Toolbar tb;
    private int id;
    private FloatingActionButton playButton;
    private String coverUrl;
    private String evaluate;
    private String title;
    private RecyclerView chapterRecycleView;
    private List<ComicDetailBean.ComicChapter> chapterList = new ArrayList<>();
    private ChapterAdapter adapter = new ChapterAdapter(chapterList);

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
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 1234);
        coverUrl = intent.getStringExtra("coverUrl");
        evaluate = intent.getStringExtra("evaluate");
        title = intent.getStringExtra("title");
        setContentView(R.layout.activity_comic_detail);
        coverImage = (ImageView) findViewById(R.id.comic_detail_cover_image);
        bgImage = (ImageView) findViewById(R.id.comic_detail_bg);
        stuffText = (TextView) findViewById(R.id.comic_staff);
        evaluateText = (TextView) findViewById(R.id.comic_detail_evaluate);
        playButton = (FloatingActionButton) findViewById(R.id.comic_detail_play);
        //设置actionbar
        toolbar = (CollapsingToolbarLayout) findViewById(R.id.comic_detail_toolbar);
        chapterRecycleView = (RecyclerView) findViewById(R.id.chapter_recycler_view);
        chapterRecycleView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        chapterRecycleView.setLayoutManager(linearLayoutManager);
        tb = (Toolbar) findViewById(R.id.comic_detail_tb);
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

    private void initDetail(int id) {
        HttpUtil.sendOkHttpRequest("http://app.u17.com/v3/appV3_3/android/phone/comic/detail_static_new?v=3320100&comicid=" + id + "&model=Lenovo+P1c72&come_from=Tg002&android_id=4558b957bedb8f36", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final ComicDetailBean bean = HandleResponseUtil.handleComicDetailResponse(response.body().string());
                chapterList.addAll(bean.data.returnData.comicChapterList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Glide.with(ComicDetailActivity.this)
                                .load(coverUrl)
                                .into(coverImage);
                        Glide.with(ComicDetailActivity.this)
                                .load(coverUrl)
                                .bitmapTransform(new BlurTransformation(ComicDetailActivity.this, 14, 5))
                                .into(bgImage);
                        evaluateText.setText(evaluate);
                        toolbar.setTitle(title);
                        adapter.notifyDataSetChanged();


                    }
                });

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comic_detail_play:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://bangumi.bilibili.com/anime/" + id));
                startActivity(intent);
                break;

        }

    }
}
