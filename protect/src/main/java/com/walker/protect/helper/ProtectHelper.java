package com.walker.protect.helper;

import android.app.Application;

import com.walker.protect.library.EasyProtectorLib;

/**
 * Author  : walker
 * Date    : 2021/9/18  1:27 下午
 * Email   : feitianwumu@163.com
 * Summary : 守护助手
 */
public class ProtectHelper {
    private volatile static ProtectHelper instance;
    private ProtectActivityLifecycle protectActivityLifecycle;

    /**
     * 初始化 必须在Application中先进行初始化
     *
     * @param application
     */
    public static void init(Application application) {
        if (application == null) {
            return;
        }
        if (instance == null) {
            synchronized (ProtectHelper.class) {
                if (instance == null) {
                    instance = new ProtectHelper(application);
                }
            }
        }

        EasyProtectorLib.checkXposedExistAndDisableIt();
    }

    private ProtectHelper(Application application) {
        protectActivityLifecycle = new ProtectActivityLifecycle(application);
        application.registerActivityLifecycleCallbacks(protectActivityLifecycle);
    }
}
