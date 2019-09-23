package cn.com.hwtc.cheryac.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

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
    private ImageView ivFog;

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
        ivFog = findViewById(R.id.iv_fog);
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
        if (cbSwitchAutomaticDefog.isChecked()) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 0.0f);
            alphaAnimation.setDuration(100L);
            alphaAnimation.setFillAfter(true);
            ivFog.startAnimation(alphaAnimation);
        }

        updateFogProbability(mStatusManager.getFogProbability());
        updateCurrentHumidity(mStatusManager.getHumidUpState(), mStatusManager.getHumidSensor());


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_home) {
            mStatusManager.onBack();
        }
    }

    @Override
    public void updateCurrentHumidity(boolean humidUpState, int humidity) {
        //除雾完成显示描述
        //关闭自动除雾的状态下,无"除雾已完成"log显示
        //开启自动除雾状态下,无"除雾已完成log显示
        //当湿度达到75%以上后,开始识别是否除雾已完成
        //当湿度降低到45%以下后,开启"除雾已完成log显示
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
        tvFogProbability.setText(String.format(Locale.getDefault(), "%d%%", fogProbability));
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.getId() == R.id.cb_switch_automatic_defog) {
            Log.d(TAG, "onCheckedChanged -> " + mStatusManager.getAutoMistSwitch());
            mStatusManager.setAutoMistSwitch(b ? 1 : 0);
            // TODO: 2019/9/16 添加透明动画
            //开启:逐步淡化和扩散雾气画面,汽车图层上叠加一层不透明度为0的汽车图像,逐渐将不透明度调整为100%
            //关闭:静态显示雾气在汽车玻璃上的画面
            if (b) {
                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f); //第一个参数开始的透明度，第二个参数结束的透明度
                alphaAnimation.setDuration(2000L); //完成这个动作所需时间
                alphaAnimation.setFillAfter(true); //动画执行完毕后是否停在结束时的透明度上
                ivFog.startAnimation(alphaAnimation);

                rlDefogOk.setVisibility(View.INVISIBLE);
            } else {
                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 1.0f); //第一个参数开始的透明度，第二个参数结束的透明度
                alphaAnimation.setDuration(100L);
                alphaAnimation.setFillAfter(true);
                ivFog.startAnimation(alphaAnimation);

                rlDefogOk.setVisibility(View.INVISIBLE);
            }
            mStatusManager.sendInfo(mContext);
        }
    }
}
