package com.example.thainara.personalcontrol;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by thainara on 19/10/15.
 */
public class PCApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
