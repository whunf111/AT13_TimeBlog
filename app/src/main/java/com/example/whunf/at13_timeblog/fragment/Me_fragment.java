package com.example.whunf.at13_timeblog.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whunf.at13_timeblog.Listener.LoadIcon;
import com.example.whunf.at13_timeblog.R;
import com.example.whunf.at13_timeblog.model.User;
import com.example.whunf.at13_timeblog.util.BitmapUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

/**
 * A simple {@link Fragment} subclass.
 */
public class Me_fragment extends Fragment {
    private final static String TAG = Me_fragment.class.getSimpleName();
    View view;
    ImageView imageView, icon;
    WifiManager wifiManager;
    Context context;
    EditText et_userName, et_nickName, et_sex, et_phone, et_id;
    TextView tv_birthday;

    ImageLoader imageLoader;
    DisplayImageOptions options;


    public Me_fragment() {
        // Required empty public constructor

    }

    public Me_fragment(Context context) {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        this.context = context;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me_fragment, container, false);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }


    @Override
    public void onStart() {
        super.onStart();
        initView();
        initData();
        if (wifiManager.isWifiEnabled()) {
            Drawable drawable = getResources().getDrawable(R.drawable.youwifi1);
            imageView.setImageDrawable(drawable);
        } else {
            Drawable drawable = getResources().getDrawable(R.drawable.icon_background);
            imageView.setImageDrawable(drawable);
        }
    }

    private void initData() {
        et_userName.setText(User.userName);
        et_sex.setText(User.sex);
        et_phone.setText(User.phone);
        et_nickName.setText(User.nick);
        et_id.setText(User.id);
        tv_birthday.setText(User.birth);
        Log.i(TAG, User.photo);
//        LoadIcon.getInstance().showHeadIcon(getActivity().getApplicationContext(), icon, User.userName, User
//                .photo, onDownLoadListener);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.add)
                .showImageForEmptyUri(R.drawable.close)
                .showImageOnFail(R.drawable.btn_publish)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(70))
                .build();
        imageLoader.displayImage(User.photo, icon, options);
    }

    private void initView() {
        imageView = (ImageView) view.findViewById(R.id.ic_bg_layer);
        et_userName = (EditText) view.findViewById(R.id.tv_user_name);
        tv_birthday = (TextView) view.findViewById(R.id.tv_birth);
        et_id = (EditText) view.findViewById(R.id.tv_user_id);
        et_nickName = (EditText) view.findViewById(R.id.tv_nick);
        et_phone = (EditText) view.findViewById(R.id.tv_phone);
        et_sex = (EditText) view.findViewById(R.id.tv_sex);
        icon = (ImageView) view.findViewById(R.id.im_icon);
    }

//    LoadIcon.onDownLoadListener onDownLoadListener = new LoadIcon.onDownLoadListener() {
//        @Override
//        public void onSuccess(String username) {
//            Log.i(TAG,"call back ok"+"   "+username);
//            BitmapUtil.getBitmap(getActivity().getApplicationContext(), username, true, icon);
//        }
//
//        @Override
//        public void onFailed(String username) {
//            Toast.makeText(getActivity().getApplicationContext(), "Õº∆¨º”‘ÿ ß∞‹", Toast.LENGTH_SHORT).show();
//            icon.setImageResource(R.drawable.user);
//        }
//    };
}
