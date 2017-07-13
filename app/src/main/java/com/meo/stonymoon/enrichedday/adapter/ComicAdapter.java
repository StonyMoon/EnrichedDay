package com.meo.stonymoon.enrichedday.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.bean.ComicBean;
import com.meo.stonymoon.enrichedday.ui.discovery.child.ComicDetailActivity;

import java.util.List;

/**
 * Created by A on 2017/7/11.
 */

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ViewHolder> {
    private List<ComicBean.Comic> comicList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.bangumi_image);
            titleView = (TextView) view.findViewById(R.id.bangumi_title);
        }

    }

    public ComicAdapter(List<ComicBean.Comic> s) {
        comicList = s;
    }


    public ComicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();

        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bangumi, parent, false);
        final ComicAdapter.ViewHolder holder = new ComicAdapter.ViewHolder(view);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ComicBean.Comic b = comicList.get(position - 1);
                //Xrecycler刷新部分占了一个位置
                Intent intent = new Intent(mContext, ComicDetailActivity.class);
                intent.putExtra("title", b.name);
                intent.putExtra("evaluate", b.description);
                intent.putExtra("coverUrl", b.cover);
                intent.putExtra("id", b.id);


                mContext.startActivity(intent);

            }
        });


        return holder;

    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    public void onBindViewHolder(ComicAdapter.ViewHolder holder, int position) {
        ComicBean.Comic comic = comicList.get(position);
        Glide.with(mContext)
                .load(comic.cover)
                .into(holder.imageView);
        holder.titleView.setText(comic.name);

    }


}
