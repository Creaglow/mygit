package com.example.cpydr.cpys.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.example.cpydr.cpys.R;
import com.example.cpydr.cpys.adapter.ArticleAdapter;
import com.example.cpydr.cpys.adapter.ArticleViewPagerAdapter;
import com.example.cpydr.cpys.entity.Article;
import com.example.cpydr.cpys.util.SuperClass;

import java.util.ArrayList;
import java.util.List;

/**
 *ViewPager 能让界面左右滑动
 */
public class ArticleFragment extends Fragment {

    private List<Article> list;
    private ArticleAdapter myAdapter;
    private ViewPager viewPager;
    private ArticleViewPagerAdapter pagerAdapter;
    private List<Fragment> fragments;
    private RadioGroup mRadioGroup;
    private HorizontalScrollView horizontalScrollView;//得到滑动控件
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_article,container,false);
        // myAdapter = new ArticleAdapter(getActivity(),list);
        viewPager = (ViewPager)v.findViewById(R.id.vp_showfragment);




        //上对下的互动
        mRadioGroup = (RadioGroup)v.findViewById(R.id.article_radioGroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.article_rad0:
                        viewPager.setCurrentItem(0,true);
                        break;
                    case R.id.article_rad1:
                        viewPager.setCurrentItem(1,true);
                        break;
                    case R.id.article_rad2:
                        viewPager.setCurrentItem(2,true);
                        break;
                    case R.id.article_rad3:
                        viewPager.setCurrentItem(3,true);
                        break;
                    case R.id.article_rad4:
                        viewPager.setCurrentItem(4,true);
                        break;
                    case R.id.article_rad5:
                        viewPager.setCurrentItem(5,true);
                        break;
                    case R.id.article_rad6:
                        viewPager.setCurrentItem(6,true);
                        break;
                    case R.id.article_rad7:
                        viewPager.setCurrentItem(7,true);
                        break;
                    case R.id.article_rad8:
                        viewPager.setCurrentItem(8,true);
                        break;
                    case R.id.article_rad9:
                        viewPager.setCurrentItem(9,true);
                        break;

                }
            }
        });

        //下对上的互动
        horizontalScrollView =(HorizontalScrollView) v.findViewById(R.id.article_rad);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                RadioButton radioButton =(RadioButton)mRadioGroup.getChildAt(position);
                radioButton.setChecked(true);
                horizontalScrollView.scrollTo(radioButton.getLeft(),0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        fragments = new ArrayList<Fragment>();
        fragments.add(new AriticleBannerFragment(1));
        fragments.add(new AriticleNoBannerFragment(2));
        fragments.add(new AriticleNoBannerFragment(151));
        fragments.add(new AriticleNoBannerFragment(152));
        fragments.add(new AriticleNoBannerFragment(153));
        fragments.add(new AriticleNoBannerFragment(154));
        fragments.add(new AriticleNoBannerFragment(196));
        fragments.add(new AriticleNoBannerFragment(197));
        fragments.add(new AriticleNoBannerFragment(199));
        fragments.add(new AriticleNoBannerFragment(25));

        pagerAdapter = new ArticleViewPagerAdapter(getChildFragmentManager(),fragments);
        viewPager.setAdapter(pagerAdapter);




        return v;
    }



}
