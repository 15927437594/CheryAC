package cn.com.hwtc.cheryac.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.Nullable;

import cn.com.hwtc.cheryac.R;
import cn.com.hwtc.cheryac.manager.StatusManager;

/**
 * user: Created by jid on 2019/9/2.
 * email: jid@hwtc.com.cn
 * description:
 */
public class SmartFragranceActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = SmartFragranceActivity.class.getSimpleName();
    private Context mContext;
    private ImageView ivHome;
    private StatusManager mStatusManager;
    private CheckBox cbSwitchFragrance;
    private RadioButton rbPerfumeBottleFirst;
    private RadioButton rbPerfumeBottleSecond;
    private RadioButton rbPerfumeBottleThird;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_fragrance);
        initView();
        initEvent();
    }

    @Override
    protected void initView() {
        super.initView();
        ivHome = findViewById(R.id.iv_home);
        cbSwitchFragrance = findViewById(R.id.cb_switch_fragrance);
        rbPerfumeBottleFirst = findViewById(R.id.rb_perfume_bottle_first);
        rbPerfumeBottleSecond = findViewById(R.id.rb_perfume_bottle_second);
        rbPerfumeBottleThird = findViewById(R.id.rb_perfume_bottle_third);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        if (mContext == null) {
            mContext = getApplicationContext();
        }
        mStatusManager = StatusManager.getInstance();
        ivHome.setOnClickListener(this);
        cbSwitchFragrance.setOnCheckedChangeListener(this);
        rbPerfumeBottleFirst.setOnCheckedChangeListener(this);
        rbPerfumeBottleSecond.setOnCheckedChangeListener(this);
        rbPerfumeBottleThird.setOnCheckedChangeListener(this);
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
            case R.id.cb_switch_fragrance:
                Log.d(TAG, "onCheckedChanged cb_switch_fragrance -> " + b);
                break;
            case R.id.rb_perfume_bottle_first:
                Log.d(TAG, "onCheckedChanged rb_perfume_bottle_first -> " + b);

                break;
            case R.id.rb_perfume_bottle_second:
                Log.d(TAG, "onCheckedChanged rb_perfume_bottle_second -> " + b);

                break;
            case R.id.rb_perfume_bottle_third:
                Log.d(TAG, "onCheckedChanged rb_perfume_bottle_third -> " + b);

                break;
            default:
                break;
        }
    }
}
