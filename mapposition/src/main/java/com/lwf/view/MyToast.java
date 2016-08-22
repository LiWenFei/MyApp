package com.lwf.view;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 封装了Toast,方便在runonuithread方法中调用;
 *
 * @since 2015年5月11日
 */
public class MyToast implements Runnable {
    private Context context;
    public int duration;

    public CharSequence msgStr;
    private Toast toast;

    public MyToast(Context context, CharSequence msgStr, int duration) {
        this.context = context;
        this.msgStr = msgStr;
        this.duration = duration;
    }

    @Override
    public void run() {
        if (toast == null) {
            toast = Toast.makeText(context, msgStr, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(msgStr);
            toast.setDuration(duration);
        }
        toast.show();
    }
}
