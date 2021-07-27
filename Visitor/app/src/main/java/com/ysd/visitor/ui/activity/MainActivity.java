package com.ysd.visitor.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.ysd.visitor.R;

public class MainActivity extends AppCompatActivity {
    DisplayManager mDisplayManager;//屏幕管理类
    Display[] displays;//屏幕数组
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        Button main_register = findViewById(R.id.main_register);
        main_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                mDisplayManager = (DisplayManager)MainActivity.this.getSystemService(Context.DISPLAY_SERVICE);
                displays =mDisplayManager.getDisplays(); //得到显示器数组
                SecondScreen mPresentation =new SecondScreen (getApplicationContext(),displays[1],R.layout.activity_main);//displays[1]是副屏
                mPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                mPresentation.show();
            }
        });
        mDisplayManager = (DisplayManager)MainActivity.this.getSystemService(Context.DISPLAY_SERVICE);
        displays =mDisplayManager.getDisplays(); //得到显示器数组
        SecondScreen mPresentation =new SecondScreen (getApplicationContext(),displays[1],R.layout.second);//displays[1]是副屏
        mPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        mPresentation.show();
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

}
