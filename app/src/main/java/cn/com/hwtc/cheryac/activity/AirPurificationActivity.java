package cn.com.hwtc.cheryac.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private TextView tvOutsidePm25;
    private TextView tvInsidePm25;

    @Override
    protected int createLayout(Bundle saveInstanceState) {
        return R.layout.activity_air_purification;
    }

    @Override
    protected void initView() {
        ivHome = findViewById(R.id.iv_home);
        llManualPurification = findViewById(R.id.ll_manual_purification);
        tvOutsidePm25 = findViewById(R.id.tv_outside_pm25);
        tvInsidePm25 = findViewById(R.id.tv_inside_pm25);
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
                mStatusManager.setManualPurificationSwitch(1);
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
    public void updatePurificationPercent(int outsidePm25, int insidePm25, int percent) {
        Log.d(TAG, "updatePurificationPercent -> " + outsidePm25 + " * " + insidePm25 + " * " + percent);
        tvOutsidePm25.setText(String.valueOf(outsidePm25));
        tvInsidePm25.setText(String.valueOf(insidePm25));
        if (percent >= 100) {
            mStatusManager.setManualPurificationSwitch(0);
            mStatusManager.sendInfo(mContext);
        }
    }
}
