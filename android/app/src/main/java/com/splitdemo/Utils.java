package com.splitdemo;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.CatalystInstanceImpl;
import com.facebook.react.bridge.ReactContext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class Utils {

    /**
     * 已加载的bundle集合
     */
    private static Set<String> sLoadedScript = new HashSet<>();

    static void createReactContextInBackground(ReactInstanceManager manager) {
        try {
            Method method = ReactInstanceManager.class.getDeclaredMethod("createReactContextInBackground");
            method.setAccessible(true);
            method.invoke(manager);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    static void recreateReactContextInBackground(ReactInstanceManager manager) {
        try {
            Method method = ReactInstanceManager.class.getDeclaredMethod("recreateReactContextInBackground");
            method.setAccessible(true);
            method.invoke(manager);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    static void moveBeforeResume(ReactInstanceManager manager) {
        try {
            Method method = ReactInstanceManager.class.getDeclaredMethod("moveToBeforeResumeLifecycleState");
            method.setAccessible(true);
            method.invoke(manager);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public static CatalystInstance getCatalystInstance(ReactNativeHost host) {
        ReactInstanceManager manager = host.getReactInstanceManager();
        if (manager == null) {
            return null;
        }

        ReactContext context = manager.getCurrentReactContext();
        if (context == null) {
            return null;
        }
        return context.getCatalystInstance();
    }

    @WorkerThread
    public static void loadScriptFromAsset(Context context,
                                           CatalystInstance instance,
                                           String assetName) {
        if (sLoadedScript.contains(assetName)) {//判断bundle是否加载过
            return;
        }
        try {
            String source = "assets://" + assetName;
            Method method = CatalystInstanceImpl.class.getDeclaredMethod("loadScriptFromAssets",
                    AssetManager.class,
                    String.class,
                    boolean.class);
            method.setAccessible(true);
            method.invoke(instance, context.getAssets(), source, false);
            //记录当前bundle，表示已经加载过
            sLoadedScript.add(assetName);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void setMainComponent(ReactActivityDelegate delegate, String componentName) {
        try {
            Field field = ReactActivityDelegate.class.getDeclaredField("mMainComponentName");
            field.setAccessible(true);
            field.set(delegate, componentName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static Set<String> getsLoadedScript() {
        return sLoadedScript;
    }

}
