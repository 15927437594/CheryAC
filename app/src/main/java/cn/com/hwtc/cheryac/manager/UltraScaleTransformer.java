package cn.com.hwtc.cheryac.manager;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * user: Created by jid on 2019/9/3.
 * email: jid@hwtc.com.cn
 * description:
 */
public class UltraScaleTransformer implements ViewPager.PageTransformer {
    private float minScale;

    public UltraScaleTransformer(float scale) {
        minScale = scale;
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        float scaleFactor = minScale + (1 - minScale) * (1 - Math.abs(position));

        if (position < 0) { // [-1,0]
            // Scale the page down (between MIN_SCALE and 1)
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        } else if (position == 0) {
            page.setScaleX(1);
            page.setScaleY(1);
        } else if (position <= 1) { // (0,1]
            // Scale the page down (between MIN_SCALE and 1)
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        }
    }

}
