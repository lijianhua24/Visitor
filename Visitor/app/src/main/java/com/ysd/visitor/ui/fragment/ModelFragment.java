package com.ysd.visitor.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ysd.visitor.R;
import com.ysd.visitor.adapter.ModelListAdapter;
import com.ysd.visitor.app.App;
import com.ysd.visitor.base.BaseFragment;
import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.bean.GetModelListBean;
import com.ysd.visitor.bean.VisitorBanedBean;
import com.ysd.visitor.bean.VisitorMachineBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.presenter.ModelPresenter;
import com.ysd.visitor.utlis.WeiboDialogUtils;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ModelFragment extends BaseFragment<ModelPresenter> implements HomeContract.getModelList.IView {

    private RecyclerView model_recy;
    private Button model_bt;
    private String token;
    private List<GetModelListBean.DataBean> data1;
    private VisitorMachineBean.DataBean data11;
    private String model;
    private ModelListAdapter modelListAdapter;
    private String check;
    private String value;
    private Dialog mWeiboDialogUtils;

    @Override
    protected ModelPresenter providePresenter() {
        return new ModelPresenter();
    }

    @Override
    protected void initData() {
        mWeiboDialogUtils = WeiboDialogUtils.createLoadingDialog(getActivity(), "正在加载....");
        model_recy.setLayoutManager(new LinearLayoutManager(getActivity()));
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("devcode", token);
        String s = new Gson().toJson(hashMap);
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
        mPresenter.getModelListPresenter(requestBody);
        HashMap<String, String> hashMaps = new HashMap<>();
        hashMaps.put("devcode", token);
        String s1 = new Gson().toJson(hashMap);
        RequestBody requestBody1 = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s1);
        mPresenter.getVisitorMachinePresenter(requestBody1);
        model_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = null;
                for (int j = 0; j < data1.size(); j++) {
                    if (data1.get(j).isCheck()) {
                        value = data1.get(j).getValue();
                        if (check != null) {
                            check = check + value + ",";
                        } else {
                            check = value + ",";
                        }
                    }
                }
                if (check!=null){
                    mWeiboDialogUtils = WeiboDialogUtils.createLoadingDialog(getActivity(), "正在加载....");
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("devcode", token);
                    hashMap.put("devs", "");
                    hashMap.put("model", check);
                    String s = new Gson().toJson(hashMap);
                    RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                    mPresenter.setVisitorMachinePresenter(requestBody);
                }else {
                    Toast.makeText(getContext(), "至少选择一种模式", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void initView() {
        token = App.sharedPreferences.getString("token", null);
        model_recy = getActivity().findViewById(R.id.model_recy);
        model_bt = getActivity().findViewById(R.id.model_bt);

    }

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_model;
    }

    @Override
    public void onModelListSuccess(GetModelListBean data) {
        mWeiboDialogUtils.dismiss();
        if (data.getCode() == 0) {
            data1 = data.getData();
            if (model != null && data1 != null) {
                String[] split = model.split(",");
                for (int i = 0; i < data1.size(); i++) {
                    for (int j = 0; j < split.length; j++) {
                        if (data1.get(i).getValue().contains(split[j])) {
                            data1.get(i).setCheck(true);
                        }
                    }
                }
                modelListAdapter = new ModelListAdapter(getActivity(), data1, model);
                model_recy.setAdapter(modelListAdapter);
            }
        }
    }

    @Override
    public void onModelListFailure(Throwable e) {

    }

    @Override
    public void onVisitorMachineSuccess(VisitorMachineBean data) {
        if (data.getCode() == 0) {

            model = data.getData().getModel();
            Log.d("TAG", "onVisitorMachineSuccess: " + model);
            if (model != null && data1 != null) {
                String[] split = model.split(",");
                for (int i = 0; i < data1.size(); i++) {
                    for (int j = 0; j < split.length; j++) {
                        if (data1.get(i).getValue().contains(split[j])) {
                            data1.get(i).setCheck(true);
                        }
                    }
                }
                modelListAdapter = new ModelListAdapter(getActivity(), data1, model);
                model_recy.setAdapter(modelListAdapter);

            }
        }
    }

    @Override
    public void onVisitorMachineFailure(Throwable e) {

    }

    @Override
    public void onsetVisitorMachineSuccess(VisitorBanedBean data) {
        Toast.makeText(getActivity(), ""+data.getMsg(), Toast.LENGTH_SHORT).show();
        if (data!=null){
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("devcode", token);
            String s = new Gson().toJson(hashMap);
            RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
            mPresenter.getModelListPresenter(requestBody);
            HashMap<String, String> hashMaps = new HashMap<>();
            hashMaps.put("devcode", token);
            String s1 = new Gson().toJson(hashMap);
            RequestBody requestBody1 = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s1);
            mPresenter.getVisitorMachinePresenter(requestBody1);
        }
    }

    @Override
    public void onsetVisitorMachineFailure(Throwable e) {

    }
}
