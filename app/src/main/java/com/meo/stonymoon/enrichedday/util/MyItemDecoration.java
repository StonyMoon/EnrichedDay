package com.meo.stonymoon.enrichedday.util;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private int leftRight;
    private int topBottom;

    public MyItemDecoration(int leftRight, int topBottom) {
        this.leftRight = leftRight;
        this.topBottom = topBottom;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.right = leftRight;
        outRect.left = leftRight;
        outRect.bottom = topBottom;


    }
}