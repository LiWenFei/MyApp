package com.lwf.util;

import java.util.HashMap;

/**
 * Created by liwenfei on 16/8/4.
 */
public class MyRequestPara extends HashMap<String, String> {

    private MyRequestPara() {
    }

    public static MyRequestPara create() {
        return new MyRequestPara();
    }

    public MyRequestPara with(String key, String val) {
        put(key, val);
        return this;
    }

    public MyRequestPara with(String key, int val) {
        put(key, val + "");
        return this;
    }

    public MyRequestPara end() {
        return this;
    }

    public MyRequestPara withIfNotNull(String key, String val) {
        if (val != null)
            with(key, val);
        return this;
    }
}
