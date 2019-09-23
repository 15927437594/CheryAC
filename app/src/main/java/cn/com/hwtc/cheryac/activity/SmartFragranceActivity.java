package cn.com.hwtc.cheryac.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.hwtc.cheryac.R;

/**
 * user: Created by jid on 2019/9/2.
 * email: jid@hwtc.com.cn
 * description:
 */
public class SmartFragranceActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = SmartFragranceActivity.class.getSimpleName();
    private ImageView ivHome;
    private CheckBox cbSwitchFragrance;
    private RadioButton rbPerfumeBottleFirst;
    private RadioButton rbPerfumeBottleSecond;
    private RadioButton rbPerfumeBottleThird;
    private RelativeLayout rlWelcomeMode;
    private RelativeLayout rlLongDistanceMode;
    private RelativeLayout rlRelaxMode;
    private TextView tvWelcome;
    private TextView tvLongDistance;
    private TextView tvRelax;
    private ImageView ivWelcome;
    private ImageView ivLongDistance;
    private ImageView ivRelax;
    private TextView tvFragranceConcentrationHigh;
    private TextView tvFragranceConcentrationMiddle;
    private TextView tvFragranceConcentrationLow;
    private ImageView ivFragranceConcentration;
    private ImageView ivPerfumeBottlePetals;

    @Override
    protected int createLayout(Bundle saveInstanceState) {
        return R.layout.activity_smart_fragrance;
    }

    @Override
    protected void initView() {
        ivHome = findViewById(R.id.iv_home);
        rlWelcomeMode = findViewById(R.id.rl_welcome_mode);
        rlLongDistanceMode = findViewById(R.id.rl_long_distance_mode);
        rlRelaxMode = findViewById(R.id.rl_relax_mode);
        cbSwitchFragrance = findViewById(R.id.cb_switch_fragrance);
        rbPerfumeBottleFirst = findViewById(R.id.rb_perfume_bottle_first);
        rbPerfumeBottleSecond = findViewById(R.id.rb_perfume_bottle_second);
        rbPerfumeBottleThird = findViewById(R.id.rb_perfume_bottle_third);
        tvWelcome = findViewById(R.id.tv_welcome);
        tvLongDistance = findViewById(R.id.tv_long_distance);
        tvRelax = findViewById(R.id.tv_relax);
        ivWelcome = findViewById(R.id.iv_welcome);
        ivLongDistance = findViewById(R.id.iv_long_distance);
        ivRelax = findViewById(R.id.iv_relax);
        tvFragranceConcentrationHigh = findViewById(R.id.tv_fragrance_concentration_high);
        tvFragranceConcentrationMiddle = findViewById(R.id.tv_fragrance_concentration_middle);
        tvFragranceConcentrationLow = findViewById(R.id.tv_fragrance_concentration_low);
        ivFragranceConcentration = findViewById(R.id.iv_fragrance_concentration);
        ivPerfumeBottlePetals = findViewById(R.id.iv_perfume_bottle_petals);
    }

    @Override
    protected void initEvent() {
        ivHome.setOnClickListener(this);
        rlWelcomeMode.setOnClickListener(this);
        rlLongDistanceMode.setOnClickListener(this);
        rlRelaxMode.setOnClickListener(this);
        cbSwitchFragrance.setOnCheckedChangeListener(this);
        rbPerfumeBottleFirst.setOnCheckedChangeListener(this);
        rbPerfumeBottleSecond.setOnCheckedChangeListener(this);
        rbPerfumeBottleThird.setOnCheckedChangeListener(this);
        tvFragranceConcentrationHigh.setOnClickListener(this);
        tvFragranceConcentrationMiddle.setOnClickListener(this);
        tvFragranceConcentrationLow.setOnClickListener(this);
        ivFragranceConcentration.setOnClickListener(this);
        if (mHandler != null) {
            mHandler.postDelayed(mChangeConcentrationRunnable, 1000L);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        cbSwitchFragrance.setChecked(mStatusManager.getAutoFragranceSwitch() == 1);
        updateFragranceMode();
        updateFragranceConcentration();
        updateFragranceType();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    private void updateFragranceMode() {
        Log.d(TAG, "updateFragranceMode -> " + mStatusManager.getFragranceMode());
        if (mStatusManager.getFragranceMode() == 1) {
            ivWelcome.setImageDrawable(getResources().getDrawable(R.drawable.icon_welcome_able));
            tvWelcome.setTextColor(getResources().getColor(R.color.colorFragranceMode));
        } else {
            ivWelcome.setImageDrawable(getResources().getDrawable(R.drawable.icon_welcome_unable));
            tvWelcome.setTextColor(getResources().getColor(R.color.colorWhite));
        }

        if (mStatusManager.getFragranceMode() == 2) {
            ivLongDistance.setImageDrawable(getResources().getDrawable(R.drawable.icon_long_distance_able));
            tvLongDistance.setTextColor(getResources().getColor(R.color.colorFragranceMode));
        } else {
            ivLongDistance.setImageDrawable(getResources().getDrawable(R.drawable.icon_long_distance_unable));
            tvLongDistance.setTextColor(getResources().getColor(R.color.colorWhite));
        }

        if (mStatusManager.getFragranceMode() == 3) {
            ivRelax.setImageDrawable(getResources().getDrawable(R.drawable.icon_relax_able));
            tvRelax.setTextColor(getResources().getColor(R.color.colorFragranceMode));
        } else {
            ivRelax.setImageDrawable(getResources().getDrawable(R.drawable.icon_relax_unable));
            tvRelax.setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    private void updateFragranceConcentration() {
        Log.d(TAG, "updateFragranceConcentration -> " + mStatusManager.getFragranceConcentration());
        if (mStatusManager.getFragranceConcentration() == 0) {
            ivFragranceConcentration.setImageDrawable(getResources().getDrawable(R.drawable.icon_fragrance_concentration_default));
        } else if (mStatusManager.getFragranceConcentration() == 1) {
            ivFragranceConcentration.setImageDrawable(getResources().getDrawable(R.drawable.icon_fragrance_concentration_low));
        } else if (mStatusManager.getFragranceConcentration() == 2) {
            ivFragranceConcentration.setImageDrawable(getResources().getDrawable(R.drawable.icon_fragrance_concentration_middle));
        } else if (mStatusManager.getFragranceConcentration() == 3) {
            ivFragranceConcentration.setImageDrawable(getResources().getDrawable(R.drawable.icon_fragrance_concentration_high));
        }
    }

    private void updateFragranceType() {
        Log.d(TAG, "updateFragranceType -> " + mStatusManager.getFragranceType());
        if (mStatusManager.getFragranceType() == 0) {
            rbPerfumeBottleFirst.setChecked(false);
            rbPerfumeBottleSecond.setChecked(false);
            rbPerfumeBottleThird.setChecked(false);
        } else if (mStatusManager.getFragranceType() == 1) {
            rbPerfumeBottleFirst.setChecked(true);
            rbPerfumeBottleSecond.setChecked(false);
            rbPerfumeBottleThird.setChecked(false);
        } else if (mStatusManager.getFragranceType() == 2) {
            rbPerfumeBottleFirst.setChecked(false);
            rbPerfumeBottleSecond.setChecked(true);
            rbPerfumeBottleThird.setChecked(false);
        } else if (mStatusManager.getFragranceType() == 3) {
            rbPerfumeBottleFirst.setChecked(false);
            rbPerfumeBottleSecond.setChecked(false);
            rbPerfumeBottleThird.setChecked(true);
        }
    }

    private Runnable mChangeConcentrationRunnable = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "call mChangeConcentrationRunnable");
            if (mHandler != null) {
                mHandler.postDelayed(this, 1000L);
            }

            if (mStatusManager.getAutoControlPurification()) {
                if (mStatusManager.getFragranceConcentration() == 0) {
                    mStatusManager.setFragranceConcentration(1);
                } else if (mStatusManager.getFragranceConcentration() == 1) {
                    mStatusManager.setFragranceConcentration(2);
                } else if (mStatusManager.getFragranceConcentration() == 2) {
                    mStatusManager.setFragranceConcentration(3);
                } else if (mStatusManager.getFragranceConcentration() == 3) {
                    mStatusManager.setFragranceConcentration(1);
                }
                updateFragranceConcentration();
                mStatusManager.sendInfo(mContext);
            }
        }
    };

    private Runnable mAnimationRunnable = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "call mAnimationRunnable");
            Animation animationRepeat = AnimationUtils.loadAnimation(mContext, R.anim.mix_animation);
            ivPerfumeBottlePetals.startAnimation(animationRepeat);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home:
                mStatusManager.onBack();
                break;
            case R.id.rl_welcome_mode:
                //开启自动模式时,将香氛类型置为空(信号值为0)
                if (mStatusManager.getFragranceMode() != 1) {
                    mStatusManager.setFragranceMode(1);
                    updateFragranceMode();
                    mStatusManager.setFragranceType(0);
                    updateFragranceType();
                    mStatusManager.sendInfo(mContext);
                    mStatusManager.setAutoControlPurification(true);
                }
                break;
            case R.id.rl_long_distance_mode:
                //开启自动模式时,将香氛类型置为空(信号值为0)
                if (mStatusManager.getFragranceMode() != 2) {
                    mStatusManager.setFragranceMode(2);
                    updateFragranceMode();
                    mStatusManager.setFragranceType(0);
                    updateFragranceType();
                    mStatusManager.sendInfo(mContext);
                    mStatusManager.setAutoControlPurification(true);
                }
                break;
            case R.id.rl_relax_mode:
                //开启自动模式时,将香氛类型置为空(信号值为0)
                if (mStatusManager.getFragranceMode() != 3) {
                    mStatusManager.setFragranceMode(3);
                    updateFragranceMode();
                    mStatusManager.setFragranceType(0);
                    updateFragranceType();
                    mStatusManager.sendInfo(mContext);
                    mStatusManager.setAutoControlPurification(true);
                }
                break;
            case R.id.tv_fragrance_concentration_high:
                mStatusManager.setFragranceMode(0);
                mStatusManager.setFragranceConcentration(3);
                updateFragranceMode();
                updateFragranceConcentration();
                mStatusManager.sendInfo(mContext);
                mStatusManager.setAutoControlPurification(false);
                break;
            case R.id.tv_fragrance_concentration_middle:
                mStatusManager.setFragranceMode(0);
                mStatusManager.setFragranceConcentration(2);
                updateFragranceMode();
                updateFragranceConcentration();
                mStatusManager.sendInfo(mContext);
                mStatusManager.setAutoControlPurification(false);
                break;
            case R.id.tv_fragrance_concentration_low:
                mStatusManager.setFragranceMode(0);
                mStatusManager.setFragranceConcentration(1);
                updateFragranceMode();
                updateFragranceConcentration();
                mStatusManager.sendInfo(mContext);
                mStatusManager.setAutoControlPurification(false);
                break;
            case R.id.iv_fragrance_concentration:
                mStatusManager.setFragranceMode(0);

                if (mStatusManager.getFragranceConcentration() == 0) {
                    mStatusManager.setFragranceConcentration(1);
                } else if (mStatusManager.getFragranceConcentration() == 1) {
                    mStatusManager.setFragranceConcentration(2);
                } else if (mStatusManager.getFragranceConcentration() == 2) {
                    mStatusManager.setFragranceConcentration(3);
                } else if (mStatusManager.getFragranceConcentration() == 3) {
                    mStatusManager.setFragranceConcentration(1);
                }

                updateFragranceMode();
                updateFragranceConcentration();
                mStatusManager.sendInfo(mContext);
                mStatusManager.setAutoControlPurification(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_switch_fragrance:
                Log.d(TAG, "onCheckedChanged cb_switch_fragrance -> " + b);
                mStatusManager.setAutoFragranceSwitch(b ? 1 : 0);
                mStatusManager.sendInfo(mContext);
                // TODO: 2019/9/12 功能添加
                //香氛开启:花朵从10%的大小开始逐渐放大至100%后,在80%~100%持续循环;放大过程中,同步旋转,旋转角速度15度/秒,旋转持续
                //香氛结束:静态显示
                if (b) {
                    Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.scale_animation);
                    ivPerfumeBottlePetals.startAnimation(animation);
                    if (mHandler != null) {
                        mHandler.postDelayed(mAnimationRunnable, 1000L);
                    }
                } else {
                    ivPerfumeBottlePetals.clearAnimation();
                    if (mHandler != null) {
                        mHandler.removeCallbacks(mAnimationRunnable);
                    }
                }
                break;
            case R.id.rb_perfume_bottle_first:
                Log.d(TAG, "onCheckedChanged rb_perfume_bottle_first -> " + b);
                //点击香氛类型时,将自动模式切换为手动模式
                if (b) {
                    if (mStatusManager.getFragranceType() != 1) {
                        mStatusManager.setFragranceType(1);
                        updateFragranceType();
                        mStatusManager.setFragranceMode(0);
                        updateFragranceMode();
                        mStatusManager.sendInfo(mContext);
                        mStatusManager.setAutoControlPurification(false);
                    }
                }
                break;
            case R.id.rb_perfume_bottle_second:
                Log.d(TAG, "onCheckedChanged rb_perfume_bottle_second -> " + b);
                //点击香氛类型时,将自动模式切换为手动模式
                if (b) {
                    if (mStatusManager.getFragranceType() != 2) {
                        mStatusManager.setFragranceType(2);
                        updateFragranceType();
                        mStatusManager.setFragranceMode(0);
                        updateFragranceMode();
                        mStatusManager.sendInfo(mContext);
                        mStatusManager.setAutoControlPurification(false);
                    }
                }
                break;
            case R.id.rb_perfume_bottle_third:
                Log.d(TAG, "onCheckedChanged rb_perfume_bottle_third -> " + b);
                //点击香氛类型时,将自动模式切换为手动模式
                if (b) {
                    if (mStatusManager.getFragranceType() != 3) {
                        mStatusManager.setFragranceType(3);
                        updateFragranceType();
                        mStatusManager.setFragranceMode(0);
                        updateFragranceMode();
                        mStatusManager.sendInfo(mContext);
                        mStatusManager.setAutoControlPurification(false);
                    }
                }
                break;
            default:
                break;
        }
    }

}
