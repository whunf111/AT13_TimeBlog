package com.example.whunf.at13_timeblog.activity;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.whunf.at13_timeblog.R;
import com.example.whunf.at13_timeblog.adapter.MyPageAdapter;
import com.example.whunf.at13_timeblog.fragment.ArticalDetial_Fragment;
import com.example.whunf.at13_timeblog.fragment.ArticalMain_Fragment;
import com.example.whunf.at13_timeblog.fragment.Me_fragment;
import com.example.whunf.at13_timeblog.fragment.Setting_Fragment;
import com.example.whunf.at13_timeblog.fragment.Squar_Fragment;
import com.example.whunf.at13_timeblog.model.Artical;
import com.example.whunf.at13_timeblog.model.ArticalDetial;
import com.nostra13.universalimageloader.core.ImageLoader;


public class MainActivity extends FragmentActivity implements View.OnClickListener {
    ViewPager viewPager;
    RadioGroup radioGroup;
    RadioButton rb0, rb1, rb2;
    Fragment[] fragments;
    ArticalDetial_Fragment adf;
    Context context;
    int screenWidth;

    //是否详情界面已经弹出
    boolean isShow = false;

    FrameLayout frameLayout;
    LinearLayout linearLayout;
    LinearLayout.LayoutParams fragmentParams;


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //这里设置为严格模式，允许在主线程进行网络操作
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll()
                .build();
        StrictMode.setThreadPolicy(threadPolicy);

        screenWidth = getWindowManager().getDefaultDisplay().getWidth();

        frameLayout = (FrameLayout) findViewById(R.id.fragment_detail);
        linearLayout = (LinearLayout) findViewById(R.id.m_content);

        //设置帖子列表的布局（显示出来）
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        layoutParams.width = screenWidth;
        linearLayout.setLayoutParams(layoutParams);

        //设置帖子详情的布局（隐蔽）
        fragmentParams = (LinearLayout.LayoutParams) frameLayout.getLayoutParams();
        fragmentParams.width = screenWidth;
        fragmentParams.leftMargin = -screenWidth;
        isShow = false;
        frameLayout.setLayoutParams(fragmentParams);


        context = this;
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
        ((ArticalMain_Fragment) fragments[0]).setOnArticalItemclick(oaic);
        fragments[1] = new Squar_Fragment();
        fragments[2] = new Me_fragment(context);
        fragments[3] = new Setting_Fragment();

        adf = new ArticalDetial_Fragment();
        adf.setOnPopUpBackListener(opub);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_detail, adf);
        ft.commit();


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
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.tab_square:
                viewPager.setCurrentItem(1, true);
                break;
            case R.id.tab_me:
                viewPager.setCurrentItem(2, true);
                break;
            default:
                break;
        }
    }

    ArticalMain_Fragment.OnArticalItemclick oaic = new ArticalMain_Fragment.OnArticalItemclick() {
        @Override
        public void onClick(Artical artical) {
            adf.setData(artical);
            rightPopUp();
        }
    };

    ArticalDetial_Fragment.onPopUpBack opub = new ArticalDetial_Fragment.onPopUpBack() {
        @Override
        public void popBack() {
            leftHind();
            Log.i("TAG", "返回按钮点击回调已完成");
        }
    };


    //将详情页右拉到主界面
    public void rightPopUp() {
        new PopUpTask().execute(30);
    }

    //将详情页左拉隐藏
    public void leftHind() {
        new PopUpTask().execute(-30);
    }


    class PopUpTask extends AsyncTask<Integer, Integer, Integer> {


        @Override
        protected Integer doInBackground(Integer... params) {
            int progress = params[0];
            int margin = fragmentParams.leftMargin;
            if (progress > 0) {
                while (margin < 0) {
                    margin += progress;
                    publishProgress(margin);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (margin >= 0) {
                    return 0;
                }
            } else if (progress < 0) {
                while (margin > -screenWidth) {
                    margin += progress;
                    publishProgress(margin);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (margin < -screenWidth) {
                    return 1;
                }
            }
            return -1;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            fragmentParams.leftMargin = values[0];
            frameLayout.setLayoutParams(fragmentParams);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (integer == 0) {
            Toast.makeText(getApplicationContext(), "详情界面已经完全弹出", Toast.LENGTH_SHORT).show();
                fragmentParams.leftMargin = 0;
            }else if(integer==1){
                Toast.makeText(getApplicationContext(), "详情界面已经完全隐蔽", Toast.LENGTH_SHORT).show();
                fragmentParams.leftMargin=-screenWidth;
            }
            frameLayout.setLayoutParams(fragmentParams);
        }
    }


}
