package com.meo.stonymoon.enrichedday.util;

import android.view.View;


public class KeyView {
    private int key;
    private View view;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    KeyView(int key, View view) {
        this.key = key;
        this.view = view;

    }


}
