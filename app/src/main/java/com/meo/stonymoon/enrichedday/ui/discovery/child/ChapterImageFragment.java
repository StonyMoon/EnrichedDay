package com.meo.stonymoon.enrichedday.ui.discovery.child;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.view.PinchImageView;


public class ChapterImageFragment extends Fragment {
    private String url;
    private PinchImageView imageView;

    public ChapterImageFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            url = getArguments().getString("url");

        }
        View view = inflater.inflate(R.layout.fragment_chapter_image, container, false);
        imageView = (PinchImageView) view.findViewById(R.id.chapter_image);
        Glide.with(this).load(url).into(imageView);
        return view;

    }

}
