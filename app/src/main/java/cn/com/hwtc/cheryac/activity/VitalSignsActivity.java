package cn.com.hwtc.cheryac.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import cn.com.hwtc.cheryac.R;
import cn.com.hwtc.cheryac.manager.StatusManager;

/**
 * user: Created by jid on 2019/9/2.
 * email: jid@hwtc.com.cn
 * description:
 */
public class VitalSignsActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private ImageView ivHome;
    private StatusManager mStatusManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital_signs);
        initView();
        initEvent();
    }

    @Override
    protected void initView() {
        super.initView();
        ivHome = findViewById(R.id.iv_home);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        if (mContext == null) {
            mContext = getApplicationContext();
        }
        mStatusManager = StatusManager.getInstance();
        ivHome.setOnClickListener(this);
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
}
