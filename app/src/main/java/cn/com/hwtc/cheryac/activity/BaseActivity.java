package cn.com.hwtc.cheryac.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

/**
 * user: Created by jid on 2019/9/2.
 * email: jid@hwtc.com.cn
 * description:
 */
public class BaseActivity extends Activity {
    public static final String HW_STATUSBAR_MODE_EXTRA = "hw_statusbar_mode_extra";
    public static final String HW_STATUSBAR_SHOW_OR_HIDE_ACTION = "android.hw.statusbar.SHOW_OR_HIDE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemBarTransparent();
        showNavigationBar(false);
        setNavigationBarStatue(5);
    }

    protected void initView() {
    }

    protected void initEvent() {
    }

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
        if (show) {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * 设置导航栏的状态
     *
     * @param status 3:HOME键 4:BACK键 5:隐藏
     */
    protected void setNavigationBarStatue(int status) {
        Intent intent = new Intent(HW_STATUSBAR_SHOW_OR_HIDE_ACTION);
        intent.putExtra(HW_STATUSBAR_MODE_EXTRA, status);
        sendBroadcast(intent);
    }
}
