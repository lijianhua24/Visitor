package com.ysd.visitor.ui.activity;

import androidx.annotation.NonNull;
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
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
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
    private int page = 1;
    private String search = "";
    private int anInt = 0;
    private AuthorityAdapter authorityAdapter;

    @Override
    protected AuthorityPresenter providePresenter() {
        return new AuthorityPresenter();
    }

    @Override
    protected void initData() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        aurhortiy_recy.setLayoutManager(gridLayoutManager);

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("key",search);
        hashMap.put("page",page+"");
        String s = new Gson().toJson(hashMap);
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
        mPresenter.getVisitorListPresenter(requestBody);
        aurhortiy_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = aurhortiy_edit.getText().toString().trim();
                if (!trim.isEmpty()){
                    search = trim;
                    mWeiboDialogUtils = WeiboDialogUtils.createLoadingDialog(AuthorityActivity.this, "正在搜索....");
                    page = 1;
                    anInt = 0;
                    list.clear();
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("key",search);
                    hashMap.put("page",page+"");
                    String s = new Gson().toJson(hashMap);
                    RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                    mPresenter.getVisitorListPresenter(requestBody);
                }else {
                    search = "";
                    Toast.makeText(AuthorityActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //刷新
        aurhortiy_smart.setEnableRefresh(true);
        aurhortiy_smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                list.clear();
                page = 1;
                anInt = 0;
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("key",search);
                hashMap.put("page",page+"");
                String s = new Gson().toJson(hashMap);
                RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                mPresenter.getVisitorListPresenter(requestBody);
                authorityAdapter.notifyDataSetChanged();
            }
        });

        //加载
        aurhortiy_smart.setEnableLoadMore(true);
        aurhortiy_smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("key",search);
                hashMap.put("page",page+"");
                String s = new Gson().toJson(hashMap);
                RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                mPresenter.getVisitorListPresenter(requestBody);
                authorityAdapter.notifyDataSetChanged();
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
        TextView car_name = findViewById(R.id.car_name);
        car_name.setText("权限管理");
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
        aurhortiy_smart.finishLoadMore();
        aurhortiy_smart.finishRefresh();
        if (data.getCode() == 0){
            List<GetVisitorListBean.DataBean> data1 = data.getData();
            list.addAll(data1);
            if (anInt == 0 ){
                authorityAdapter = new AuthorityAdapter(this, list);
                aurhortiy_recy.setAdapter(authorityAdapter);
                anInt++;
            }

        }else {
            Toast.makeText(this, ""+data.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onVisitorListFailure(Throwable e) {

    }
}
