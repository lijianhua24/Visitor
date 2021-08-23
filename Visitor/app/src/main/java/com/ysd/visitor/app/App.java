package com.ysd.visitor.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.drawee.backends.pipeline.Fresco;


/**
 * @Package: App.java
 * @describe(描述)：com.zr.car.app 程序入口
 * @ClassName: App
 * @data（日期）: 2020/4/3 0003
 * @time（时间）: 09:05
 * @author（作者）: 李建华
 * @UpdateRemark: 更新说明：Administrator
 * @Version: 3.5
 **/

public class App extends Application {
    private static App sContext;
    public static SharedPreferences sharedPreferences;


    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        Fresco.initialize(sContext);
        // externalFilesDir = sContext.getExternalFilesDir("SDCard/Android/data/com.zr.car/cache/目录");
        sharedPreferences = sContext.getSharedPreferences("user", Context.MODE_PRIVATE);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例

    }

    public static App getAppContext() {
        return sContext;
    }
}
