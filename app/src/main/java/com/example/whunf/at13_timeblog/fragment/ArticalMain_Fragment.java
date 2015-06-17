package com.example.whunf.at13_timeblog.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.whunf.at13_timeblog.R;
import com.example.whunf.at13_timeblog.activity.PublishArtical;
import com.example.whunf.at13_timeblog.adapter.MyListAdapter;
import com.example.whunf.at13_timeblog.model.Artical;
import com.example.whunf.at13_timeblog.util.HttpUtil;
import com.example.whunf.at13_timeblog.util.UrlUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArticalMain_Fragment extends Fragment implements View.OnClickListener {
    private static final String TAG = ArticalMain_Fragment.class.getSimpleName();
    View v;
    TextView tv;
    ListView lv;
    Context context;
    List<Artical> articalList;
    OnArticalItemclick oaic;

    public ArticalMain_Fragment() {
        // Required empty public constructor
//        context=getActivity().getApplicationContext();
    }
    public ArticalMain_Fragment(Context context) {
        // Required empty public constructor
//        context=getActivity().getApplicationContext();
        this.context=context;
    }

    public void setOnArticalItemclick(OnArticalItemclick oaic){
        this.oaic=oaic;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_artical_main_, container, false);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        initListener();
        initData();
        MyListAdapter mla= new MyListAdapter(articalList,context);
        lv.setAdapter(mla);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                oaic.onClick(articalList.get(position));
            }
        });
    }

    private void initData() {
        String list_URL = UrlUtil.URL_LOAD_ARTICLES;
        String list_json = HttpUtil.getMsg(list_URL);


        //Google 出品 GSON 解析工具
//        Gson gson = new Gson();
//        Type type = new TypeToken<ArrayList<Artical>>() {
//        }.getType();
//        List<Artical> articalList = new ArrayList();
//        articalList = gson.fromJson(list_json, type);

        //Alibaba 出品 JSON 解析工具
          articalList = JSON.parseArray(list_json, Artical.class);

        //手动解析Json
//        int i1 = 0;
//        try {
//            JSONArray jsonArray = new JSONArray(list_json);
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//                String a_id = jsonObject.getString("a_id");
//                String time = jsonObject.getString("time");
//                int i3 = 332;
//                i1 = i3;
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        //纯手动解析
//        String key = "a_id";
//        int pre_Pos = list_json.indexOf(key);
//        int end_Pos = pre_Pos + key.length();
//        int comma_Pos=list_json.indexOf(",");
//        String _str = list_json.substring(end_Pos+2, comma_Pos);
//        System.out.print(_str);

    }

    private void initListener() {
        tv.setOnClickListener(this);
    }

    private void initView() {
        tv = (TextView) v.findViewById(R.id.tv_write_article);
        lv = (ListView) v.findViewById(R.id.main_listlv);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_write_article) {
            Intent intent = new Intent(getActivity(), PublishArtical.class);
            startActivity(intent);
        }

    }

    public interface OnArticalItemclick {
        void onClick(Artical artical);
    }



}
