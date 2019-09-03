package cn.com.hwtc.cheryac.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraScaleTransformer;

import java.util.ArrayList;

import cn.com.hwtc.cheryac.R;
import cn.com.hwtc.cheryac.adapter.UltraPagerAdapter;
import cn.com.hwtc.cheryac.manager.StatusManager;

public class MainActivity extends BaseActivity implements UltraPagerAdapter.OnPositionClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context mContext = null;
    private UltraViewPager ultraViewPager;
    private StatusManager mStatusManager;
    private ArrayList<String> clsNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    @Override
    protected void initView() {
        super.initView();
        ultraViewPager = findViewById(R.id.ultra_viewpager);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        if (mContext == null) {
            mContext = getApplicationContext();
        }
        createClsList();
        mStatusManager = StatusManager.getInstance();
        UltraPagerAdapter ultraPagerAdapter = new UltraPagerAdapter(mContext);
        ultraPagerAdapter.setOnPositionClickListener(this);
        ultraViewPager.setAdapter(ultraPagerAdapter);

        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        ultraViewPager.setMultiScreen(0.5f);
        ultraViewPager.setItemRatio(1.0f);
        ultraViewPager.setRatio(2.0f);
        ultraViewPager.setMaxHeight(720);
        ultraViewPager.setAutoMeasureHeight(true);
        ultraViewPager.setPageTransformer(false, new UltraScaleTransformer());
    }

    private void createClsList() {
        clsNameList = new ArrayList<>();
        clsNameList.add(0, "cn.com.hwtc.cheryac.activity.AirPurificationActivity");
        clsNameList.add(1, "cn.com.hwtc.cheryac.activity.SmartFragranceActivity");
        clsNameList.add(2, "cn.com.hwtc.cheryac.activity.VitalSignsActivity");
        clsNameList.add(3, "cn.com.hwtc.cheryac.activity.AutomaticDefoggingActivity");
    }

    @Override
    public void onPositionClick(int position) {
        Log.d(TAG, "onPositionClick -> " + position);
        String clsName = clsNameList.get(position);
        Log.d(TAG, "clsName -> " + clsName);
        mStatusManager.startActivity(mContext, "cn.com.hwtc.cheryac", clsName);
    }
}
