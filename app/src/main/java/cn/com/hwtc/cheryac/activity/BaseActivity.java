package cn.com.hwtc.cheryac.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import cn.com.hwtc.cheryac.manager.StatusManager;

/**
 * user: Created by jid on 2019/9/2.
 * email: jid@hwtc.com.cn
 * description:
 */
public abstract class BaseActivity extends Activity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    public static final String HW_STATUSBAR_MODE_EXTRA = "hw_statusbar_mode_extra";
    public static final String HW_STATUSBAR_SHOW_OR_HIDE_ACTION = "android.hw.statusbar.SHOW_OR_HIDE";
    protected Context mContext = null;
    protected Handler mHandler = null;
    protected StatusManager mStatusManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        int layout = createLayout(savedInstanceState);
        if (layout != 0) {
            setContentView(layout);
            if (mContext == null) {
                mContext = getApplicationContext();
            }
            if (mHandler == null) {
                mHandler = new Handler();
            }
            mStatusManager = StatusManager.getInstance();
            initView();
            initEvent();
            setSystemBarTransparent();
            showNavigationBar(false);
            setNavigationBarStatue(5);
            //6和8一起使用才能生效
            setNavigationBarStatue(6);
            setNavigationBarStatue(8);
        } else {
            throw new IllegalStateException("createLayout() returned 0, If you don't want to use createLayout(), but implement your " +
                    "own view,you have to override setContentView();");
        }
    }

    protected abstract int createLayout(Bundle saveInstanceState);

    protected abstract void initView();

    protected abstract void initEvent();

    private void setSystemBarTransparent() {
        Window window = getWindow();
        if (window != null) {
            //透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    protected void showNavigationBar(boolean show) {
        WindowManager.LayoutParams attr = getWindow().getAttributes();
        if (show) {
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            attr.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(attr);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * 设置导航栏的状态
     *
     * @param status //3:显示home键 4:显示back键 5:隐藏home键或者back键 6:隐藏状态栏 7:显示状态栏 8:屏蔽下拉功能 9:恢复下拉功能
     */
    protected void setNavigationBarStatue(int status) {
        Intent intent = new Intent(HW_STATUSBAR_SHOW_OR_HIDE_ACTION);
        intent.putExtra(HW_STATUSBAR_MODE_EXTRA, status);
        sendBroadcast(intent);
    }

}
