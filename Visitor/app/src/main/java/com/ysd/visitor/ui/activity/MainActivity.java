package com.ysd.visitor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.huashi.otg.sdk.HsOtgApi;
import com.megvii.facepp.multi.sdk.DLmkDetectApi;
import com.megvii.facepp.multi.sdk.FaceDetectApi;
import com.megvii.facepp.multi.sdk.FaceppApi;
import com.ysd.visitor.R;
import com.ysd.visitor.app.App;
import com.ysd.visitor.base.BaseActivity;
import com.ysd.visitor.presenter.LoginPresenter;
import com.ysd.visitor.utlis.SecondScreen;

import static com.ysd.visitor.utlis.ConUtil.readAssetsData;

public class MainActivity extends BaseActivity {
    private LinearLayout main_register;
    private LinearLayout main_inquire, main_authority, main_settings,main_opendoor;
    private Handler handler = new Handler();
    DisplayManager mDisplayManager;//屏幕管理类
    Display[] displays;//屏幕数组

    @Override
    protected LoginPresenter providePresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initData() {
        String imageuri = App.sharedPreferences.getString("imageuri", null);
        mDisplayManager = (DisplayManager) MainActivity.this.getSystemService(Context.DISPLAY_SERVICE);
        displays = mDisplayManager.getDisplays(); //得到显示器数组
        SecondScreen mPresentations = new SecondScreen(getApplicationContext(), displays[1], R.layout.second);//displays[1]是副屏
        mPresentations.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        mPresentations.show();
        ImageView second_xbanner = mPresentations.findViewById(R.id.second_xbanner);
        Log.d("TAG", "initData: " + imageuri);
        Glide.with(MainActivity.this).load(imageuri).into(second_xbanner);



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
        main_opendoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OpenDoorActivity.class));
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
        main_opendoor = findViewById(R.id.main_opendoor);
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

}
