package com.meo.stonymoon.enrichedday.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A on 2017/7/20.
 */

public class SliderBean {
    public List<Data> data = new ArrayList<>();

    public class Data {
        public String pic;
        public String url;
    }

}
