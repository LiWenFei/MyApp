package com.lwf.base;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * activity实例引用管理
 *
 * @Author liuweiping
 * @Date 2014年5月29日 上午11:57:26
 * @Version
 */
public class ActivityInstanceRef {
    private static WeakReference<Activity> curAct;
    private static WeakHashMap<Activity, Object> acList = new WeakHashMap<>();

    /**
     * 设置当前activity
     *
     * @param act
     */
    public static void setCurActivity(Activity act) {
        curAct = new WeakReference<>(act);
        acList.put(act, null);
    }

    /**
     * @return 获取Activity数量
     */
    public static int getActivityCount() {
        return acList == null ? 0 : acList.size();
    }

    public static Activity getCurActivity() {
        if (acList == null || acList.isEmpty())
            return null;
        WeakReference<Activity> wr = curAct;
        if (wr != null) {
            return wr.get();
        }
        return null;
    }

    public static void clearCurActivity(Activity act) {
        acList.remove(act);
//        if (remove != null && TextUtils.equals(remove.getClass().getSimpleName(), curAct.get().getClass().getSimpleName())) {
//
//        }
    }

    /**
     * 退出所有Activity
     */
    public static void destroyAllActivity() {
        if (null != acList && !acList.isEmpty()) {
            Object[] keys = acList.keySet().toArray();
            for (Object obj : keys) {
                Activity act = (Activity)obj;
                if (act != null) {
                    act.finish();
                }
            }
        }
    }
}
