package cn.com.hwtc.cheryac.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.com.hwtc.cheryac.R;
import cn.com.hwtc.cheryac.manager.StatusManager;

/**
 * user: Created by jid on 2019/9/2.
 * email: jid@hwtc.com.cn
 * description:
 */
public class AirPurificationActivity extends BaseActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener, StatusManager.OnUpdateAirPurificationCallback {
    private static final String TAG = AirPurificationActivity.class.getSimpleName();
    private ImageView ivHome;
    private LinearLayout llManualPurification;

    @Override
    protected int createLayout(Bundle saveInstanceState) {
        return R.layout.activity_air_purification;
    }

    @Override
    protected void initView() {
        ivHome = findViewById(R.id.iv_home);
        llManualPurification = findViewById(R.id.ll_manual_purification);
    }

    @Override
    protected void initEvent() {
        ivHome.setOnClickListener(this);
        llManualPurification.setOnClickListener(this);
        mStatusManager.setOnUpdateAirPurificationCallback(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home:
                mStatusManager.onBack();
                break;
            case R.id.ll_manual_purification:
                //点击手动净化按键后,CAN报文发送自动净化开启(界面自动净化不显示开启);进度条从0%快速滑动至k(已净化进度百分比)值位置,
                //直到到达100%后,CAN报文发送自动净化关闭
                mStatusManager.setAutoPurificationSwitch(1);
                mStatusManager.sendInfo(mContext);
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_switch_automatic_purification:
                Log.d(TAG, "onCheckedChanged cb_switch_automatic_purification -> " + b);
                mStatusManager.setAutoPurificationSwitch(b ? 1 : 0);
                llManualPurification.setEnabled(b);
                mStatusManager.sendInfo(mContext);
                break;
            default:
                break;
        }
    }

    @Override
    public void updatePurificationPercent(int percent) {
        Log.d(TAG, "updatePurificationPercent -> " + percent);
        if (percent == 100) {
            mStatusManager.setAutoPurificationSwitch(0);
            mStatusManager.sendInfo(mContext);
        }
    }
}
