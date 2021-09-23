package com.walker.protect.helper;

import android.app.Activity;
import android.app.Application;

import com.walker.protect.library.EasyProtectorLib;

import java.lang.reflect.Method;

/**
 * Author  : walker
 * Date    : 2021/9/18  1:43 下午
 * Email   : feitianwumu@163.com
 * Summary : 处理器
 */
public class ProtectTransact {

    private Application mApplication;

    public ProtectTransact(Application application) {
        mApplication = application;
    }

    public void transact(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Protector.class)) {
                check(activity, method);
                break;
            }
        }
    }

    public void check() {
        boolean isRoot = EasyProtectorLib.checkIsRoot();
        boolean isXposed = EasyProtectorLib.checkIsXposedExist();
        boolean isRunningInEmulator = EasyProtectorLib.checkIsRunningInEmulator(mApplication.getApplicationContext(), null);

        if (isRoot || isXposed || isRunningInEmulator) {
            exit();
        }
    }

    private void check(Activity activity, Method method) {
        Protector protector = method.getAnnotation(Protector.class);
        if (protector != null) {
            int resultType = protector.resultType();

            boolean isRoot = EasyProtectorLib.checkIsRoot();
            boolean isXposed = EasyProtectorLib.checkIsXposedExist();
            boolean isRunningInEmulator = EasyProtectorLib.checkIsRunningInEmulator(mApplication.getApplicationContext(), null);
            try {
                String msg = "isRoot:" + isRoot + "   isXposed:" + isXposed + "   isRunningInEmulator:" + isRunningInEmulator;
                method.setAccessible(true);
                method.invoke(activity, msg);
            } catch (IllegalArgumentException e) {
                try {
                    method.invoke(activity, new Object[]{});
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (isRoot || isXposed || isRunningInEmulator) {
                if (0 < resultType) {
                    exit();
                }
            }
        }
    }

    private void exit() {
        System.exit(0);
    }
}
