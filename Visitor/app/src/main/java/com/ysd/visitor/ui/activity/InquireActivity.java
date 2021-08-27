package com.ysd.visitor.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ysd.visitor.R;
import com.ysd.visitor.adapter.InquireAdapter;
import com.ysd.visitor.base.BaseActivity;
import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.bean.VisitorInOutRecordBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.presenter.VisitorInOutRecordPresenter;
import com.ysd.visitor.utlis.WeiboDialogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class InquireActivity extends BaseActivity<VisitorInOutRecordPresenter> implements HomeContract.getVisitorInOutRecord.IView {


    private SmartRefreshLayout inquire_smart;
    private RecyclerView inquire_recy;
    private ArrayList<VisitorInOutRecordBean.DataBean> list = new ArrayList<>();
    private Dialog mWeiboDialogUtils;
    private EditText inquire_edit;
    private ImageView inquire_bt;

    @Override
    protected VisitorInOutRecordPresenter providePresenter() {
        return new VisitorInOutRecordPresenter();
    }

    @Override
    protected void initData() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        inquire_recy.setLayoutManager(gridLayoutManager);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("key", "");
        String s = new Gson().toJson(hashMap);
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
        mPresenter.getVisitorInOutRecordPresenter(requestBody);
        inquire_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = inquire_edit.getText().toString().trim();
                if (trim.length() != 0) {
                    mWeiboDialogUtils = WeiboDialogUtils.createLoadingDialog(InquireActivity.this, "正在搜索....");
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("key", trim);
                    String s = new Gson().toJson(hashMap);
                    RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                    mPresenter.getVisitorInOutRecordPresenter(requestBody);
                } else {
                    Toast.makeText(InquireActivity.this, "请填写搜索内容", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void initView() {
        mWeiboDialogUtils = WeiboDialogUtils.createLoadingDialog(InquireActivity.this, "正在加载....");
        inquire_smart = findViewById(R.id.inquire_smart);
        inquire_recy = findViewById(R.id.inquire_recy);
        inquire_edit = findViewById(R.id.inquire_edit);
        inquire_bt = findViewById(R.id.inquire_bt);
        LinearLayout car_head_linear = findViewById(R.id.car_head_linear);
        TextView car_name = findViewById(R.id.car_name);
        car_name.setText("记录查询");
        car_head_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_inquire;
    }

    @Override
    public void onVisitorInOutRecordSuccess(VisitorInOutRecordBean data) {
        mWeiboDialogUtils.dismiss();
        list.clear();
        if (data.getCode() == 0) {
            List<VisitorInOutRecordBean.DataBean> data1 = data.getData();
            list.addAll(data1);
            inquire_recy.setAdapter(new InquireAdapter(this, list));
        }
    }

    @Override
    public void onVisitorInOutRecordFailure(Throwable e) {

    }
}
