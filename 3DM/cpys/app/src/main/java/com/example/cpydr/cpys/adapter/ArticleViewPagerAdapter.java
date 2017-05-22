package com.example.cpydr.cpys.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by cpydr on 2016/1/7.
 */
public class ArticleViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    public ArticleViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {

        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
