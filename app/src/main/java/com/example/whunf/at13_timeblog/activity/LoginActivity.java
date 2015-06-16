package com.example.whunf.at13_timeblog.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.whunf.at13_timeblog.R;
import com.example.whunf.at13_timeblog.model.User;
import com.example.whunf.at13_timeblog.util.HttpUtil;
import com.example.whunf.at13_timeblog.util.UrlUtil;

import java.util.HashMap;


public class LoginActivity extends Activity implements View.OnClickListener {
    EditText et_userName, et_password;
    Button bt_login;
    TextView tv_regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll()
                .build();
        StrictMode.setThreadPolicy(threadPolicy);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        et_password = (EditText) findViewById(R.id.et_psw);
        et_userName = (EditText) findViewById(R.id.et_name);
        bt_login = (Button) findViewById(R.id.m_login);
        tv_regist = (TextView) findViewById(R.id.m_regist);
        bt_login.setOnClickListener(this);
        tv_regist.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_login:
                String username = et_userName.getText().toString();
                String password = et_password.getText().toString();
                HashMap<String, String> hashMap = new HashMap();
                hashMap.put("name", username);
                hashMap.put("psw", password);
                String jsonString = HttpUtil.postMsg(UrlUtil.URL_LOGIN, hashMap);
                assert jsonString != null;
                if (jsonString.startsWith("{result")) {
                    Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                } else {
                    User user = JSON.parseObject(jsonString, User.class);
                    User.userName=user.getUserName();
                    User.birth=user.getBirth();
                    User.id=user.getId();
                    User.nick=user.getNick();
                    User.phone=user.getPhone();
                    User.photo=user.getPhoto();
                    User.sex=user.getSex();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.m_regist:
                Intent intent=new Intent(LoginActivity.this,RegditActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
