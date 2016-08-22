package com.lwf.base;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * 异步任务类
 */
public abstract class MyTask implements Runnable {
    private static final MoreExecutor MORE_EXECUTOR = new MoreExecutor();
    private static final int CORE_POOL_SIZE = 5;

    /**
     * 异步任务类
     */
    public MyTask() {
    }

    /**
     * 外部接口，处理请求
     *
     * @param run         Runnable
     * @param isLTimeOper 是否是耗时操作.
     */
    public static void runInBackground(Runnable run, boolean isLTimeOper) {
        if (isLTimeOper) {
            MORE_EXECUTOR.execute(run);
        } else {
            AsyncTaskAssistant.executeOnThreadPool(run);
        }

    }

    /**
     * 并发执行多任务。
     */
    private static class MoreExecutor implements Executor {
        public int runCount = 0;

        /**
         * @param add 同步执行，维护runCount字段
         */
        private synchronized void editRuncount(boolean add) {
            if (add) {
                runCount++;
            } else {
                runCount--;
            }
        }

        /**
         * 占用线程池CORE_POOL_SIZE-1个,如果满了立即创建thread执行;
         */
        public synchronized void execute(@NonNull final Runnable r) {
            if (runCount >= (CORE_POOL_SIZE - 1)) {
                new Thread(r).start();
            } else {
                editRuncount(true);
                executeTask(r);
                editRuncount(false);
            }

        }

        /**
         * @param runtask Runnable
         */
        protected synchronized void executeTask(Runnable runtask) {
            AsyncTaskAssistant.executeOnThreadPool(runtask);
        }
    }

}
