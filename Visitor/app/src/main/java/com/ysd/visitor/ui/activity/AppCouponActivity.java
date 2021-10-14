package com.ysd.visitor.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ysd.visitor.R;
import com.ysd.visitor.app.App;
import com.ysd.visitor.base.BaseActivity;
import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.bean.SubmitVisitorInfoBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.presenter.UnionCodesPresenter;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AppCouponActivity extends BaseActivity<UnionCodesPresenter> implements HomeContract.getUnionCodes.IView {


    private Button add_coupon_finsh;
    private EditText add_coupon_price;
    private TextView add_coupon_sum, add_coupon_money, add_coupon_time, add_coupon_three, add_coupon_six, add_coupon_nine;
    private String type = "4";
    private String token;
    private String time = "30";

    @Override
    protected UnionCodesPresenter providePresenter() {
        return new UnionCodesPresenter();
    }

    @Override
    protected void initData() {
        add_coupon_price.setEnabled(false);
        add_coupon_price.setHint("无");
        add_coupon_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "4";
                add_coupon_price.setEnabled(false);
                add_coupon_price.setHint("无");
                add_coupon_sum.setBackgroundResource(R.drawable.bg_shape);
                add_coupon_sum.setTextColor(AppCouponActivity.this.getResources().getColor(R.color.colorBai));
                add_coupon_money.setBackgroundResource(R.drawable.time_shape);
                add_coupon_money.setTextColor(AppCouponActivity.this.getResources().getColor(R.color.colorBlack));
                add_coupon_time.setBackgroundResource(R.drawable.time_shape);
                add_coupon_time.setTextColor(AppCouponActivity.this.getResources().getColor(R.color.colorBlack));
            }
        });

        add_coupon_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "5";
                add_coupon_price.setEnabled(true);
                add_coupon_price.setHint("请输入金额");
                add_coupon_money.setBackgroundResource(R.drawable.bg_shape);
                add_coupon_money.setTextColor(AppCouponActivity.this.getResources().getColor(R.color.colorBai));
                add_coupon_sum.setBackgroundResource(R.drawable.time_shape);
                add_coupon_sum.setTextColor(AppCouponActivity.this.getResources().getColor(R.color.colorBlack));
                add_coupon_time.setBackgroundResource(R.drawable.time_shape);
                add_coupon_time.setTextColor(AppCouponActivity.this.getResources().getColor(R.color.colorBlack));
            }
        });
        add_coupon_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "3";
                add_coupon_price.setEnabled(true);
                add_coupon_price.setHint("请输入时长");
                add_coupon_time.setBackgroundResource(R.drawable.bg_shape);
                add_coupon_time.setTextColor(AppCouponActivity.this.getResources().getColor(R.color.colorBai));
                add_coupon_sum.setBackgroundResource(R.drawable.time_shape);
                add_coupon_sum.setTextColor(AppCouponActivity.this.getResources().getColor(R.color.colorBlack));
                add_coupon_money.setBackgroundResource(R.drawable.time_shape);
                add_coupon_money.setTextColor(AppCouponActivity.this.getResources().getColor(R.color.colorBlack));
            }
        });

        add_coupon_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = "30";
                add_coupon_three.setBackgroundResource(R.drawable.bg_shape);
                add_coupon_three.setTextColor(AppCouponActivity.this.getResources().getColor(R.color.colorBai));
                add_coupon_six.setBackgroundResource(R.drawable.time_shape);
                add_coupon_six.setTextColor(AppCouponActivity.this.getResources().getColor(R.color.colorBlack));
                add_coupon_nine.setBackgroundResource(R.drawable.time_shape);
                add_coupon_nine.setTextColor(AppCouponActivity.this.getResources().getColor(R.color.colorBlack));
            }
        });

        add_coupon_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = "60";
                add_coupon_six.setBackgroundResource(R.drawable.bg_shape);
                add_coupon_six.setTextColor(AppCouponActivity.this.getResources().getColor(R.color.colorBai));
                add_coupon_three.setBackgroundResource(R.drawable.time_shape);
                add_coupon_three.setTextColor(AppCouponActivity.this.getResources().getColor(R.color.colorBlack));
                add_coupon_nine.setBackgroundResource(R.drawable.time_shape);
                add_coupon_nine.setTextColor(AppCouponActivity.this.getResources().getColor(R.color.colorBlack));
            }
        });

        add_coupon_nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = "90";
                add_coupon_nine.setBackgroundResource(R.drawable.bg_shape);
                add_coupon_nine.setTextColor(AppCouponActivity.this.getResources().getColor(R.color.colorBai));
                add_coupon_three.setBackgroundResource(R.drawable.time_shape);
                add_coupon_three.setTextColor(AppCouponActivity.this.getResources().getColor(R.color.colorBlack));
                add_coupon_six.setBackgroundResource(R.drawable.time_shape);
                add_coupon_six.setTextColor(AppCouponActivity.this.getResources().getColor(R.color.colorBlack));
            }
        });

        add_coupon_finsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.contains("4")) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("devcode", token);
                    hashMap.put("type", type);
                    hashMap.put("reduce", "");
                    hashMap.put("expireTime", time);
                    String s = new Gson().toJson(hashMap);
                    RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                    mPresenter.getUnionCodesPresenter(requestBody);

                } else {
                    String price = add_coupon_price.getText().toString();
                    if (!price.isEmpty()) {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("devcode", token);
                        hashMap.put("type", type);
                        hashMap.put("reduce", price);
                        hashMap.put("expireTime", time);
                        String s = new Gson().toJson(hashMap);
                        RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                        mPresenter.getUnionCodesPresenter(requestBody);
                    } else {
                        Toast.makeText(AppCouponActivity.this, "请输入额度", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    protected void initView() {
        token = App.sharedPreferences.getString("token", null);
        add_coupon_finsh = findViewById(R.id.add_coupon_finsh);
        add_coupon_sum = findViewById(R.id.add_coupon_sum);
        add_coupon_money = findViewById(R.id.add_coupon_money);
        add_coupon_time = findViewById(R.id.add_coupon_time);
        add_coupon_three = findViewById(R.id.add_coupon_three);
        add_coupon_six = findViewById(R.id.add_coupon_six);
        add_coupon_nine = findViewById(R.id.add_coupon_nine);
        add_coupon_price = findViewById(R.id.add_coupon_price);
        LinearLayout car_head_linear = findViewById(R.id.car_head_linear);
        TextView car_name = findViewById(R.id.car_name);
        car_name.setText("添加优惠券");
        car_head_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_app_coupon;
    }

    @Override
    public void onUnionCodesSuccess(SubmitVisitorInfoBean data) {
        Toast.makeText(this, "" + data.getMsg(), Toast.LENGTH_SHORT).show();
        Log.d("TAG", "onUnionCodesSuccess: "+data.getMsg());
        if (data.getCode() == 0) {
            finish();
        }
    }

    @Override
    public void onUnionCodesFailure(Throwable e) {

    }
}
