package com.splitdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.ReactContext;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ReactInstanceManager manager = getReactNativeHost().getReactInstanceManager();
        createContext(manager);
        findViewById(R.id.load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreateContext(manager);
            }
        });

        findViewById(R.id.sample_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), SampleAActivity.class));
            }
        });

        findViewById(R.id.sample_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), SampleBActivity.class));
            }
        });
    }

    /**
     * 此方法只能在 {@link #createContext} 调用后才能调用
     *
     * @param manager
     */
    private void recreateContext(final ReactInstanceManager manager) {
        Utils.recreateReactContextInBackground(manager);
        manager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
            @Override
            public void onReactContextInitialized(ReactContext context) {
                Utils.moveBeforeResume(manager);
                Utils.getsLoadedScript().clear();//重建context时，需要清除set，以便下一次加载bundle
            }
        });
    }

    /**
     * 第一次创建context时，需要调用此方法base.bundle.js
     * 创建完reactContext 这里要使用beforeResume 不要使用resume,
     * 直接使用resume，会导致{@link ReactContext#onHostResume }提前调用
     * 后面获取不到{@link ReactContext#mCurrentActivity }
     *
     * @param manager
     */
    private void createContext(final ReactInstanceManager manager) {
        Utils.createReactContextInBackground(manager);
        manager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
            @Override
            public void onReactContextInitialized(ReactContext context) {
                Utils.moveBeforeResume(manager);
            }
        });
    }

    private ReactNativeHost getReactNativeHost() {
        return ((ReactApplication) getApplication()).getReactNativeHost();
    }

}
