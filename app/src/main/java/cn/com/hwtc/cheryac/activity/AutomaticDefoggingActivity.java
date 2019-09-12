package cn.com.hwtc.cheryac.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.hwtc.cheryac.R;
import cn.com.hwtc.cheryac.manager.StatusManager;

/**
 * user: Created by jid on 2019/9/2.
 * email: jid@hwtc.com.cn
 * description:
 */
public class AutomaticDefoggingActivity extends BaseActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener, StatusManager.OnUpdateAutoDefogCallback {
    private static final String TAG = AutomaticDefoggingActivity.class.getSimpleName();
    private ImageView ivHome;
    private CheckBox cbSwitchAutomaticDefog;
    private TextView tvFogProbability;
    private RelativeLayout rlDefogOk;
    private TextView tvMistTip;

    @Override
    protected int createLayout(Bundle saveInstanceState) {
        return R.layout.activity_automatic_defogging;
    }

    @Override
    protected void initView() {
        ivHome = findViewById(R.id.iv_home);
        cbSwitchAutomaticDefog = findViewById(R.id.cb_switch_automatic_defog);
        tvFogProbability = findViewById(R.id.tv_fog_probability);
        rlDefogOk = findViewById(R.id.rl_defog_ok);
        tvMistTip = findViewById(R.id.tv_mist_tip);
    }

    @Override
    protected void initEvent() {
        ivHome.setOnClickListener(this);
        cbSwitchAutomaticDefog.setOnCheckedChangeListener(this);
        mStatusManager.setOnUpdateAutoDefogCallback(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        cbSwitchAutomaticDefog.setChecked(mStatusManager.getAutoMistSwitch() == 1);
        updateFogProbability(mStatusManager.getFogProbability());
        updateCurrentHumidity(mStatusManager.getHumidUpState(), mStatusManager.getHumidSensor());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home:
                mStatusManager.onBack();
                break;
            default:
                break;
        }
    }

    @Override
    public void updateCurrentHumidity(boolean humidUpState, int humidity) {
        Log.d(TAG, "updateCurrentHumidity -> " + humidity);
        // TODO: 2019/9/10 如果是从75以上降低到45以下,则显示除雾已完成,如果再从45以下上升到60以上,则隐藏除雾已完成
        //开启自动除雾状态下
        if (mStatusManager.getAutoMistSwitch() == 1) {
            if (!humidUpState & humidity < 45) {
                if (mStatusManager.getDefogCompleted()) {
                    rlDefogOk.setVisibility(View.VISIBLE);
                    tvMistTip.setVisibility(View.INVISIBLE);
                }
            } else if (humidity > 75) {
                // TODO: 2019/9/8 开始识别是否除雾已完成
                mStatusManager.setDefogCompleted(true);
                tvMistTip.setVisibility(View.VISIBLE);
            } else if (humidUpState & humidity > 60) {
                mStatusManager.setDefogCompleted(false);
                rlDefogOk.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void updateFogProbability(int fogProbability) {
        Log.d(TAG, "updateFogProbability -> " + fogProbability);
        tvFogProbability.setText(String.valueOf(fogProbability) + "%");
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_switch_automatic_defog:
                mStatusManager.setAutoMistSwitch(b ? 1 : 0);
                if (!b) {
                    rlDefogOk.setVisibility(View.INVISIBLE);
                }
                mStatusManager.sendInfo(mContext);
                break;
            default:
                break;
        }
    }
}
