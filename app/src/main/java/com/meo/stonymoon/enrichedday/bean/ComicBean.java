package com.meo.stonymoon.enrichedday.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A on 2017/7/11.
 */

public class ComicBean {
    public ComicData data;

    public class ComicData {
        public ComicReturnData returnData;

    }

    public class ComicReturnData {
        public List<Comic> comics = new ArrayList<>();

    }

    public class Comic {
        public String cover;
        public String name;
        @SerializedName("comicId")
        public int id;
        public String description;
        public List<String> tags = new ArrayList<>();
        public String author;

    }


}
