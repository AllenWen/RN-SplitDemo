package com.splitdemo;

import android.os.AsyncTask;
import android.os.Bundle;

import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactFragmentActivity;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.common.LifecycleState;

public abstract class BaseReactActivity extends ReactFragmentActivity {
    BaseReactActivityDelegate mDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ReactInstanceManager manager = getReactNativeHost().getReactInstanceManager();
        LifecycleState state = manager.getLifecycleState();
        switch (state) {
            case RESUMED:
                loadScriptAsync();
                break;
            case BEFORE_CREATE:
            case BEFORE_RESUME:
            default:
                if (!manager.hasStartedCreatingInitialContext()) {
                    manager.createReactContextInBackground();
                    manager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
                        @Override
                        public void onReactContextInitialized(ReactContext reactContext) {
                            loadScriptAsync();
                        }
                    });
                } else {
                    loadScriptAsync();
                }
                break;
        }
    }

    @Override
    protected ReactActivityDelegate createReactActivityDelegate() {
        mDelegate = new BaseReactActivityDelegate(this, null);
        return mDelegate;
    }

    private void loadScriptAsync() {
        LoadScriptTask task = new LoadScriptTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private final class LoadScriptTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // 获取bridge 加载对应的bundle
            CatalystInstance instance = Utils.getCatalystInstance(getReactNativeHost());
            Utils.loadScriptFromAsset(BaseReactActivity.this,
                    instance,
                    getScriptAssetPath());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //加载完成
            //设置component名
            Utils.setMainComponent(mDelegate, getMainComponentName());
            //传入启动参数，例如routeName，param等
            mDelegate.setLaunchOptions(getInitBundle());
            //加载reactRootView
            loadApp(getMainComponentName());
        }

    }

    protected abstract String getScriptAssetPath();

    protected abstract String getMainComponentName();

    protected abstract Bundle getInitBundle();

}
