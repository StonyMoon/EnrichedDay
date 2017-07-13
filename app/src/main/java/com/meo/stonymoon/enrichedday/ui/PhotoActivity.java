package com.meo.stonymoon.enrichedday.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.meo.stonymoon.enrichedday.R;
import com.github.chrisbanes.photoview.PhotoView;

public class PhotoActivity extends AppCompatActivity {
    private String url;
    private PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("imageUrl");
        setContentView(R.layout.activity_photo);
        photoView = (PhotoView) findViewById(R.id.photo_view);
        Glide.with(this)
                .load(url)
                .into(photoView);


    }
}
