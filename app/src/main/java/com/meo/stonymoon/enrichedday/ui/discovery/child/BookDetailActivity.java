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


import jp.wasabeef.glide.transformations.BlurTransformation;


public class BookDetailActivity extends AppCompatActivity {
    private ImageView coverImage;
    private ImageView bgImage;
    private TextView evaluateText;
    private TextView authorText;
    private TextView authorIntroText;
    private TextView pointText;
    private TextView pubdateText;


    private CollapsingToolbarLayout toolbar;
    private ActionBar actionBar;
    private Toolbar tb;

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

        setContentView(R.layout.activity_book_detail);
        coverImage = (ImageView) findViewById(R.id.book_detail_cover_image);
        bgImage = (ImageView) findViewById(R.id.book_detail_bg);
        evaluateText = (TextView) findViewById(R.id.book_detail_evaluate);
        authorIntroText = (TextView) findViewById(R.id.book_detail_author_intro);
        pointText = (TextView) findViewById(R.id.book_detail_point);
        pubdateText = (TextView) findViewById(R.id.book_detail_pubdate);
        authorText = (TextView) findViewById(R.id.book_detail_author);


        //设置actionbar
        toolbar = (CollapsingToolbarLayout) findViewById(R.id.book_detail_toolbar);
        authorText = (TextView) findViewById(R.id.book_detail_author);


        tb = (Toolbar) findViewById(R.id.book_detail_tb);
        setSupportActionBar(tb);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }


        initDetail();

    }


    private void initDetail() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("imageUrl");
        toolbar.setTitle(intent.getStringExtra("title"));
        pointText.setText("评分：" + intent.getStringExtra("average") + " " + intent.getIntExtra("numRaters", 0) + "人评分");
        pubdateText.setText("出版时间：" + intent.getStringExtra("pubdate"));
        authorIntroText.setText(intent.getStringExtra("authorIntro"));
        evaluateText.setText(intent.getStringExtra("summary"));
        Glide.with(BookDetailActivity.this)
                .load(url)
                .into(coverImage);
        Glide.with(BookDetailActivity.this)
                .load(url)
                .bitmapTransform(new BlurTransformation(BookDetailActivity.this, 14, 5))
                .into(bgImage);

    }


}
