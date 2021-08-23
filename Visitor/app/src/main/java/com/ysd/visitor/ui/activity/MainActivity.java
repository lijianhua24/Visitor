package com.ysd.visitor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.huashi.otg.sdk.HandlerMsg;
import com.megvii.facepp.multi.sdk.DLmkDetectApi;
import com.megvii.facepp.multi.sdk.FaceDetectApi;
import com.megvii.facepp.multi.sdk.FacePPImage;
import com.megvii.facepp.multi.sdk.FacePPMultiAuthManager;
import com.megvii.facepp.multi.sdk.FaceppApi;
import com.stx.xhb.xbanner.XBanner;
import com.ysd.visitor.R;
import com.ysd.visitor.app.App;
import com.ysd.visitor.base.BaseActivity;
import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.bean.GetAdverListBean;
import com.ysd.visitor.bean.LoginBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.presenter.LoginPresenter;
import com.ysd.visitor.utlis.ConUtil;
import com.ysd.visitor.utlis.SecondScreen;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.internal.Util;

import static com.ysd.visitor.utlis.ConUtil.readAssetsData;

public class MainActivity extends BaseActivity {
    private LinearLayout main_register;
    private LinearLayout main_inquire, main_authority, main_settings;

    @Override
    protected LoginPresenter providePresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initData() {


        main_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
        main_inquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InquireActivity.class));

            }
        });
        main_authority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AuthorityActivity.class));
            }
        });
        main_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });


    }

    @Override
    protected void initView() {
        checkPermission();
        main_register = findViewById(R.id.main_register);
        main_inquire = findViewById(R.id.main_inquire);
        main_authority = findViewById(R.id.main_authority);
        main_settings = findViewById(R.id.main_settings);
        //initSDK();
        int megviifacepp_model = FaceppApi.getInstance().initHandle(readAssetsData(MainActivity.this, "megviifacepp_model"));//初始化facepp sdk，加载模型
        if (megviifacepp_model == 0) {
            Toast.makeText(this, "初始化成功", Toast.LENGTH_SHORT).show();
            FaceDetectApi.getInstance().initFaceDetect();// 初始化人脸检测
            DLmkDetectApi.getInstance().initDLmkDetect();//初始化稠密点检测
        }

    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_main;
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(MainActivity.this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 10);
            }
        }
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!Settings.canDrawOverlays(this)) {
                    // SYSTEM_ALERT_WINDOW permission not granted...
                    Toast.makeText(MainActivity.this, "not granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void initSDK() {

    }

    @Override
    public void onLoginSuccess(LoginBean data) {
        Toast.makeText(this, "" + data.getMsg(), Toast.LENGTH_SHORT).show();
        if (data.getCode() == 0) {
            String data1 = data.getData();
            SharedPreferences.Editor edit = App.sharedPreferences.edit();
            edit.putString("token", data1);
            edit.commit();
            HashMap<String, String> hashMaps = new HashMap<>();
            hashMaps.put("devcode",data1);
            String s1 = new Gson().toJson(hashMaps);
            RequestBody requestBodys = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s1);
            mPresenter.getAdverListPresenter(requestBodys);
        }
    }

    @Override
    public void onLoginFailure(Throwable e) {

    }

    @Override
    public void onAdverListSuccess(GetAdverListBean data) {
            *//*if (data.getCode() == 0){
                List<GetAdverListBean.DataBean> data1 = data.getData();
                mDisplayManager = (DisplayManager) MainActivity.this.getSystemService(Context.DISPLAY_SERVICE);
                displays = mDisplayManager.getDisplays(); //得到显示器数组
                SecondScreen mPresentations = new SecondScreen(getApplicationContext(), displays[1], R.layout.second);//displays[1]是副屏
                mPresentations.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                mPresentations.show();
                XBanner second_xbanner = mPresentations.findViewById(R.id.second_xbanner);
                second_xbanner.setData(data1,null);
                second_xbanner.setmAdapter(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        Glide.with(MainActivity.this).load(data1.get(position).getUrl()).into((ImageView) view);
                    }
                });
            }*//*
    }

    @Override
    public void onAdverListFailure(Throwable e) {

    }*/
}
