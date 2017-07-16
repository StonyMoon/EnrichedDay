package com.meo.stonymoon.enrichedday.ui.music;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.util.ShakeListener;

import static android.content.Context.SENSOR_SERVICE;

public class MusicFragment extends Fragment {
    private ShakeListener mShakeListener = null;
    private ImageView loadImage;
    private boolean flag = false;
    public MusicFragment() {
        // Required empty public constructor
    }

    /*
     * 拿到传感器
     *
     */
    @Override
    public void onStart() {
        super.onStart();
        mShakeListener = new ShakeListener(getContext());
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            public void onShake() {
                //mShakeListener.stop();

                if (flag) {
                    flag = false;
                    loadImage.setImageResource(R.drawable.load_down);

                } else {
                    flag = true;
                    loadImage.setImageResource(R.drawable.load_up);

                }


            }
        });
    }

    /*
     * 注销传感器
     */
    @Override
    public void onPause() {
        // 务必要在pause中注销 mSensorManager
        // 否则会造成界面退出后摇一摇依旧生效的bug
        if (mShakeListener != null) {
            mShakeListener.stop();
        }
        super.onPause();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        loadImage = (ImageView) view.findViewById(R.id.music_load);


        return view;
    }

    public void startShakeListener() {
        if (mShakeListener != null)
            mShakeListener.start();

    }

    public void stopShakeListener() {
        if (mShakeListener != null) {
            mShakeListener.stop();
        }

    }


}
