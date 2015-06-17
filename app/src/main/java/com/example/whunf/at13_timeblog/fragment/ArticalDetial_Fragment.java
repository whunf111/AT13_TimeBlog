package com.example.whunf.at13_timeblog.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.whunf.at13_timeblog.R;
import com.example.whunf.at13_timeblog.model.Artical;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticalDetial_Fragment extends Fragment implements View.OnClickListener {

    View v;
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;
    EditText et1;
   public  Artical art;

    public ArticalDetial_Fragment() {
        // Required empty public constructor
    }

    public  void setData(Artical artical){
        art=artical;
        tv3.setText(art.getTitle());
        tv4.setText(art.getU_name());
        tv5.setText(art.getTime());
//        tv6.setText(art.);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_artical_detial, container, false);
        initView();
        return v;
    }

    @Override
    public void onStart() {
        initListener();

    }

    private void initListener() {
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv7.setOnClickListener(this);
        tv6.setText("333");
    }

    private void initView() {
        tv1 = (TextView) v.findViewById(R.id.tv_reply_count);
        tv2 = (TextView) v.findViewById(R.id.tv_back);
        tv3 = (TextView) v.findViewById(R.id.tv_title);
        tv4 = (TextView) v.findViewById(R.id.tv_user);
        tv5 = (TextView) v.findViewById(R.id.tv_time);
        tv6 = (TextView) v.findViewById(R.id.tv_content);
        tv7 = (TextView) v.findViewById(R.id.tv_publish_reply);
        et1 = (EditText) v.findViewById(R.id.et_reply);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_reply_count:
                break;
            case R.id.tv_back:
                break;
            case R.id.tv_publish_reply:
                break;
            default:
                break;
        }
    }
}
