package com.lwf.net;


import com.lwf.mapposition.BuildConfig;

/**
 * 接口地址
 *
 * @author liuweiping
 * @since 16/7/26
 */
public class ApiUrls {
    public static final String API_HOST = BuildConfig.API_HOST;

    /**
     * 自更新
     */
    // 更多
    public static final String INTRO_PROTOCOL = API_HOST + "/uapp/intro/protocol";
    public static final String INTRO_QA = API_HOST + "/uapp/intro/problem_list";
    public static final String INTRO_ABOUT = API_HOST + "/uapp/intro/about";
}
