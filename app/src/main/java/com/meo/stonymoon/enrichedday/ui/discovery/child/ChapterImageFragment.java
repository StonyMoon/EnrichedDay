package com.meo.stonymoon.enrichedday.ui.discovery.child;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.view.PinchImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;


public class ChapterImageFragment extends Fragment {
    private String url;
    private PinchImageView imageView;
    private AVLoadingIndicatorView loadingView;
    public ChapterImageFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            url = getArguments().getString("url");

        }
        View view = inflater.inflate(R.layout.fragment_chapter_image, container, false);
        loadingView = (AVLoadingIndicatorView) view.findViewById(R.id.comic_loading_view);
        imageView = (PinchImageView) view.findViewById(R.id.chapter_image);
        Picasso.with(this.getContext()).load(url).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                loadingView.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                loadingView.setVisibility(View.GONE);
            }
        });
        return view;

    }

}
