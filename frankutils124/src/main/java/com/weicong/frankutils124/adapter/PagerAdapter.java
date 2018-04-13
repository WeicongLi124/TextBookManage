package com.weicong.frankutils124.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author: Frank
 * @time: 2018/3/11 14:47
 * @e-mail: 912220261@qq.com
 * Function:
 */

public class PagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> titles;
    private boolean isDestroy;

    public PagerAdapter(FragmentManager fm, List<Fragment> fragments, boolean isDestroy) {
        super(fm);
        this.fragments = fragments;
        this.isDestroy = isDestroy;
    }

    public PagerAdapter (FragmentManager fm, List<Fragment> fragments, List<String> titles, boolean isDestroy){
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
        this.isDestroy = isDestroy;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (!isDestroy) {}
        else super.destroyItem(container, position, object);
    }
}
