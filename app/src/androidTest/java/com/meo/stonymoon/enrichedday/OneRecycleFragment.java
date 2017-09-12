//package com.meo.stonymoon.enrichedday;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v4.app.Fragment;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//
//import com.liaoinstan.apphomerecyclerview.home.Cheese;
//import com.liaoinstan.apphomerecyclerview.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class OneRecycleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
//    private View rootView = null;//缓存Fragment view
//    private OneRecycleAdapter adapter;
//    private SwipeRefreshLayout lay_fresh;
//
//    public static OneRecycleFragment newInstance() {
//        OneRecycleFragment f = new OneRecycleFragment();
//        return f;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        rootView = inflater.inflate(R.layout.fragment_recycle, container, false);
//        initBase();
//        return rootView;
//    }
//
//    private void initBase() {
//        lay_fresh = (SwipeRefreshLayout) this.rootView.findViewById(R.id.lay_refresh);
//        lay_fresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
//        lay_fresh.setOnRefreshListener(this);
//
//        RecyclerView recyclerView = (RecyclerView) this.rootView.findViewById(R.id.recyclerView);
////        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
//        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 6, GridLayoutManager.VERTICAL, false));
////        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//
//        List<Cheese> results = new ArrayList<Cheese>();
//        results.add(new Cheese("", "my titles develop by random1"));
//        results.add(new Cheese("", "my titles develop by random2"));
//        results.add(new Cheese("", "my titles develop by random3"));
//        results.add(new Cheese("", "my titles develop by moremoremoremoremoremoremoremore random4"));
//        results.add(new Cheese("", "my titles develop by random5"));
//        results.add(new Cheese("", "my titles develop by moremoremoremoremoremoremoremore random6"));
//        results.add(new Cheese("", "my titles develop by random7"));
//        results.add(new Cheese("", "my titles develop by moremoremoremoremoremoremoremore random8"));
//        results.add(new Cheese("", "my titles develop by moremoremoremoremoremoremoremore random9"));
//        results.add(new Cheese("", "my titles develop by random10"));
//        results.add(new Cheese("", "my titles develop by moremoremoremoremoremoremoremore random11"));
//        results.add(new Cheese("", "my titles develop by moremoremoremoremoremoremoremore random12"));
//        results.add(new Cheese("", "my titles develop by random13"));
//        results.add(new Cheese("", "my titles develop by random14"));
//        results.add(new Cheese("", "my titles develop by random15"));
//        recyclerView.setAdapter(adapter = new OneRecycleAdapter(getActivity()));
//    }
//
//    @Override
//    public void onRefresh() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                lay_fresh.setRefreshing(false);
//                adapter.notifyDataSetChanged();
//            }
//        }, 1000);
//    }
//}
