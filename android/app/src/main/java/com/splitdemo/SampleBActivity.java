package com.splitdemo;

import android.os.Bundle;

public class SampleBActivity extends BaseReactActivity {

    @Override
    protected String getScriptAssetPath() {
        return "apptwo.bundle.js";
    }

    @Override
    protected String getMainComponentName() {
        return "AppTwo";
    }

    @Override
    protected Bundle getInitBundle() {
        return null;
    }

}