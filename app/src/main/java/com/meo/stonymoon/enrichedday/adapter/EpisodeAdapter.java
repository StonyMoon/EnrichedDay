package com.meo.stonymoon.enrichedday.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.bean.BangumiBean;
import com.meo.stonymoon.enrichedday.bean.BangumiDetailBean;
import com.meo.stonymoon.enrichedday.ui.VideoActivity;
import com.meo.stonymoon.enrichedday.ui.discovery.child.BangumiDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by A on 2017/7/31.
 */

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder> {
    private List<BangumiDetailBean.EpisodeBean> episodeList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView;
        CardView cardView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.episode_image);
            titleView = (TextView) view.findViewById(R.id.episode_title);
            cardView = (CardView) view.findViewById(R.id.episode_card_view);
        }

    }

    public EpisodeAdapter(List<BangumiDetailBean.EpisodeBean> s) {
        episodeList = s;
    }


    public EpisodeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bangumi_episode, parent, false);
        final EpisodeAdapter.ViewHolder holder = new EpisodeAdapter.ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                BangumiDetailBean.EpisodeBean b = episodeList.get(position - 2);
                //Xrecycler刷新部分占了一个位置
                Intent intent = new Intent(mContext, VideoActivity.class);
                intent.putExtra("id", b.episodeId);
                mContext.startActivity(intent);
            }

        });

        return holder;

    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    public void onBindViewHolder(EpisodeAdapter.ViewHolder holder, int position) {
        BangumiDetailBean.EpisodeBean bangumi = episodeList.get(position);
        Picasso.with(mContext)
                .load(bangumi.cover)
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);
        holder.titleView.setText(bangumi.title);
    }


}
