package com.abhijai.example.miskaatask.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    private static AppExecutors appExecutorsInstance;
    public static AppExecutors getInstance()
    {
        if (appExecutorsInstance==null){
            appExecutorsInstance = new AppExecutors();
        }
        return appExecutorsInstance;
    }

    private final Executor diskIo = Executors.newSingleThreadExecutor();
    private final Executor mainThreadExecutor = new MainThreadExecutor();

    public Executor getDiskIo(){
        return diskIo;
    }

    public Executor getMainThreadExecutor(){
        return mainThreadExecutor;
    }

    /**
     * This class will help us to post something from background thread to the main thread.
     * It will handle the errors like below :
     *                                      "only the original thread that created a view hierarchy can touch its views"
     */
    private static class MainThreadExecutor implements Executor{
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
