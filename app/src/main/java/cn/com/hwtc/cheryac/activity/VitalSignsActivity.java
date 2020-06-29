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
import cn.com.hwtc.cheryac.animation.FrameSurfaceView;
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
    private LinearLayout llInsideAirFresh;
    private LinearLayout llAiRobot;
    private FrameSurfaceView mFrameSurfaceView;

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
        llInsideAirFresh = findViewById(R.id.ll_inside_air_fresh);
        llAiRobot = findViewById(R.id.ll_ai_robot);
        mFrameSurfaceView = findViewById(R.id.fs_electrocardiogram);
        initVisualizer();
    }

    @Override
    protected void initEvent() {
        ivHome.setOnClickListener(this);
        cbSwitchAutomaticMonitor.setOnCheckedChangeListener(this);
        mStatusManager.setOnUpdateVitalSignsCallback(this);
        if (mHandler != null) {
            mHandler.postDelayed(mAiRobotRunnable, 1000L); //以1s的频率红色字闪烁
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        cbSwitchAutomaticMonitor.setChecked(mStatusManager.getCarbonDioxideAutoMonitorSwitch() == 1);
        updateCarbonDioxide(mStatusManager.getRiseState(), mStatusManager.getCarbonDioxideLevel());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_home) {
            mStatusManager.onBack();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.getId() == R.id.cb_switch_automatic_monitor) {
            Log.d(TAG, "onCheckedChanged -> " + b);
            mStatusManager.setCarbonDioxideAutoMonitorSwitch(b ? 1 : 0);
            mStatusManager.sendInfo(mContext);
            mFrameSurfaceView.setStart(!b);
        }
    }

    private Runnable mAiRobotRunnable = new Runnable() {
        @Override
        public void run() {
//            Log.d(TAG, "call mAiRobotRunnable");
            if (mHandler != null) {
                mHandler.postDelayed(this, 500L);
            }

            if (mStatusManager.getCarbonDioxideLevel() >= 38) {
                if (llAiRobot.getVisibility() == View.VISIBLE) {
                    llAiRobot.setVisibility(View.INVISIBLE);
                } else {
                    llAiRobot.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    /**
     * riseState: 2表示上升,3表示下降
     * level: CO2的浓度等级
     */
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
                tvOpenVentilationSystem.setVisibility(View.INVISIBLE);
                tvOpenInsideCamera.setVisibility(View.INVISIBLE);
                tvInformMaster.setVisibility(View.INVISIBLE);
                tvSendInsidePhoto.setVisibility(View.INVISIBLE);
                llAiRobot.setVisibility(View.INVISIBLE);
                llInsideAirFresh.setVisibility(View.VISIBLE);
                tvVitalSigns.setText("");
                ivInformMaster.setVisibility(View.INVISIBLE);
                ivSendInsidePhoto.setVisibility(View.INVISIBLE);
                ivOpenInsideCamera.setVisibility(View.INVISIBLE);
                ivOpenVentilationSystem.setVisibility(View.INVISIBLE);
            } else if (level >= 8 & level < 18) {
                llAiRobot.setVisibility(View.INVISIBLE);
                llInsideAirFresh.setVisibility(View.INVISIBLE);
                //心形右侧打钩项每隔一秒显示一行
                tvOpenVentilationSystem.setVisibility(View.VISIBLE);
                tvOpenInsideCamera.setVisibility(View.VISIBLE);
                tvInformMaster.setVisibility(View.VISIBLE);
                tvSendInsidePhoto.setVisibility(View.VISIBLE);
                tvVitalSigns.setText(getString(R.string.monitor_vital_signs_1));
            } else if (level >= 18 & level < 28) {
                llAiRobot.setVisibility(View.INVISIBLE);
                llInsideAirFresh.setVisibility(View.INVISIBLE);
                tvOpenVentilationSystem.setVisibility(View.VISIBLE);
                tvOpenInsideCamera.setVisibility(View.VISIBLE);
                tvInformMaster.setVisibility(View.VISIBLE);
                tvSendInsidePhoto.setVisibility(View.VISIBLE);
                tvVitalSigns.setText(getString(R.string.monitor_vital_signs_2));
            } else if (level >= 28 & level < 38) {
                llAiRobot.setVisibility(View.INVISIBLE);
                llInsideAirFresh.setVisibility(View.INVISIBLE);
                tvOpenVentilationSystem.setVisibility(View.VISIBLE);
                tvOpenInsideCamera.setVisibility(View.VISIBLE);
                tvInformMaster.setVisibility(View.VISIBLE);
                tvSendInsidePhoto.setVisibility(View.VISIBLE);
                tvVitalSigns.setText(getString(R.string.monitor_vital_signs_3));
            } else if (level >= 38) {
                llAiRobot.setVisibility(View.VISIBLE);
                llInsideAirFresh.setVisibility(View.INVISIBLE);
                tvOpenVentilationSystem.setVisibility(View.VISIBLE);
                tvOpenInsideCamera.setVisibility(View.VISIBLE);
                tvInformMaster.setVisibility(View.VISIBLE);
                tvSendInsidePhoto.setVisibility(View.VISIBLE);
                tvVitalSigns.setText("");
            }
        } else if (riseState == 3) {
            if (level > 37) {
                llAiRobot.setVisibility(View.VISIBLE);
                llInsideAirFresh.setVisibility(View.INVISIBLE);
                tvOpenVentilationSystem.setVisibility(View.VISIBLE);
                tvOpenInsideCamera.setVisibility(View.VISIBLE);
                tvInformMaster.setVisibility(View.VISIBLE);
                tvSendInsidePhoto.setVisibility(View.VISIBLE);
                tvVitalSigns.setText("");
            } else if (level > 27) {
                llAiRobot.setVisibility(View.INVISIBLE);
                llInsideAirFresh.setVisibility(View.INVISIBLE);
                tvOpenVentilationSystem.setVisibility(View.VISIBLE);
                tvOpenInsideCamera.setVisibility(View.VISIBLE);
                tvInformMaster.setVisibility(View.VISIBLE);
                tvSendInsidePhoto.setVisibility(View.VISIBLE);
                tvVitalSigns.setText(getString(R.string.monitor_vital_signs_5));
            } else if (level > 17) {
                tvOpenVentilationSystem.setVisibility(View.VISIBLE);
                tvOpenInsideCamera.setVisibility(View.VISIBLE);
                tvInformMaster.setVisibility(View.VISIBLE);
                tvSendInsidePhoto.setVisibility(View.VISIBLE);
                llAiRobot.setVisibility(View.INVISIBLE);
                llInsideAirFresh.setVisibility(View.INVISIBLE);
                tvVitalSigns.setText(getString(R.string.monitor_vital_signs_6));
            } else if (level > 7) {
                llAiRobot.setVisibility(View.INVISIBLE);
                llInsideAirFresh.setVisibility(View.INVISIBLE);
                //心形右侧打钩项每隔一秒显示一行
                tvOpenVentilationSystem.setVisibility(View.VISIBLE);
                tvOpenInsideCamera.setVisibility(View.VISIBLE);
                tvInformMaster.setVisibility(View.VISIBLE);
                tvSendInsidePhoto.setVisibility(View.VISIBLE);
                tvVitalSigns.setText(getString(R.string.monitor_vital_signs_7));
            } else if (level == 7) {
                llAiRobot.setVisibility(View.INVISIBLE);
                tvVitalSigns.setText(getString(R.string.monitor_vital_signs_8));

                ivInformMaster.setVisibility(View.INVISIBLE);
                ivSendInsidePhoto.setVisibility(View.INVISIBLE);
                ivOpenInsideCamera.setVisibility(View.INVISIBLE);
                ivOpenVentilationSystem.setVisibility(View.INVISIBLE);
            } else {
                llAiRobot.setVisibility(View.INVISIBLE);
                llInsideAirFresh.setVisibility(View.VISIBLE);
                tvOpenVentilationSystem.setVisibility(View.INVISIBLE);
                tvOpenInsideCamera.setVisibility(View.INVISIBLE);
                tvInformMaster.setVisibility(View.INVISIBLE);
                tvSendInsidePhoto.setVisibility(View.INVISIBLE);
                tvVitalSigns.setText("");

                ivInformMaster.setVisibility(View.INVISIBLE);
                ivSendInsidePhoto.setVisibility(View.INVISIBLE);
                ivOpenInsideCamera.setVisibility(View.INVISIBLE);
                ivOpenVentilationSystem.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void updateMonitorAction(boolean informMaster, boolean sendInsidePhoto, boolean openInsideCamera, boolean openVenSystem) {
        Log.d(TAG, "updateMonitorAction -> " + informMaster + "*" + sendInsidePhoto + "*" + openInsideCamera + "*" + openVenSystem);
        int carbonDioxideLevel = mStatusManager.getCarbonDioxideLevel();
        Log.d(TAG, "updateMonitorAction carbonDioxideLevel -> " + carbonDioxideLevel);
        if (carbonDioxideLevel >= 8) {
            ivInformMaster.setVisibility(informMaster ? View.VISIBLE : View.INVISIBLE);
            ivSendInsidePhoto.setVisibility(sendInsidePhoto ? View.VISIBLE : View.INVISIBLE);
            ivOpenInsideCamera.setVisibility(openInsideCamera ? View.VISIBLE : View.INVISIBLE);
            ivOpenVentilationSystem.setVisibility(openVenSystem ? View.VISIBLE : View.INVISIBLE);
        }
    }

    private void initVisualizer() {
        if (!mFrameSurfaceView.isInit()) {
            mFrameSurfaceView.setOnDrawnFrameChange(new FrameSurfaceView.OnDrawnFrameChange() {
                @Override
                public void onDrawnIndex(int index) {

                }
            });

            mFrameSurfaceView.setBitmapIds(0);
            mFrameSurfaceView.setDuration(4000);
            mFrameSurfaceView.setRepeatTimes(FrameSurfaceView.INFINITE);
            mFrameSurfaceView.start();
            mFrameSurfaceView.setStart(true);
        }
    }

}
