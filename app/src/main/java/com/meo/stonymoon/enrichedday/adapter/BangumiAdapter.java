package com.meo.stonymoon.enrichedday.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.bean.BangumiBean;
import com.meo.stonymoon.enrichedday.ui.discovery.child.BangumiDetailActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class BangumiAdapter extends RecyclerView.Adapter<BangumiAdapter.ViewHolder> {

    private List<BangumiBean.Bangumi> bangumiList;
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

    public BangumiAdapter(List<BangumiBean.Bangumi> s) {
        bangumiList = s;
    }


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();

        }
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bangumi, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                BangumiBean.Bangumi b = bangumiList.get(position - 1);
                //Xrecycler刷新部分占了一个位置
                Intent intent = new Intent(mContext, BangumiDetailActivity.class);
                String id = b.url.split("/")[4];
                intent.putExtra("bangumiId", id);
                intent.putExtra("coverUrl", b.cover);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,
                        new Pair<View, String>(view.findViewById(R.id.bangumi_image), "bangumi_share")
                );
                ActivityCompat.startActivity(mContext, intent, options.toBundle());
            }

        });


        return holder;

    }

    @Override
    public int getItemCount() {
        return bangumiList.size();
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        BangumiBean.Bangumi bangumi = bangumiList.get(position);
        Picasso.with(mContext)
                .load(bangumi.cover)
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);
        holder.titleView.setText(bangumi.title);
    }


}
