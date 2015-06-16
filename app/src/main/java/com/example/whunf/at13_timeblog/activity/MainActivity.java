package com.example.whunf.at13_timeblog.activity;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.whunf.at13_timeblog.R;
import com.example.whunf.at13_timeblog.adapter.MyPageAdapter;
import com.example.whunf.at13_timeblog.fragment.ArticalMain_Fragment;
import com.example.whunf.at13_timeblog.fragment.Me_fragment;
import com.example.whunf.at13_timeblog.fragment.Setting_Fragment;
import com.example.whunf.at13_timeblog.fragment.Squar_Fragment;
import com.nostra13.universalimageloader.core.ImageLoader;


public class MainActivity extends FragmentActivity implements View.OnClickListener {
    ViewPager viewPager;
    RadioGroup radioGroup;
    RadioButton rb0, rb1, rb2;
    Fragment[] fragments;
    Context context;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll()
                .build();
        StrictMode.setThreadPolicy(threadPolicy);

        context=this;
        initView();
        initData();
        setListener();
        MyPageAdapter mpa = new MyPageAdapter(getSupportFragmentManager(), fragments);

        viewPager.setAdapter(mpa);
        viewPager.setCurrentItem(2);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                              @Override
                                              public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                              }

                                              @Override
                                              public void onPageSelected(int position) {
                                                  if (position < 3) {
                                                      radioGroup.clearCheck();
                                                      RadioButton rb = (RadioButton) radioGroup.getChildAt(position);
                                                      rb.setChecked(true);
                                                  } else if (position == 3) {
                                                      radioGroup.clearCheck();
                                                  }
                                              }

                                              @Override
                                              public void onPageScrollStateChanged(int state) {
                                              }
                                          }

        );

    }

    private void setListener() {
        rb0.setOnClickListener(this);
        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);
    }

    private void initData() {
        fragments = new Fragment[4];
        fragments[0] = new ArticalMain_Fragment(this);
        fragments[1] = new Squar_Fragment();
        fragments[2] = new Me_fragment(context);
        fragments[3] = new Setting_Fragment();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.m_pager);
        radioGroup = (RadioGroup) findViewById(R.id.tab_group);
        rb0 = (RadioButton) findViewById(R.id.tab_article);
        rb1 = (RadioButton) findViewById(R.id.tab_square);
        rb2 = (RadioButton) findViewById(R.id.tab_me);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_article:
                viewPager.setCurrentItem(0,true);
                break;
            case R.id.tab_square:
                viewPager.setCurrentItem(1,true);
                break;
            case R.id.tab_me:
                viewPager.setCurrentItem(2,true);
                break;
            default:
                break;
        }
    }
}
