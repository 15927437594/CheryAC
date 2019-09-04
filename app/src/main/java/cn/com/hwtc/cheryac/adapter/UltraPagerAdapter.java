package cn.com.hwtc.cheryac.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.com.hwtc.cheryac.R;

/**
 * user: Created by jid on 2019/9/2.
 * email: jid@hwtc.com.cn
 * description:
 */
public class UltraPagerAdapter extends PagerAdapter {
    private List<Integer> list = null;
    private OnPositionClickListener onPositionClickListener = null;
    private LayoutInflater mLayoutInflater;

    public UltraPagerAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        addImageResource();
    }

    private void addImageResource() {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(R.drawable.icon_air_purification);
        list.add(R.drawable.icon_smart_fragrance);
        list.add(R.drawable.icon_vital_signs);
        list.add(R.drawable.icon_automatic_defogging);
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        View view = mLayoutInflater.inflate(R.layout.viewpager_launcher, container, false);
        ImageView ivViewPager = view.findViewById(R.id.iv_viewpager);
        ivViewPager.setBackgroundResource(list.get(position));
        container.addView(view);
        ivViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPositionClickListener != null) {
                    onPositionClickListener.onPositionClick(position);
                }
            }
        });
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    public interface OnPositionClickListener {
        void onPositionClick(int position);
    }

    public void setOnPositionClickListener(OnPositionClickListener listener) {
        this.onPositionClickListener = listener;
    }

}
