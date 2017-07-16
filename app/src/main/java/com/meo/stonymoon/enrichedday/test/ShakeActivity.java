package com.meo.stonymoon.enrichedday.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.test.ShakeListener;

public class ShakeActivity extends Activity {

    ShakeListener mShakeListener = null;
    Vibrator mVibrator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        // drawerSet ();//设置 drawer监听 切换 按钮的方向

        //获得振动器服务
        mVibrator = (Vibrator) getApplication().getSystemService(VIBRATOR_SERVICE);
        //实例化加速度传感器检测类
        mShakeListener = new ShakeListener(ShakeActivity.this);

        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {

            public void onShake() {
                mShakeListener.stop();
                startVibrato(); // 开始 震动
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mVibrator.cancel();
                        mShakeListener.start();
                    }
                }, 2000);
            }
        });
    }

    // 定义震动
    public void startVibrato() {
        mVibrator.vibrate(new long[]{500, 200, 500, 200}, -1);
        // 第一个｛｝里面是节奏数组，
        // 第二个参数是重复次数，-1为不重复，非-1则从pattern的指定下标开始重复
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mShakeListener != null) {
            mShakeListener.stop();
        }
    }
}