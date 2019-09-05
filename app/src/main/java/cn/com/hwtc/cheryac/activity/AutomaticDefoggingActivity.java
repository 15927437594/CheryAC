package cn.com.hwtc.cheryac.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import cn.com.hwtc.cheryac.R;

/**
 * user: Created by jid on 2019/9/2.
 * email: jid@hwtc.com.cn
 * description:
 */
public class AutomaticDefoggingActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivHome;

    @Override
    protected int createLayout(Bundle saveInstanceState) {
        return R.layout.activity_automatic_defogging;
    }

    @Override
    protected void initView() {
        ivHome = findViewById(R.id.iv_home);
    }

    @Override
    protected void initEvent() {
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
