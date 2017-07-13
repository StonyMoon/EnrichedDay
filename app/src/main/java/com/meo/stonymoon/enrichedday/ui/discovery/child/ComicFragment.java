package com.meo.stonymoon.enrichedday.ui.discovery.child;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.adapter.ComicAdapter;
import com.meo.stonymoon.enrichedday.bean.ComicBean;
import com.meo.stonymoon.enrichedday.util.HandleResponseUtil;
import com.meo.stonymoon.enrichedday.util.HttpUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ComicFragment extends Fragment {
    private List<ComicBean.Comic> comicList = new ArrayList<>();
    private XRecyclerView recyclerView;
    private ComicAdapter adapter = new ComicAdapter(comicList);
    private int page = 1;

    public ComicFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comic, container, false);
        recyclerView = (XRecyclerView) view.findViewById(R.id.comic_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                comicList.clear();
                page = 1;
                initPicture(1);
            }

            @Override
            public void onLoadMore() {
                page += 1;
                initPicture(page);
            }
        });

        initPicture(page);

        return view;
    }

    private void initPicture(int page) {
//TODO异常处理
        HttpUtil.sendOkHttpRequest("http://app.u17.com/v3/appV3_3/android/phone/list/commonComicList?argValue=9&argName=topic&argCon=0&page=" + page + "&v=3320100&model=Lenovo+P1c72&come_from=Tg002&android_id=4558b957bedb8f36", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonBody = response.body().string();
                ComicBean comicBean = HandleResponseUtil.handleComicBeanRecommendResponse(jsonBody);
                if (comicBean.data.returnData != null) {
                    comicList.addAll(comicBean.data.returnData.comics);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            recyclerView.refreshComplete();
                            recyclerView.loadMoreComplete();
                        }
                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "已经拉到底了", Toast.LENGTH_SHORT).show();
                            recyclerView.refreshComplete();
                            recyclerView.loadMoreComplete();
                        }
                    });


                }


            }
        });


    }


}
