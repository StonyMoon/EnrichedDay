package com.meo.stonymoon.enrichedday.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.bean.ComicDetailBean;
import com.meo.stonymoon.enrichedday.ui.discovery.child.ComicChapterActivity;

import java.util.List;


public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {

    private List<ComicDetailBean.ComicChapter> chapterList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        CardView cardView;

        public ViewHolder(View view) {
            super(view);
            titleView = (TextView) view.findViewById(R.id.chapter_item_text);
            cardView = (CardView) view.findViewById(R.id.chapter_item_card);
        }

    }

    public ChapterAdapter(List<ComicDetailBean.ComicChapter> s) {
        chapterList = s;
    }


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();

        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ComicDetailBean.ComicChapter b = chapterList.get(position);
                Intent intent = new Intent(mContext, ComicChapterActivity.class);
                intent.putExtra("chapterId", b.chapterId);
                mContext.startActivity(intent);

            }
        });


        return holder;

    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        ComicDetailBean.ComicChapter chapter = chapterList.get(position);
        holder.titleView.setText(chapter.name);
    }

}