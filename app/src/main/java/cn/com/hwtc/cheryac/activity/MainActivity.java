package cn.com.hwtc.cheryac.activity;

import android.os.Bundle;
import android.util.Log;

import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;

import cn.com.hwtc.cheryac.R;
import cn.com.hwtc.cheryac.adapter.UltraPagerAdapter;
import cn.com.hwtc.cheryac.manager.UltraScaleTransformer;

public class MainActivity extends BaseActivity implements UltraPagerAdapter.OnPositionClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private UltraViewPager ultraViewPager;
    private ArrayList<String> clsNameList;

    @Override
    protected int createLayout(Bundle saveInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ultraViewPager = findViewById(R.id.ultra_viewpager);
    }

    @Override
    protected void initEvent() {
        createClsList();
        UltraPagerAdapter ultraPagerAdapter = new UltraPagerAdapter(mContext);
        ultraPagerAdapter.setOnPositionClickListener(this);
        ultraViewPager.setAdapter(ultraPagerAdapter);

        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        ultraViewPager.setMultiScreen(0.5f);
        ultraViewPager.setItemRatio(1.0f);
        ultraViewPager.setRatio(2.0f);
        ultraViewPager.setMaxHeight(720);
        ultraViewPager.setAutoMeasureHeight(true);
        UltraScaleTransformer ultraScaleTransformer = new UltraScaleTransformer(0.6f);
        ultraViewPager.setPageTransformer(false, ultraScaleTransformer);
        //ultraViewPager.setInfiniteLoop(true);//设置无限循环模式
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        mStatusManager.setPanel(0);
        mStatusManager.sendInfo(mContext);
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

        int panel = 0;
        if (position == 0) {
            panel = 1;
        } else if (position == 1) {
            panel = 4;
        } else if (position == 2) {
            panel = 2;
        } else if (position == 3) {
            panel = 3;
        }
        mStatusManager.setPanel(panel);

        mStatusManager.sendInfo(mContext);
    }

}
