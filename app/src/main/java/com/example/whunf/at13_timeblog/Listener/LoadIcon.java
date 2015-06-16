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


    // 显示用户头像
    public void showHeadIcon(Context context, ImageView iv, String name,
                             String url, onDownLoadListener listener) {
        boolean isHasIconInRemote = false;
        //如果该用户头像未有下载任务下载则判断是否有下载地址
        if (!imgDowTask.containsKey(name)) {
            isHasIconInRemote = null != url && !"".equals(url);
        }
        int result = BitmapUtil.getBitmap(context, name, isHasIconInRemote, iv);
        if (result == 0) {
            startLoad(name, url, listener); // 有下载地址并且需要下载头像
        }
    }


    // 开始加载
    private void startLoad(String userName, String url,
                           onDownLoadListener listener) {
        // 判断是否已经有任务在加载该头像，如果已有则不再启动任务加载
        if (imgDowTask.containsKey(userName)) {
            return;
        }
        // 创建加载任务，并添加到Map中记录，以防止有多条任务同时加载同一个头像的情况
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
