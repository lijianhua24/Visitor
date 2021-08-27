package com.ysd.visitor.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;
import com.ysd.visitor.R;
import com.ysd.visitor.app.App;
import com.ysd.visitor.base.BaseActivity;
import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.bean.GetAdverListBean;
import com.ysd.visitor.bean.LoginBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.presenter.LoginPresenter;
import com.ysd.visitor.utlis.SecondScreen;
import com.ysd.visitor.utlis.WeiboDialogUtils;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginActivity extends BaseActivity<LoginPresenter> implements HomeContract.getLogin.IView {


    private EditText login_password, login_phone;
    private Button login_complete;
    private Dialog mWeiboDialogUtils;

    @Override
    protected LoginPresenter providePresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initData() {
        login_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = login_password.getText().toString();
                String phone = login_phone.getText().toString();
                if (!password.isEmpty() && !phone.isEmpty()) {
                    mWeiboDialogUtils = WeiboDialogUtils.createLoadingDialog(LoginActivity.this, "正在登录....");
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("loginno", phone);
                    hashMap.put("password", password);
                    String s = new Gson().toJson(hashMap);
                    RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                    mPresenter.getLoginPresenter(requestBody);
                } else {
                    Toast.makeText(LoginActivity.this, "请输入账号或密码", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void initView() {
        login_password = findViewById(R.id.login_password);
        login_phone = findViewById(R.id.login_phone);
        login_complete = findViewById(R.id.login_complete);

    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onLoginSuccess(LoginBean data) {
        mWeiboDialogUtils.dismiss();
        Toast.makeText(this, "" + data.getMsg(), Toast.LENGTH_SHORT).show();
        if (data.getCode() == 0) {
            String data1 = data.getData();
            SharedPreferences.Editor edit = App.sharedPreferences.edit();
            edit.putString("token", data1);
            edit.commit();
            HashMap<String, String> hashMaps = new HashMap<>();
            hashMaps.put("devcode", data1);
            String s1 = new Gson().toJson(hashMaps);
            RequestBody requestBodys = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s1);
            mPresenter.getAdverListPresenter(requestBodys);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    @Override
    public void onLoginFailure(Throwable e) {

    }

    @Override
    public void onAdverListSuccess(GetAdverListBean data) {
        if (data.getCode() == 0) {
            List<GetAdverListBean.DataBean> data1 = data.getData();
            String url = data1.get(0).getUrl();
            App.sharedPreferences.edit().putString("imageuri",url).commit();




        }
    }

    @Override
    public void onAdverListFailure(Throwable e) {

    }
}
