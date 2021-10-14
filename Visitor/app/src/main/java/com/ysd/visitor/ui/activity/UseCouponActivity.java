package com.ysd.visitor.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pxy.LicensePlateView;
import com.ysd.visitor.R;
import com.ysd.visitor.app.App;
import com.ysd.visitor.base.BaseActivity;
import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.bean.SubmitVisitorInfoBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.presenter.UseTicketPresenter;
import com.ysd.visitor.utlis.util.KeyboardUtil;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UseCouponActivity extends BaseActivity<UseTicketPresenter> implements HomeContract.getUseTicket.IView {

    private Button usecoupon_bt;
    private LinearLayout usecoupon_linear;
    private EditText usecoupon_remark, usecoupon_car;
    private boolean check = false;
    private String unionCode;
    private String token;
    private KeyboardUtil keyboardUtil;

    @Override
    protected UseTicketPresenter providePresenter() {
        return new UseTicketPresenter();
    }

    @Override
    protected void initData() {

        usecoupon_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (keyboardUtil!=null){
                        keyboardUtil.hideKeyboard();
                    }
            }
        });

        usecoupon_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String remark = usecoupon_remark.getText().toString();
                String car = usecoupon_car.getText().toString();
                if (!remark.isEmpty()) {
                    if (!car.isEmpty()) {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("devcode", token);
                        hashMap.put("uinoncode", unionCode);
                        hashMap.put("carno", car);
                        hashMap.put("content", remark);
                        String s = new Gson().toJson(hashMap);
                        RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                        mPresenter.getUseTicketPresenter(requestBody);
                    } else {
                        Toast.makeText(UseCouponActivity.this, "请输入车牌号", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UseCouponActivity.this, "请输入使用说明", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView() {
        token = App.sharedPreferences.getString("token", null);
        usecoupon_remark = findViewById(R.id.usecoupon_remark);
        usecoupon_car = findViewById(R.id.usecoupon_car);
        usecoupon_bt = findViewById(R.id.usecoupon_bt);
        usecoupon_linear = findViewById(R.id.usecoupon_linear);
        LinearLayout car_head_linear = findViewById(R.id.car_head_linear);
        TextView car_name = findViewById(R.id.car_name);
        car_name.setText("车辆减免");
        usecoupon_remark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                }
            }
        });
        usecoupon_car.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (keyboardUtil == null) {
                        keyboardUtil = new KeyboardUtil(UseCouponActivity.this, usecoupon_car);
                        keyboardUtil.hideSoftInputMethod();
                        keyboardUtil.showKeyboard();
                    } else {
                        keyboardUtil.showKeyboard();
                    }
                } else {
                    keyboardUtil.hideKeyboard();
                }
            }
        });
        car_head_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        unionCode = getIntent().getStringExtra("unionCode");

        usecoupon_car.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (keyboardUtil == null) {
                    keyboardUtil = new KeyboardUtil(UseCouponActivity.this, usecoupon_car);
                    keyboardUtil.hideSoftInputMethod();
                    keyboardUtil.showKeyboard();
                } else {
                    keyboardUtil.showKeyboard();
                }
                return false;
            }
        });

        usecoupon_car.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("字符变换后", "afterTextChanged");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("字符变换前", s + "-" + start + "-" + count + "-" + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("字符变换中", s + "-" + "-" + start + "-" + before + "-" + count);
            }
        });
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_use_coupon;
    }


    @Override
    public void onUseTicketSuccess(SubmitVisitorInfoBean data) {
        Toast.makeText(this, "" + data.getMsg(), Toast.LENGTH_SHORT).show();
        if (data.getCode() == 0) {
            finish();
        }
    }

    @Override
    public void onUseTicketFailure(Throwable e) {

    }
}


