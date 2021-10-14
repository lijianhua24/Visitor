package com.ysd.visitor.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ysd.visitor.R;
import com.ysd.visitor.adapter.CouponAdapter;
import com.ysd.visitor.app.App;
import com.ysd.visitor.base.BaseActivity;
import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.bean.GetUnionCodeListBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.presenter.UnionCodeListPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CouponActivity extends BaseActivity<UnionCodeListPresenter> implements HomeContract.getUnionCodeList.IView {


    private RecyclerView coupon_recy;
    private LinearLayout coupon_linear;
    private SmartRefreshLayout coupon_smart;
    private String token;
    private int page = 1;
    private ArrayList<GetUnionCodeListBean.DataBean> list = new ArrayList<>();
    private CouponAdapter couponAdapter;
    private int anInt = 0;

    @Override
    protected UnionCodeListPresenter providePresenter() {
        return new UnionCodeListPresenter();
    }

    @Override
    protected void initData() {
        token = App.sharedPreferences.getString("token", null);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(CouponActivity.this, 5);
        coupon_recy.setLayoutManager(gridLayoutManager);

        coupon_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CouponActivity.this, AppCouponActivity.class));
            }
        });

        //刷新
        coupon_smart.setEnableRefresh(true);
        coupon_smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                list.clear();
                page = 1;
                anInt = 0;
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("devcode", token);
                hashMap.put("page", page + "");
                String s = new Gson().toJson(hashMap);
                RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                mPresenter.getUnionCodeListPresenter(requestBody);
                couponAdapter.notifyDataSetChanged();
            }
        });

        //加载
        coupon_smart.setEnableLoadMore(true);
        coupon_smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("devcode", token);
                hashMap.put("page", page + "");
                String s = new Gson().toJson(hashMap);
                RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                mPresenter.getUnionCodeListPresenter(requestBody);
                couponAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initView() {
        coupon_recy = findViewById(R.id.coupon_recy);
        coupon_linear = findViewById(R.id.coupon_linear);
        coupon_smart = findViewById(R.id.coupon_smart);
        LinearLayout car_head_linear = findViewById(R.id.car_head_linear);
        TextView car_name = findViewById(R.id.car_name);
        car_name.setText("优惠券固定码");
        car_head_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    public void onUnionCodeListSuccess(GetUnionCodeListBean data) {
        if (data.getCode() == 0) {
            List<GetUnionCodeListBean.DataBean> data1 = data.getData();
            list.addAll(data1);
            if (anInt == 0) {
                couponAdapter = new CouponAdapter(CouponActivity.this, list);
                coupon_recy.setAdapter(couponAdapter);
                anInt++;
            }

        }
    }

    @Override
    public void onUnionCodeListFailure(Throwable e) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("devcode", token);
        hashMap.put("page", page + "");
        String s = new Gson().toJson(hashMap);
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
        mPresenter.getUnionCodeListPresenter(requestBody);
    }
}
