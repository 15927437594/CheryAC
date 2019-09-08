package cn.com.hwtc.cheryac.activity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    }

    @Override
    protected void initEvent() {
        ivHome.setOnClickListener(this);
        cbSwitchAutomaticDefog.setOnCheckedChangeListener(this);
        mStatusManager.setOnUpdateAutoDefogCallback(this);
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
    public void updateCurrentHumidity(int humidity) {
        Log.d(TAG, "updateCurrentHumidity -> " + humidity);
        if (mStatusManager.getAutoMistSwitch() == 1) {
            if (humidity < 45) {
                rlDefogOk.setVisibility(View.VISIBLE);
            } else if (humidity > 75) {
                // TODO: 2019/9/8 开始识别是否除雾已完成
            }
        }
    }

    @Override
    public void updateFogProbability(int fogProbability) {
        Log.d(TAG, "updateFogProbability -> " + fogProbability);
        tvFogProbability.setText(String.valueOf(fogProbability));
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_switch_automatic_defog:
                mStatusManager.setAutoMistSwitch(b ? 1 : 0);
                if (!b) {
                    rlDefogOk.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                break;
        }
    }
}
