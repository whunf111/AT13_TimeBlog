package com.example.whunf.at13_timeblog.util;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by whunf on 2015/6/9 in PC
 */
public class HttpUtil {
    private static final String TAG = HttpUtil.class.getSimpleName();

    public static String getMsg(String str_url) {
        try {
            URL url = new URL(str_url);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            if (httpConnection.getResponseCode() == 200) {
                InputStream is = httpConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = "";
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            }

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }


    public static String postMsg(String url, HashMap<String, String> params) {
        // 使用NameValuePair来保存要传递的Post参数
        List<NameValuePair> pars = new ArrayList<NameValuePair>();
        // 添加要传递的参数
        for (Map.Entry<String, String> entry : params.entrySet()) {
            pars.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        try {
            // 设置字符集
            HttpEntity httpentity = new UrlEncodedFormEntity(pars, "UTF-8");
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            // 请求httpRequest(要提交的内容)
            httppost.setEntity(httpentity);
            // 执行并获取结果
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        resEntity.getContent()));
                StringBuilder sb = new StringBuilder();
                String s;
                while ((s = br.readLine()) != null) {
                    sb.append(s);
                }
                httpclient.getConnectionManager().shutdown();
                return sb.toString();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String postMsg(String url, TreeMap<String, String> params,
                                 Map<String, File> files) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        // HttpParams httpParams = new BasicHttpParams();
        // HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
        // HttpConnectionParams.setSoTimeout(httpParams, 20000);
        //
        // SchemeRegistry registry = new SchemeRegistry();
        // registry.register(new Scheme("http", PlainSocketFactory
        // .getSocketFactory(), 80));
        // registry.register(new Scheme("https", SSLSocketFactory
        // .getSocketFactory(), 443));

        try {
            // 提交的内容对象
            MultipartEntity mulEntity = new MultipartEntity();
            // 上传的文件
            if (null != files) {
                // 遍历文件集合添加文件
                for (Map.Entry<String, File> entry : files.entrySet()) {
                    FileBody fb = new FileBody(entry.getValue());
                    mulEntity.addPart(entry.getKey(), fb);
                }
            }
            // 添加文本参数列表
            if (null != params) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    mulEntity.addPart(
                            entry.getKey(),
                            new StringBody(entry.getValue(), Charset
                                    .forName("UTF-8")));
                }
            }
            httppost.setEntity(mulEntity);

            // 执行并获取结果
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        resEntity.getContent()));
                StringBuilder sb = new StringBuilder();
                String s;
                while ((s = br.readLine()) != null) {
                    sb.append(s);
                }
                httpclient.getConnectionManager().shutdown();
                return sb.toString();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }



    public static boolean download(String url, String savePath) {
        HttpGet get = new HttpGet(url);
        HttpClient client = new DefaultHttpClient();
        try {
            HttpResponse respon = client.execute(get);
            if (respon.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = respon.getEntity().getContent();
                FileOutputStream out = new FileOutputStream(savePath);
                byte[] buffer = new byte[1024];
                int b;
                while ((b = in.read(buffer)) != -1) {
                    out.write(buffer, 0, b);
                }
                out.flush();
                out.close();
                in.close();
                client.getConnectionManager().shutdown();
                return true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }


}
