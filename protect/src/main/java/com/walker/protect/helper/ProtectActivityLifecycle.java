package com.walker.protect.helper;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import java.util.Random;

/**
 * Author  : walker
 * Date    : 2021/9/18  1:28 下午
 * Email   : feitianwumu@163.com
 * Summary : ProtectActivityLifecycle
 */
public class ProtectActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    private ProtectTransact mTransact;

    private static final int RANDOM_MAX = 20;

    private int randomCount = 0;

    public ProtectActivityLifecycle(Application application) {
        mTransact = new ProtectTransact(application);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        transactCheck(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    private void transactCheck(Activity activity) {
        if (activity == null) {
            return;
        }
        if (activity instanceof IProtector) {
            mTransact.transact(activity);
        } else {
            if (randomCount < RANDOM_MAX) {
                checkRandom();
            }
        }
    }

    private void checkRandom() {
        int value = (int) (1 + Math.random() * (10 - 1 + 1));
        Log.d("Protect", "random value = " + value);
        if (value % 4 == 0) {
            randomCount++;
            mTransact.check();
        }
    }
}
