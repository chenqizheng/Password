package com.yonyou.password;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.yonyou.password.ui.lock.LockActivity;

import java.util.List;

/**
 * Created by Chen on 2017/1/6.
 */

public class MainApplication extends Application {

    private static final String TAG = "MainApplication";
    private int mActivityCount = 0;
    private boolean mIsForegroud = false;
    private boolean mShowingLock = false;

    public boolean isForegroud() {
        return mIsForegroud;
    }

    public MainApplication() {
        super();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                if(activity instanceof LockActivity){
                    mShowingLock = true;
                }
                if (!isForegroud() && !isApplicationBroughtToBackground()) {
                    Intent intent = new Intent(activity, LockActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);
                }
                mActivityCount++;
                Log.i(TAG, "mActivityCount = " + mActivityCount);
                if (mActivityCount > 0) {
                    mIsForegroud = true;
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                if(activity instanceof LockActivity){
                    mShowingLock = false;
                }
                mActivityCount--;
                Log.i(TAG, "mActivityCount = " + mActivityCount);
                if (mActivityCount == 0) {
                    mIsForegroud = false;
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

    }

    private boolean isApplicationBroughtToBackground() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(getPackageName())) {
                return true;
            }
        }
        return false;
    }

}
