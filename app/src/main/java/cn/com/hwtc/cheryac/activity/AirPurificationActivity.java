package cn.com.hwtc.cheryac.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.com.hwtc.cheryac.R;

/**
 * user: Created by jid on 2019/9/2.
 * email: jid@hwtc.com.cn
 * description:
 */
public class AirPurificationActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home:
                mStatusManager.onBack();
                break;
            case R.id.ll_manual_purification:
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
                if (b) {
                    mStatusManager.setAutoPurificationSwitch(1);
                } else {
                    mStatusManager.setAutoPurificationSwitch(0);
                }
                llManualPurification.setEnabled(b);
                break;
            default:
                break;
        }
    }
}
