package com.ysd.visitor.ui.activity;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ysd.visitor.R;
import com.ysd.visitor.adapter.VisitorDetailsAdapter;
import com.ysd.visitor.app.App;
import com.ysd.visitor.base.BaseActivity;
import com.ysd.visitor.bean.GetVisitorRightsBean;
import com.ysd.visitor.bean.VisitorBanedBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.presenter.VisitorDetailsPresenter;
import com.ysd.visitor.utlis.WeiboDialogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class VisitorDetailsActivity extends BaseActivity<VisitorDetailsPresenter> implements HomeContract.getVisitorRights.IView {

    private RecyclerView details_recy;
    private ArrayList<GetVisitorRightsBean.DataBean> list = new ArrayList<>();
    private VisitorDetailsAdapter visitorDetailsAdapter;
    private String token;
    private String id;
    private Dialog mWeiboDialogUtils;

    @Override
    protected VisitorDetailsPresenter providePresenter() {
        return new VisitorDetailsPresenter();
    }

    @Override
    protected void initData() {
        details_recy.setLayoutManager(new LinearLayoutManager(this));
        //添加Android自带的分割线
        //details_recy.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

    }

    @Override
    protected void initView() {
        mWeiboDialogUtils = WeiboDialogUtils.createLoadingDialog(VisitorDetailsActivity.this, "正在加载....");
        token = App.sharedPreferences.getString("token", null);
        id = getIntent().getStringExtra("visitorid");
        details_recy = findViewById(R.id.details_recy);
        LinearLayout car_head_linear = findViewById(R.id.car_head_linear);
        car_head_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("visitorid", id);
        String s = new Gson().toJson(hashMap);
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
        mPresenter.getVisitorRightsPresenter(requestBody);
        TextView car_name = findViewById(R.id.car_name);
        car_name.setText("权限设置");
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_visitor_details;
    }

    @Override
    public void onVisitorRightsSuccess(GetVisitorRightsBean data) {
        mWeiboDialogUtils.dismiss();
        if (data.getCode() == 0) {
            list.clear();
            List<GetVisitorRightsBean.DataBean> data1 = data.getData();
            list.addAll(data1);
            visitorDetailsAdapter = new VisitorDetailsAdapter(this, list);
            details_recy.setAdapter(visitorDetailsAdapter);
            visitorDetailsAdapter.myListenter(new VisitorDetailsAdapter.myClick() {
                @Override
                public void myLoadstatus(int i) {
                    String loadstatus = list.get(i).getLoadstatus();
                    if (loadstatus != null) {
                        if (loadstatus.contains("未下发")) {
                            mWeiboDialogUtils = WeiboDialogUtils.createLoadingDialog(VisitorDetailsActivity.this, "正在下发....");
                            int visitorid = list.get(i).getVisitorid();
                            String devcode = list.get(i).getDevcode();
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("devcode", devcode);
                            hashMap.put("visitorid", visitorid + "");
                            String s = new Gson().toJson(hashMap);
                            RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                            mPresenter.getVisitorReloadPresenter(requestBody);
                        } else {
                            mWeiboDialogUtils = WeiboDialogUtils.createLoadingDialog(VisitorDetailsActivity.this, "正在取消....");
                            int visitorid = list.get(i).getVisitorid();
                            String devcode = list.get(i).getDevcode();
                            Log.d("TAG", "myLoadstatus: " + devcode);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("devcode", devcode);
                            hashMap.put("visitorid", visitorid + "");

                            String s = new Gson().toJson(hashMap);
                            RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                            mPresenter.getVisitorCancelPresenter(requestBody);
                        }
                    }
                }

                @Override
                public void myBlackstatus(int i) {
                    String blackstatus = list.get(i).getBlackstatus();
                    if (blackstatus != null) {
                        if (blackstatus.contains("未拉黑")) {

                            Dialog dialog = new Dialog(VisitorDetailsActivity.this, R.style.DialogTheme);
                            View inflate = View.inflate(VisitorDetailsActivity.this, R.layout.edit_layout, null);
                            EditText edit = inflate.findViewById(R.id.car_xinsi_edit);
                            Button quxiao = inflate.findViewById(R.id.car_xinsi_quxiao);
                            Button queding = inflate.findViewById(R.id.car_xinsi_queding);
                            dialog.setContentView(inflate);
                            Window window = dialog.getWindow();
                            window.setGravity(Gravity.CENTER);
                            window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog.show();
                            quxiao.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            queding.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    String trim = edit.getText().toString().trim();
                                    if (trim.length()!=0) {
                                        mWeiboDialogUtils = WeiboDialogUtils.createLoadingDialog(VisitorDetailsActivity.this, "正在拉黑....");
                                        int visitorid = list.get(i).getVisitorid();
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("devcode", token);
                                        hashMap.put("visitorid", visitorid + "");
                                        hashMap.put("reason", trim);
                                        String s = new Gson().toJson(hashMap);
                                        RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                                        mPresenter.getVisitorBanedPresenter(requestBody);
                                    } else {
                                        Toast.makeText(VisitorDetailsActivity.this, "请填写拉黑原因", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            mWeiboDialogUtils = WeiboDialogUtils.createLoadingDialog(VisitorDetailsActivity.this, "正在取消....");
                            int visitorid = list.get(i).getVisitorid();
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("devcode", token);
                            hashMap.put("visitorid", visitorid + "");
                            String s = new Gson().toJson(hashMap);
                            RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                            mPresenter.getVisitorUnbanedPresenter(requestBody);
                        }
                    }
                }
            });
        } else {
            Toast.makeText(this, "" + data.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onVisitorRightsFailure(Throwable e) {

    }

    @Override
    public void onVisitorBanedSuccess(VisitorBanedBean data) {

        Toast.makeText(VisitorDetailsActivity.this, "" + data.getMsg(), Toast.LENGTH_SHORT).show();
        if (data.getCode() == 0) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("visitorid", id);
            String s = new Gson().toJson(hashMap);
            RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
            mPresenter.getVisitorRightsPresenter(requestBody);
            if (visitorDetailsAdapter != null) {
                visitorDetailsAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onVisitorBanedFailure(Throwable e) {
        Log.d("TAG", "onVisitorBanedFailure: " + e);
    }

    @Override
    public void onVisitorUnbanedSuccess(VisitorBanedBean data) {
        Toast.makeText(VisitorDetailsActivity.this, "" + data.getMsg(), Toast.LENGTH_SHORT).show();
        if (data.getCode() == 0) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("visitorid", id);
            String s = new Gson().toJson(hashMap);
            RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
            mPresenter.getVisitorRightsPresenter(requestBody);
            if (visitorDetailsAdapter != null) {
                visitorDetailsAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onVisitorUnbanedFailure(Throwable e) {
        Log.d("TAG", "onVisitorUnbanedFailure: " + e);
    }

    @Override
    public void onVisitorReloadSuccess(VisitorBanedBean data) {
        Toast.makeText(VisitorDetailsActivity.this, "" + data.getMsg(), Toast.LENGTH_SHORT).show();
        if (data.getCode() == 0) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("visitorid", id);
            String s = new Gson().toJson(hashMap);
            RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
            mPresenter.getVisitorRightsPresenter(requestBody);
            if (visitorDetailsAdapter != null) {
                visitorDetailsAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onVisitorReloadFailure(Throwable e) {
        Log.d("TAG", "onVisitorReloadFailure: " + e);
    }

    @Override
    public void onVisitorCancelSuccess(VisitorBanedBean data) {
        Toast.makeText(VisitorDetailsActivity.this, "" + data.getMsg(), Toast.LENGTH_SHORT).show();
        if (data.getCode() == 0) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("visitorid", id);
            String s = new Gson().toJson(hashMap);
            RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
            mPresenter.getVisitorRightsPresenter(requestBody);
            if (visitorDetailsAdapter != null) {
                visitorDetailsAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onVisitorCancelFailure(Throwable e) {
        Log.d("TAG", "onVisitorCancelFailure: " + e);
    }
}
