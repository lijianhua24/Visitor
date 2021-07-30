package com.ysd.visitor.ui.activity;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.huashi.otg.sdk.HandlerMsg;
import com.ysd.visitor.R;
import com.ysd.visitor.base.BaseActivity;
import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.utlis.SecondScreen;

public class MainActivity extends BaseActivity {
    DisplayManager mDisplayManager;//屏幕管理类
    Display[] displays;//屏幕数组
    private Button main_register;
    private Button main_inquire;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == HandlerMsg.READ_SUCCESS){
                Toast.makeText(MainActivity.this, ""+10000, Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected BasePresenter providePresenter() {
        return null;
    }

    @Override
    protected void initData() {

        mDisplayManager = (DisplayManager) MainActivity.this.getSystemService(Context.DISPLAY_SERVICE);
        displays = mDisplayManager.getDisplays(); //得到显示器数组
        SecondScreen mPresentation = new SecondScreen(getApplicationContext(), displays[1], R.layout.second);//displays[1]是副屏
        mPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        mPresentation.show();
        main_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                mDisplayManager = (DisplayManager) MainActivity.this.getSystemService(Context.DISPLAY_SERVICE);
                displays = mDisplayManager.getDisplays(); //得到显示器数组
                SecondScreen mPresentation = new SecondScreen(getApplicationContext(), displays[1], R.layout.activity_main);//displays[1]是副屏
                mPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                mPresentation.show();
            }
        });
        main_inquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InquireActivity.class));

            }
        });
    }

    @Override
    protected void initView() {
        checkPermission();
        main_register = findViewById(R.id.main_register);
        main_inquire = findViewById(R.id.main_inquire);
        initSDK();

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

    @Override
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
    private void initSDK(){

    }

}
