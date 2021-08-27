package com.ysd.visitor.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ysd.visitor.R;
import com.ysd.visitor.adapter.OpenDoorAdapter;
import com.ysd.visitor.app.App;
import com.ysd.visitor.base.BaseActivity;
import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.bean.CheckedModelListBean;
import com.ysd.visitor.bean.GetDoorDevsBean;
import com.ysd.visitor.bean.VisitorBanedBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.presenter.OpenDoorPresenter;
import com.ysd.visitor.presenter.VisitorDetailsPresenter;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class OpenDoorActivity extends BaseActivity<OpenDoorPresenter> implements  HomeContract.getOPendoorList.IView{


    private RecyclerView opendoor_recy;
    private String token;
    @Override
    protected OpenDoorPresenter providePresenter() {
        return new OpenDoorPresenter();
    }

    @Override
    protected void initData() {
        token = App.sharedPreferences.getString("token", null);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("devcode", token);
        String s = new Gson().toJson(hashMap);
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
        mPresenter.getOPendoorListPresenter(requestBody);
    }

    @Override
    protected void initView() {

        opendoor_recy = findViewById(R.id.opendoor_recy);
        LinearLayout car_head_linear = findViewById(R.id.car_head_linear);
        TextView car_head_finsh = findViewById(R.id.car_name);
        opendoor_recy.setLayoutManager(new LinearLayoutManager(OpenDoorActivity.this));
        car_head_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        car_head_finsh.setText("远程开门");

    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_open_door;
    }


    @Override
    public void onOPendoorListSuccess(GetDoorDevsBean data) {
        if (data.getCode() == 0){
            List<GetDoorDevsBean.DataBean> data1 = data.getData();
            OpenDoorAdapter openDoorAdapter = new OpenDoorAdapter(OpenDoorActivity.this, data1);
            opendoor_recy.setAdapter(openDoorAdapter);
            openDoorAdapter.myListenter(new OpenDoorAdapter.myClick() {
                @Override
                public void myLoadstatus(int i) {
                    String devCode = data1.get(i).getDevCode();
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("devcode", devCode);
                    String s = new Gson().toJson(hashMap);
                    RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                    mPresenter.getOPendoorPresenter(requestBody);
                }
            });
        }
    }

    @Override
    public void onOPendoorListFailure(Throwable e) {

    }

    @Override
    public void onOPendoorSuccess(VisitorBanedBean data) {
        Toast.makeText(this, ""+data.getMsg(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOPendoorFailure(Throwable e) {

    }

}
