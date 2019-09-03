package cn.com.hwtc.cheryac.application;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import cn.com.hwtc.cheryac.service.CheryControlService;

/**
 * user: Created by jid on 2019/9/2.
 * email: jid@hwtc.com.cn
 * description:
 */
public class LauncherApplication extends Application {
    private static final String TAG = LauncherApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
        startService(new Intent(this, CheryControlService.class));
    }
}
