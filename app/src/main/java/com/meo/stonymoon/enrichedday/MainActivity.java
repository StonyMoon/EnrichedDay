package com.meo.stonymoon.enrichedday;

import com.meo.stonymoon.enrichedday.ui.discovery.*;
import com.meo.stonymoon.enrichedday.ui.friend.FriendFragment;
import com.meo.stonymoon.enrichedday.ui.music.*;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
        ViewPager.OnPageChangeListener {
    private DrawerLayout drawer;
    private ViewPager viewPager;
    private ImageView musicImage;
    private ImageView discoveryImage;
    private ImageView friendImage;
    private MusicFragment musicFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView itemImage = (ImageView) findViewById(R.id.main_title_item);
        musicImage = (ImageView) findViewById(R.id.bar_music);
        discoveryImage = (ImageView) findViewById(R.id.bar_discovery);
        friendImage = (ImageView) findViewById(R.id.bar_friend);
        discoveryImage.setSelected(true);
        musicImage.setOnClickListener(this);
        discoveryImage.setOnClickListener(this);
        friendImage.setOnClickListener(this);


        viewPager = (ViewPager) findViewById(R.id.main_viewpager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        /*
         *注意这里是supportActionBar
         */
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        itemImage.setOnClickListener(this);
        initFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_title_item:
                drawer.openDrawer(Gravity.LEFT);
                break;
            case R.id.bar_music:
                if (!musicImage.isSelected()) {
                    discoveryImage.setSelected(false);
                    friendImage.setSelected(false);
                    musicImage.setSelected(true);
                    viewPager.setCurrentItem(0);

                }
                break;
            case R.id.bar_discovery:
                if (!discoveryImage.isSelected()) {
                    discoveryImage.setSelected(true);
                    friendImage.setSelected(false);
                    musicImage.setSelected(false);
                    viewPager.setCurrentItem(1);

                }
                break;

            case R.id.bar_friend:
                if (!friendImage.isSelected()) {
                    discoveryImage.setSelected(false);
                    friendImage.setSelected(true);
                    musicImage.setSelected(false);
                    viewPager.setCurrentItem(2);

                }
                break;

        }

    }

    private void initFragment() {
        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        musicFragment = new MusicFragment();
        musicFragment.stopShakeListener();
        //关闭shake listener
        DiscoveryFragment discoveryFragment = new DiscoveryFragment();
        mFragmentList.add(musicFragment);
        mFragmentList.add(discoveryFragment);
        mFragmentList.add(new FriendFragment());
        // 注意使用的是：getSupportFragmentManager
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        viewPager.setAdapter(adapter);
        // 设置ViewPager最大缓存的页面个数(cpu消耗少)
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(1);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                musicFragment.startShakeListener();
                discoveryImage.setSelected(false);
                friendImage.setSelected(false);
                musicImage.setSelected(true);
                break;
            case 1:
                discoveryImage.setSelected(true);
                friendImage.setSelected(false);
                musicImage.setSelected(false);
                musicFragment.stopShakeListener();
                break;
            case 2:
                discoveryImage.setSelected(false);
                friendImage.setSelected(true);
                musicImage.setSelected(false);
                musicFragment.stopShakeListener();
                break;

        }


    }
}
