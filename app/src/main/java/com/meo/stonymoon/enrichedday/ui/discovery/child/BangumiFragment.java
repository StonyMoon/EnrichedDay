package com.meo.stonymoon.enrichedday.ui.discovery.child;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.adapter.BangumiAdapter;
import com.meo.stonymoon.enrichedday.bean.BangumiBean;
import com.meo.stonymoon.enrichedday.util.HandleResponseUtil;
import com.meo.stonymoon.enrichedday.util.HttpUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.meo.stonymoon.enrichedday.util.MyItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BangumiFragment extends Fragment {
    private List<BangumiBean.Bangumi> bangumiList = new ArrayList<>();
    private BangumiAdapter adapter = new BangumiAdapter(bangumiList);
    private XRecyclerView recyclerView;
    private int page = 1;

    public BangumiFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bangumi, container, false);
        recyclerView = (XRecyclerView) view.findViewById(R.id.bangumi_recycerview);
        recyclerView.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new MyItemDecoration(2, 5));
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                bangumiList.clear();
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
        HttpUtil.sendOkHttpRequest("https://bangumi.bilibili.com/web_api/season/index_global?page=" + page + "&page_size=20&version=0&is_finish=0&start_year=0&tag_id=&index_type=1&index_sort=0&quarter=0", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonBody = response.body().string();
                BangumiBean bangumiBean = HandleResponseUtil.handleBangumiRecommendResponse(jsonBody);
                bangumiList.addAll(bangumiBean.result.bangumiList);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        recyclerView.refreshComplete();
                        recyclerView.loadMoreComplete();
                    }
                });

            }
        });


    }


}
