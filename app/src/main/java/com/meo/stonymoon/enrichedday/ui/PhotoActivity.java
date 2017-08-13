package com.meo.stonymoon.enrichedday.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.meo.stonymoon.enrichedday.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

public class PhotoActivity extends AppCompatActivity {
    private String url;
    private String pixivUrl;
    private PhotoView photoView;
    private FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("imageUrl");
        pixivUrl = getIntent().getStringExtra("pixivUrl");
        setContentView(R.layout.activity_photo);
        photoView = (PhotoView) findViewById(R.id.photo_view);
        button = (FloatingActionButton) findViewById(R.id.pixiv_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(pixivUrl));
                startActivity(intent);
            }
        });

        Picasso.with(this)
                .load(url)
                .into(photoView);


    }
}
