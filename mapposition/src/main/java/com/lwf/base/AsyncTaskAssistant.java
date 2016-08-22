package com.lwf.base;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SuppressLint("NewApi")
public final class AsyncTaskAssistant {
    //	private static final int CORE_POOL_SIZE = 16;
    //	private static final int MAXIMUM_POOL_SIZE =  Integer.MAX_VALUE;
    //	private static final int KEEP_ALIVE = 5;

    public static final int CORE_POOL_SIZE = 5;
    public static final int MAXIMUM_POOL_SIZE = 128;
    public static final int KEEP_ALIVE = 5;

    private final static BlockingQueue<Runnable> sPoolWorkQueue;
    public final static ThreadPoolExecutor THREAD_POOL_EXECUTOR;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && AsyncTask.THREAD_POOL_EXECUTOR instanceof ThreadPoolExecutor) {
            THREAD_POOL_EXECUTOR = (ThreadPoolExecutor) AsyncTask.THREAD_POOL_EXECUTOR;
            THREAD_POOL_EXECUTOR.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
            sPoolWorkQueue = THREAD_POOL_EXECUTOR.getQueue();
        } else {
            sPoolWorkQueue = new LinkedBlockingQueue<>(10);
            ThreadFactory threadFactory = new ThreadFactory() {
                // private final AtomicInteger mCount = new AtomicInteger(1);
                public Thread newThread(@NonNull Runnable r) {
                    return new Thread(r, "thread_low_version");
                }
            };
            THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, sPoolWorkQueue,
                threadFactory, new ThreadPoolExecutor.DiscardOldestPolicy());
        }
    }

    /**
     * 单例
     */
    private AsyncTaskAssistant() {
    }

    /**
     * 在线程池中，执行对应的runnable
     *
     * @param runnable
     */
    @SuppressLint("NewApi")
    public static void executeOnThreadPool(Runnable runnable) {
        THREAD_POOL_EXECUTOR.execute(runnable);
    }
}
