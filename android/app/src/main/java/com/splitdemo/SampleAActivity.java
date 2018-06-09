package com.splitdemo;

import android.os.Bundle;

public class SampleAActivity extends BaseReactActivity {

    @Override
    protected String getScriptAssetPath() {
        return "appone.bundle.js";
    }

    @Override
    protected String getMainComponentName() {
        return "AppOne";
    }

    @Override
    protected Bundle getInitBundle() {
        return null;
    }

}
