package com.meo.stonymoon.enrichedday.ui.friend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.adapter.PixivAdapter;
import com.meo.stonymoon.enrichedday.bean.PixivBean;
import com.meo.stonymoon.enrichedday.util.HandleResponseUtil;
import com.meo.stonymoon.enrichedday.util.HttpUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FriendFragment extends Fragment {
    private XRecyclerView pixivRecyclerView;
    private List<PixivBean.PictureBean> mList = new ArrayList<>();
    private List<PixivBean.PictureBean> allBeanList = new ArrayList<>();
    private PixivAdapter adapter = new PixivAdapter(mList);
    private int itemNum;

    public FriendFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static FriendFragment newInstance(String param1, String param2) {
        FriendFragment fragment = new FriendFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        pixivRecyclerView = (XRecyclerView) view.findViewById(R.id.pixiv_recycler_view);
        pixivRecyclerView.setAdapter(adapter);
        pixivRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        pixivRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pixivRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                int len = allBeanList.size();
                int count = 0;
                for (; itemNum < len && count < 20; itemNum++) {
                    mList.add(allBeanList.get(itemNum));
                    count++;
                }
                pixivRecyclerView.loadMoreComplete();
                adapter.notifyDataSetChanged();
            }
        });

        initPicture();
        return view;

    }

    private void initPicture() {
        HttpUtil.sendOkHttpRequest("http://www.moe123.net/module/pixiv", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonBody = response.body().string();


                PixivBean pb = HandleResponseUtil.handlePixivResponse(jsonBody);
                if (pb == null || pb.pictureBean == null) {
                    return;
                }
                allBeanList.addAll(pb.pictureBean);

                for (itemNum = 0; itemNum < 20; itemNum++) {
                    mList.add(pb.pictureBean.get(itemNum));
                }
                //mList.addAll(pb.pictureBean);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();

                    }
                });

            }
        });


    }


}
