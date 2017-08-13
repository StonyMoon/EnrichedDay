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
import com.meo.stonymoon.enrichedday.bean.BookBean;
import com.meo.stonymoon.enrichedday.ui.discovery.child.BookDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private List<BookBean.Book> bookList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView bookText;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.book_cover);
            bookText = (TextView) view.findViewById(R.id.book_title);

        }

    }

    public BookAdapter(List<BookBean.Book> s) {
        bookList = s;
    }


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                BookBean.Book b = bookList.get(position - 1);
                //Xrecycler刷新部分占了一个位置
                Intent intent = new Intent(mContext, BookDetailActivity.class);
                intent.putExtra("imageUrl", b.images.imageUrl);
                intent.putExtra("title", b.title);
                intent.putExtra("average", b.rating.average);
                intent.putExtra("numRaters", b.rating.numRaters);
                intent.putExtra("pubdate", b.pubdate);
                intent.putExtra("authorIntro", b.authorIntro);
                intent.putExtra("summary", b.summary);

                mContext.startActivity(intent);

            }
        });

        return holder;

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        BookBean.Book book = bookList.get(position);
        Picasso.with(mContext)
                .load(book.images.imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);
        holder.bookText.setText(book.title);

    }
}
