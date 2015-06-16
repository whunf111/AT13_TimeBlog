package com.example.whunf.at13_timeblog.Listener;

import android.content.Context;
import android.os.AsyncTask;
import android.text.BoringLayout;
import android.util.Log;
import android.widget.ImageView;

import com.example.whunf.at13_timeblog.util.BitmapUtil;
import com.example.whunf.at13_timeblog.util.DirURL;
import com.example.whunf.at13_timeblog.util.HttpUtil;
import com.example.whunf.at13_timeblog.util.UrlUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by whunf on 2015/6/15 in PC
 */
public class LoadIcon {
    private final static String TAG="LoadIcon";
    private static LoadIcon loadIcon = null;
    private Map<String, AsysImageDownLoadTask> imgDowTask = new HashMap<>();


    public static synchronized LoadIcon getInstance() {
        if (loadIcon == null) {
            loadIcon = new LoadIcon();
        }
        return loadIcon;
    }


    // ��ʾ�û�ͷ��
    public void showHeadIcon(Context context, ImageView iv, String name,
                             String url, onDownLoadListener listener) {
        boolean isHasIconInRemote = false;
        //������û�ͷ��δ�����������������ж��Ƿ������ص�ַ
        if (!imgDowTask.containsKey(name)) {
            isHasIconInRemote = null != url && !"".equals(url);
        }
        int result = BitmapUtil.getBitmap(context, name, isHasIconInRemote, iv);
        if (result == 0) {
            startLoad(name, url, listener); // �����ص�ַ������Ҫ����ͷ��
        }
    }


    // ��ʼ����
    private void startLoad(String userName, String url,
                           onDownLoadListener listener) {
        // �ж��Ƿ��Ѿ��������ڼ��ظ�ͷ������������������������
        if (imgDowTask.containsKey(userName)) {
            return;
        }
        // �����������񣬲���ӵ�Map�м�¼���Է�ֹ�ж�������ͬʱ����ͬһ��ͷ������
        AsysImageDownLoadTask aIDtask = new AsysImageDownLoadTask(userName, listener);
        imgDowTask.put(userName, aIDtask);
        aIDtask.execute(url);
    }


    public interface onDownLoadListener {
        void onSuccess(String username);

        void onFailed(String username);
    }

    class AsysImageDownLoadTask extends AsyncTask<Object, Integer, Object> {

        onDownLoadListener odll;
        String userName;
        boolean isEnd = false;

        public AsysImageDownLoadTask(String userName, onDownLoadListener odll) {
            this.userName = userName;
            this.odll = odll;
        }

        @Override
        protected Object doInBackground(Object... params) {
            Log.i(TAG, "start Do background job ");
            String imgUrl;
            imgUrl = String.valueOf(params[0]);
            Log.i(TAG, "background job id done");
            return HttpUtil.download(imgUrl, DirURL.DIR_PATH+userName);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Log.i(TAG, "start Do onPostExecute job ");
            if (odll != null) {
                if ((boolean) o) {
                    Log.i(TAG,"ok");
                    odll.onSuccess(this.userName);
                    imgDowTask.remove(this.userName);
                } else {
                    Log.i(TAG,"failed");
                    odll.onFailed(this.userName);
                }
            }
            Log.i(TAG, "Do onPostExecute job done ");

        }
    }

}
