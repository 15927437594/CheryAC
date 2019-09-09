package cn.com.hwtc.cheryac.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.hwtc.cheryac.R;
import cn.com.hwtc.cheryac.manager.StatusManager;

/**
 * user: Created by jid on 2019/9/2.
 * email: jid@hwtc.com.cn
 * description:
 */
public class VitalSignsActivity extends BaseActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener, StatusManager.OnUpdateVitalSignsCallback {
    private static final String TAG = VitalSignsActivity.class.getSimpleName();
    private ImageView ivHome;
    private CheckBox cbSwitchAutomaticMonitor;
    private ImageView ivCarbonDioxideConcentration;
    private TextView tvVitalSigns;
    private ImageView ivInformMaster;
    private ImageView ivSendInsidePhoto;
    private ImageView ivOpenInsideCamera;
    private ImageView ivOpenVentilationSystem;

    @Override
    protected int createLayout(Bundle saveInstanceState) {
        return R.layout.activity_vital_signs;
    }

    @Override
    protected void initView() {
        ivHome = findViewById(R.id.iv_home);
        cbSwitchAutomaticMonitor = findViewById(R.id.cb_switch_automatic_monitor);
        ivCarbonDioxideConcentration = findViewById(R.id.iv_carbon_dioxide_concentration);
        tvVitalSigns = findViewById(R.id.tv_vital_signs);
        ivInformMaster = findViewById(R.id.iv_inform_master);
        ivSendInsidePhoto = findViewById(R.id.iv_send_inside_photo);
        ivOpenInsideCamera = findViewById(R.id.iv_open_inside_camera);
        ivOpenVentilationSystem = findViewById(R.id.iv_open_ventilation_system);
    }

    @Override
    protected void initEvent() {
        ivHome.setOnClickListener(this);
        cbSwitchAutomaticMonitor.setOnCheckedChangeListener(this);
        mStatusManager.setOnUpdateVitalSignsCallback(this);
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
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_switch_automatic_monitor:
                mStatusManager.setCarbonDioxideAutoMonitorSwitch(b ? 1 : 0);
                mStatusManager.sendInfo(mContext);
                break;
            default:
                break;
        }
    }

    @Override
    public void updateCarbonDioxide(int level) {
        Log.d(TAG, "updateCarbonDioxide -> " + level);
        if (level == 0) {
            ivCarbonDioxideConcentration.setImageDrawable(getResources().getDrawable(R.drawable.icon_carbon_dioxide_concentration));
            tvVitalSigns.setText(getString(R.string.air_quality));
        } else if (level == 1) {
            ivCarbonDioxideConcentration.setImageDrawable(getResources().getDrawable(R.drawable.icon_carbon_dioxide_concentration_1));
            tvVitalSigns.setText(getString(R.string.air_quality));
        } else if (level > 1 & level <= 7) {
            ivCarbonDioxideConcentration.setImageDrawable(getResources().getDrawable(R.drawable.icon_carbon_dioxide_concentration_7));
        } else if (level == 8) {
            ivCarbonDioxideConcentration.setImageDrawable(getResources().getDrawable(R.drawable.icon_carbon_dioxide_concentration_8));
        } else if (level > 8 & level <= 17) {
            ivCarbonDioxideConcentration.setImageDrawable(getResources().getDrawable(R.drawable.icon_carbon_dioxide_concentration_17));
        } else if (level == 18) {
            ivCarbonDioxideConcentration.setImageDrawable(getResources().getDrawable(R.drawable.icon_carbon_dioxide_concentration_18));
        } else if (level > 18 & level <= 27) {
            ivCarbonDioxideConcentration.setImageDrawable(getResources().getDrawable(R.drawable.icon_carbon_dioxide_concentration_27));
        } else if (level == 28) {
            ivCarbonDioxideConcentration.setImageDrawable(getResources().getDrawable(R.drawable.icon_carbon_dioxide_concentration_28));
        } else if (level > 28 & level <= 37) {
            ivCarbonDioxideConcentration.setImageDrawable(getResources().getDrawable(R.drawable.icon_carbon_dioxide_concentration_37));
        } else if (level == 38) {
            ivCarbonDioxideConcentration.setImageDrawable(getResources().getDrawable(R.drawable.icon_carbon_dioxide_concentration_38));
        } else if (level > 38 & level <= 45) {
            ivCarbonDioxideConcentration.setImageDrawable(getResources().getDrawable(R.drawable.icon_carbon_dioxide_concentration_45));
        }
    }

    @Override
    public void updateMonitorAction(boolean informMaster, boolean sendInsidePhoto, boolean openInsideCamera, boolean openVenSystem) {
        Log.d(TAG, "updateMonitorAction -> " + informMaster + "*" + sendInsidePhoto + "*" + openInsideCamera + "*" + openVenSystem);
        ivInformMaster.setVisibility(informMaster ? View.VISIBLE : View.INVISIBLE);
        ivSendInsidePhoto.setVisibility(sendInsidePhoto ? View.VISIBLE : View.INVISIBLE);
        ivOpenInsideCamera.setVisibility(openInsideCamera ? View.VISIBLE : View.INVISIBLE);
        ivOpenVentilationSystem.setVisibility(openVenSystem ? View.VISIBLE : View.INVISIBLE);
    }
}
