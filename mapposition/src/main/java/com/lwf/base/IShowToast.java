package com.lwf.base;

import android.widget.Toast;

/**
 * 可以共用弹出Toast的Activity和Fragment
 *
 * @since 16/7/24
 */
public interface IShowToast {

    /**
     * Toast封装,防止短时间内多次调用toast导致不停弹出的问题
     *
     * @param msgId 要显示的字符串的资源id
     * @see Toast
     */
    void showMsg(int msgId);

    /**
     * Toast封装,防止短时间内多次调用toast导致不停弹出的问题
     *
     * @param msgId    要显示的字符串的资源id
     * @param duration Toast.LENGTH_SHORT or Toast.LENGTH_LONG
     * @see Toast
     */
    void showMsg(int msgId, int duration);

    /**
     * Toast封装,防止短时间内多次调用toast导致不停弹出的问题
     *
     * @param msgStr 要显示的字符串
     * @see Toast
     */
    void showMsg(CharSequence msgStr);

    /**
     * Toast封装,防止短时间内多次调用toast导致不停弹出的问题
     *
     * @param msgStr   要显示的字符串
     * @param duration Toast.LENGTH_SHORT or Toast.LENGTH_LONG
     * @see Toast
     */
    void showMsg(CharSequence msgStr, int duration);
}
