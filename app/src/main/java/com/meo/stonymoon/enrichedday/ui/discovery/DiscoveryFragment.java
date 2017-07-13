package com.meo.stonymoon.enrichedday.ui.discovery;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meo.stonymoon.enrichedday.MyFragmentPagerAdapter;
import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.ui.discovery.child.BangumiFragment;
import com.meo.stonymoon.enrichedday.ui.discovery.child.BookFragment;
import com.meo.stonymoon.enrichedday.ui.discovery.child.ComicFragment;
import com.meo.stonymoon.enrichedday.ui.discovery.child.RecommendFragment;
import com.meo.stonymoon.enrichedday.ui.discovery.child.TestFragment;

import java.util.ArrayList;
import java.util.List;


public class DiscoveryFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout titleTab;
    private List<String> title = new ArrayList<>();


    public DiscoveryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discovery, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.discovery_viewpager);
        titleTab = (TabLayout) view.findViewById(R.id.discovery_title_tab);
        initFragment();
        return view;

    }

    private void initFragment() {
        //开始设置tab layout
        title.add("每日推荐");
        title.add("动画");
        title.add("漫画");
        title.add("小说");
        titleTab.setTabMode(TabLayout.MODE_FIXED);
        titleTab.addTab(titleTab.newTab().setText(title.get(0)));
        titleTab.addTab(titleTab.newTab().setText(title.get(1)));
        titleTab.addTab(titleTab.newTab().setText(title.get(2)));
        titleTab.addTab(titleTab.newTab().setText(title.get(3)));
        titleTab.setupWithViewPager(viewPager);

        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(new RecommendFragment());
        mFragmentList.add(new BangumiFragment());
        mFragmentList.add(new ComicFragment());
        mFragmentList.add(new BookFragment());
        // 注意使用的是：getSupportFragmentManager
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), mFragmentList, title);

        viewPager.setAdapter(adapter);
        // 设置ViewPager最大缓存的页面个数(cpu消耗少)
        viewPager.setOffscreenPageLimit(4);
        //viewPager.addOnPageChangeListener(this);
        //mBinding.include.ivTitleGank.setSelected(true);
        viewPager.setCurrentItem(0);


    }


}
