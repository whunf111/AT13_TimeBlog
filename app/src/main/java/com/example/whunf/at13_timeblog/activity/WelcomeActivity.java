package com.example.whunf.at13_timeblog.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.whunf.at13_timeblog.R;
import com.example.whunf.at13_timeblog.util.DirURL;
import com.example.whunf.at13_timeblog.util.UrlUtil;

import java.io.File;


public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        boolean isCreate = makeDirCache();
        if (isCreate) {
            Toast.makeText(getApplicationContext(),  "二级缓存文件夹已经创建", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "二级缓存文件夹已经存在，无需建立", Toast.LENGTH_SHORT).show();

        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpToLogin();
            }
        }, 2000);


    }

    //手机文件系统中的二级缓存
    private boolean makeDirCache() {
        File f = new File(DirURL.DIR_PATH);
        if (!f.exists()) {
            return f.mkdirs();
        }
        return false;
    }

    private void jumpToLogin() {
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
