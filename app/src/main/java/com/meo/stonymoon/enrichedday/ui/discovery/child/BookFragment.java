package com.meo.stonymoon.enrichedday.ui.discovery.child;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.adapter.BookAdapter;
import com.meo.stonymoon.enrichedday.bean.BookBean;
import com.meo.stonymoon.enrichedday.util.HandleResponseUtil;
import com.meo.stonymoon.enrichedday.util.HttpUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BookFragment extends Fragment {
    private List<BookBean.Book> bookList = new ArrayList<>();
    private BookAdapter adapter = new BookAdapter(bookList);
    private XRecyclerView recyclerView;
    private int start = 1;

    public BookFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        recyclerView = (XRecyclerView) view.findViewById(R.id.book_recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                bookList.clear();
                start = 1;
                initPicture(start);

            }

            @Override
            public void onLoadMore() {
                start += 20;
                initPicture(start);
            }
        });


        initPicture(start);

        return view;
    }

    private void initPicture(int start) {
        HttpUtil.sendOkHttpRequest("https://api.douban.com/v2/book/search?tag=轻小说&count=20&start=" + start, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonBody = response.body().string();

                BookBean book = HandleResponseUtil.handleBookResponse(jsonBody);

                bookList.addAll(book.books);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        recyclerView.loadMoreComplete();
                        recyclerView.refreshComplete();
                    }
                });

            }
        });


    }


}