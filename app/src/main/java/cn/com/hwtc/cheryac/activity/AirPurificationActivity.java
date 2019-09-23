package cn.com.hwtc.cheryac.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    private CheckBox cbSwitchAutomaticPurification;
    private TextView tvAirQuality;
    private TextView tvPollutionDegree;
    private ProgressBar pbPurificationPercent;

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
        cbSwitchAutomaticPurification = findViewById(R.id.cb_switch_automatic_purification);
        tvAirQuality = findViewById(R.id.tv_air_quality);
        tvPollutionDegree = findViewById(R.id.tv_pollution_degree);
        pbPurificationPercent = findViewById(R.id.pb_purification_percent);
    }

    @Override
    protected void initEvent() {
        ivHome.setOnClickListener(this);
        llManualPurification.setOnClickListener(this);
        cbSwitchAutomaticPurification.setOnCheckedChangeListener(this);
        mStatusManager.setOnUpdateAirPurificationCallback(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        tvOutsidePm25.setText(String.valueOf(mStatusManager.getOutsidePm25()));
        tvInsidePm25.setText(String.valueOf(mStatusManager.getInsidePm25()));
        cbSwitchAutomaticPurification.setChecked(mStatusManager.getAutoPurificationSwitch() == 1);
        pbPurificationPercent.setProgress(mStatusManager.getPurificationPercent());
        tvAirQuality.setText(mStatusManager.getPollutionDegree(mContext, mStatusManager.getInsidePm25()));
        tvPollutionDegree.setText(mStatusManager.getPollutionDegree(mContext, mStatusManager.getOutsidePm25()));
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
        if (compoundButton.getId() == R.id.cb_switch_automatic_purification) {
            Log.d(TAG, "onCheckedChanged cb_switch_automatic_purification -> " + b);
            mStatusManager.setAutoPurificationSwitch(b ? 1 : 0);
            if (b) {
                //开启自动净化时默认关闭手动净化
                mStatusManager.setManualPurificationSwitch(0);
            }
            mStatusManager.sendInfo(mContext);
            llManualPurification.setEnabled(!b);
        }
    }

    @Override
    public void updatePurificationPercent(int outsidePm25, int insidePm25, int percent) {
        Log.d(TAG, "updatePurificationPercent -> " + outsidePm25 + " * " + insidePm25 + " * " + percent);
        tvOutsidePm25.setText(String.valueOf(outsidePm25));
        tvInsidePm25.setText(String.valueOf(insidePm25));
        if (percent <= 100) {
            pbPurificationPercent.setProgress(percent);
        }

        if (percent >= 100) {
            mStatusManager.setManualPurificationSwitch(0);
            mStatusManager.sendInfo(mContext);
        }
        // TODO: 2019/9/10 根据车内pm25和车外pm25判断环境标准(优良重度污染中度污染轻度污染)
        tvAirQuality.setText(mStatusManager.getPollutionDegree(mContext, insidePm25));
        tvPollutionDegree.setText(mStatusManager.getPollutionDegree(mContext, outsidePm25));
    }
}
