package cn.com.hwtc.cheryac.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
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
    private TextView tvOpenVentilationSystem;
    private TextView tvOpenInsideCamera;
    private TextView tvInformMaster;
    private TextView tvSendInsidePhoto;

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
        tvOpenVentilationSystem = findViewById(R.id.tv_open_ventilation_system);
        tvOpenInsideCamera = findViewById(R.id.tv_open_inside_camera);
        tvInformMaster = findViewById(R.id.tv_inform_master);
        tvSendInsidePhoto = findViewById(R.id.tv_send_inside_photo);
        LinearLayout llInside_air_fresh = findViewById(R.id.ll_inside_air_fresh);
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
    public void updateCarbonDioxide(int riseState, int level) {
        Log.d(TAG, "updateCarbonDioxide riseState -> " + riseState);
        Log.d(TAG, "updateCarbonDioxide level -> " + level);
        if (level == 0) {
            ivCarbonDioxideConcentration.setImageDrawable(getResources().getDrawable(R.drawable.icon_carbon_dioxide_concentration_0));
        } else if (level == 1) {
            ivCarbonDioxideConcentration.setImageDrawable(getResources().getDrawable(R.drawable.icon_carbon_dioxide_concentration_1));
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

        if (riseState == 2) {
            if (level > 0 & level < 8) {
                ivOpenVentilationSystem.setVisibility(View.VISIBLE);
                tvOpenVentilationSystem.setVisibility(View.VISIBLE);
                tvOpenVentilationSystem.setText(getString(R.string.inside_air_fresh));
                tvVitalSigns.setText(getString(R.string.monitor_vital_signs_4));
            } else if (level >= 8 & level < 18) {
                //心形右侧打钩项每隔一秒显示一行
                tvOpenVentilationSystem.setVisibility(View.VISIBLE);
                tvOpenVentilationSystem.setText(getString(R.string.open_ventilation_system));

                tvVitalSigns.setText(getString(R.string.monitor_vital_signs_1));
            } else if (level >= 18 & level < 28) {
                tvVitalSigns.setText(getString(R.string.monitor_vital_signs_2));
            } else if (level >= 28 & level < 38) {
                tvVitalSigns.setText(getString(R.string.monitor_vital_signs_3));
            } else if (level >= 38) {
                ivOpenVentilationSystem.setVisibility(View.VISIBLE);
                tvOpenVentilationSystem.setVisibility(View.VISIBLE);
                tvOpenVentilationSystem.setText(getString(R.string.monitor_vital_signs_rescue));
                tvOpenVentilationSystem.setTextColor(getResources().getColor(R.color.colorRed));
            }
        } else if (riseState == 3) {
            if (level > 27 & level <= 37) {
                tvVitalSigns.setText(getString(R.string.monitor_vital_signs_5));
            } else if (level > 17 & level <= 27) {
                tvVitalSigns.setText(getString(R.string.monitor_vital_signs_6));
            } else if (level > 7 & level <= 17) {
                tvVitalSigns.setText(getString(R.string.monitor_vital_signs_7));
            } else if (level == 7) {
                tvVitalSigns.setText(getString(R.string.monitor_vital_signs_8));
            } else {

            }
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
