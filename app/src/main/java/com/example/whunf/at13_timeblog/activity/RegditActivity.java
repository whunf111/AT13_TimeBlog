package com.example.whunf.at13_timeblog.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.whunf.at13_timeblog.R;
import com.example.whunf.at13_timeblog.util.BitmapUtil;
import com.example.whunf.at13_timeblog.util.HttpUtil;
import com.example.whunf.at13_timeblog.util.UrlUtil;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.TreeMap;

public class RegditActivity extends Activity implements View.OnClickListener {

    String iconPath=null;
    // 拍照之后的地址
    private Uri picUri = Uri.parse("file:///mnt/sdcard/temp00.jpg");
    // 裁减之后的地址
    private Uri imageUri = Uri.parse("file:///mnt/sdcard/temp.jpg");
    ImageView iv_avatar;
    EditText et_name, et_psw, et_re_psw;
    Button m_regdit, m_cancel;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regdit);
        inti();
    }

    private void inti() {
        iv_avatar = (ImageView) findViewById(R.id.m_icon);
        et_name = (EditText) findViewById(R.id.et_name);
        et_psw = (EditText) findViewById(R.id.et_psw);
        et_re_psw = (EditText) findViewById(R.id.et_re_psw);
        m_regdit = (Button) findViewById(R.id.m_regist);
        m_cancel = (Button) findViewById(R.id.m_cancel);
        iv_avatar.setOnClickListener(this);
        m_regdit.setOnClickListener(this);
        m_cancel.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(picUri, "image/*");
                intent.putExtra("crop", "true");
                // // aspectX aspectY 是宽高的比例
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                intent.putExtra("outputFormat", "JPEG");
                intent.putExtra("return-data", false);
                startActivityForResult(intent, 3);
                break;
            case 3:
                if (imageUri != null) {
                    Bitmap bitmap = getBitmapFromPath(imageUri);
                    Bitmap roundBitmap= BitmapUtil.getRoundBitmap(bitmap);
                    iv_avatar.setImageBitmap(roundBitmap);
                    iconPath=imageUri.getPath();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_icon:
                if (dialog == null) {
                    View view = getLayoutInflater().inflate(R.layout.regdit_avatar_select_layout,
                            null);
                    Button bt_selectFromPhone = (Button) view.findViewById(R.id.btn_get_from_local);
                    Button bt_selectFromCame = (Button) view.findViewById(R.id.btn_get_from_camera);
                    bt_selectFromCame.setOnClickListener(this);
                    bt_selectFromPhone.setOnClickListener(this);
                    dialog = new Dialog(this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(view);
                    Window window = dialog.getWindow();
                    window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                    WindowManager.LayoutParams lp = window.getAttributes();
                    lp.width = window.getWindowManager().getDefaultDisplay().getWidth();
                    window.setAttributes(lp);
                }
                dialog.show();
                break;
            case R.id.m_regist:
                String userName = et_name.getText().toString();
                String psw = et_psw.getText().toString();
                String rePsw = et_re_psw.getText().toString();
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(psw) || TextUtils.isEmpty(rePsw)) {
                    Toast.makeText(getApplicationContext(), "用户名密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!psw.equals(rePsw)) {
                    Toast.makeText(getApplicationContext(), "用户两次输入的密码不相等", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    TreeMap<String, String> treemap = new TreeMap();
                    treemap.put("name", userName);
                    treemap.put("psw", psw);
                    HashMap<String, File> hashMapFile = new HashMap();
                    if(iconPath!=null){
                        File f=new File(iconPath);
                        hashMapFile.put(userName,f);
                    }
                    String jsonString = HttpUtil.postMsg(UrlUtil.URL_REGIST, treemap, hashMapFile);
                    assert jsonString != null;
                    if (jsonString.endsWith("{result:1}")) {
                        Toast.makeText(getApplicationContext(), "用户注册成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "用户注册失败", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
                break;
            case R.id.m_cancel:
                finish();
                break;
            case R.id.btn_get_from_local:
                Intent intent_local=new Intent(Intent.ACTION_GET_CONTENT);
                intent_local.setType("image/*");
                intent_local.putExtra("crop", "true");
                intent_local.putExtra("aspectX", 1);
                intent_local.putExtra("aspectY", 1);
                intent_local.putExtra("scale", true);
                intent_local.putExtra("return-data", false);
                intent_local.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                intent_local.putExtra("outputFormat", "JPEG");
                startActivityForResult(intent_local,3);

                break;
            case R.id.btn_get_from_camera:
                dialog.cancel();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                intent.putExtra("noFaceDetection", false);
                startActivityForResult(intent, 2);
                break;
        }
    }

    private Bitmap getBitmapFromPath(Uri uri) {
        Bitmap b=null;
        try {
            b=BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }
}
