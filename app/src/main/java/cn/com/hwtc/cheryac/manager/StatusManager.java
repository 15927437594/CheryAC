package cn.com.hwtc.cheryac.manager;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

/**
 * user: Created by jid on 2019/9/2.
 * email: jid@hwtc.com.cn
 * description:状态管理模块
 */
public class StatusManager {
    private static final String TAG = StatusManager.class.getSimpleName();
    private static volatile StatusManager sInstance = null;
    private DriveMode mDriveMode = DriveMode.MODE_WELCOME;

    private StatusManager() {
    }

    public static StatusManager getInstance() {
        if (sInstance == null) {
            synchronized (StatusManager.class) {
                if (sInstance == null) {
                    sInstance = new StatusManager();
                }
            }
        }
        return sInstance;
    }

    public void onBack() {
        new Thread() {
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                    Log.d(TAG, "KEYCODE_BACK");
                } catch (Exception e) {
                    Log.e(TAG, "Exception when onBack -> " + e.toString());
                }
            }
        }.start();
    }

    public void onHome(Context context) {
        //方式一模拟home键
//        new Thread() {
//            public void run() {
//                try {
//                    Instrumentation inst = new Instrumentation();
//                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_HOME);
//                    Log.d(TAG, "KEYCODE_HOME");
//                } catch (Exception e) {
//                    Log.e(TAG, "Exception when onHome -> " + e.toString());
//                }
//            }
//        }.start();

        //方式二模拟home键
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(i);
    }

    public void startActivity(Context context, String packageName, String className) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClassName(packageName, className);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private enum DriveMode {
        MODE_WELCOME, MODE_LONG_DISTANCE, MODE_RELAX
    }

    public DriveMode getDriveMode() {
        return mDriveMode;
    }

    public void setDriveMode(DriveMode mode) {
        mDriveMode = mode;
    }
}
