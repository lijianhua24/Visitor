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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ysd.visitor.R;
import com.ysd.visitor.adapter.AuthorityAdapter;
import com.ysd.visitor.base.BaseActivity;
import com.ysd.visitor.bean.GetVisitorListBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.presenter.AuthorityPresenter;
import com.ysd.visitor.utlis.WeiboDialogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AuthorityActivity extends BaseActivity<AuthorityPresenter> implements HomeContract.getVisitorList.IView {

    private EditText aurhortiy_edit;
    private ImageView aurhortiy_bt;
    private RecyclerView aurhortiy_recy;
    private SmartRefreshLayout aurhortiy_smart;
    private ArrayList<GetVisitorListBean.DataBean> list = new ArrayList<>();
    private Dialog mWeiboDialogUtils;
    private TextView car_head_name;
    private View car_head_linear;

    @Override
    protected AuthorityPresenter providePresenter() {
        return new AuthorityPresenter();
    }

    @Override
    protected void initData() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        aurhortiy_recy.setLayoutManager(gridLayoutManager);

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("key","");
        String s = new Gson().toJson(hashMap);
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
        mPresenter.getVisitorListPresenter(requestBody);
        aurhortiy_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = aurhortiy_edit.getText().toString().trim();
                if (trim!=null){
                    mWeiboDialogUtils = WeiboDialogUtils.createLoadingDialog(AuthorityActivity.this, "正在搜索....");

                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("key",trim);
                    String s = new Gson().toJson(hashMap);
                    RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                    mPresenter.getVisitorListPresenter(requestBody);
                }
            }
        });
    }

    @Override
    protected void initView() {
        mWeiboDialogUtils = WeiboDialogUtils.createLoadingDialog(AuthorityActivity.this, "正在加载....");
        aurhortiy_edit = findViewById(R.id.aurhortiy_edit);
        aurhortiy_bt = findViewById(R.id.aurhortiy_bt);
        aurhortiy_recy = findViewById(R.id.aurhortiy_recy);
        aurhortiy_smart = findViewById(R.id.aurhortiy_smart);

        car_head_linear = findViewById(R.id.car_head_linear);
        car_head_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_authority;
    }

    @Override
    public void onVisitorListSuccess(GetVisitorListBean data) {
        mWeiboDialogUtils.dismiss();
        list.clear();
        if (data.getCode() == 0){
            List<GetVisitorListBean.DataBean> data1 = data.getData();
            list.addAll(data1);
            AuthorityAdapter authorityAdapter = new AuthorityAdapter(this, list);
            aurhortiy_recy.setAdapter(authorityAdapter);
        }else {
            Toast.makeText(this, ""+data.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onVisitorListFailure(Throwable e) {

    }
}
