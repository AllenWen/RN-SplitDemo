package com.splitdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.facebook.react.ReactActivityDelegate;

import javax.annotation.Nullable;

public class BaseReactActivityDelegate extends ReactActivityDelegate {

    private Bundle bundle;

    public BaseReactActivityDelegate(Activity activity, @Nullable String mainComponentName) {
        super(activity, mainComponentName);
    }

    public BaseReactActivityDelegate(FragmentActivity fragmentActivity, @Nullable String mainComponentName) {
        super(fragmentActivity, mainComponentName);
    }

    @Nullable
    @Override
    protected Bundle getLaunchOptions() {
        return bundle;
    }

    public void setLaunchOptions(Bundle bundle) {
        this.bundle = bundle;
    }
}
