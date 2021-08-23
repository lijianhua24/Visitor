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
import com.ysd.visitor.adapter.SttingsListAdapter;
import com.ysd.visitor.app.App;
import com.ysd.visitor.base.BaseFragment;
import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.bean.GetDoorDevsBean;
import com.ysd.visitor.bean.VisitorBanedBean;
import com.ysd.visitor.bean.VisitorMachineBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.presenter.SettingsPresenter;
import com.ysd.visitor.utlis.WeiboDialogUtils;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.facebook.imagepipeline.nativecode.NativeJpegTranscoder.TAG;


public class SettingsFragment extends BaseFragment<SettingsPresenter> implements HomeContract.getDoorDevs.IView {

    private RecyclerView settings_recy;
    private Button settings_bt;
    private String token;
    private List<GetDoorDevsBean.DataBean> data1;
    private String model;
    private SttingsListAdapter sttingsListAdapter;
    private Dialog mWeiboDialogUtils;
    private String check;
    private String value;
    @Override
    protected SettingsPresenter providePresenter() {
        return new SettingsPresenter();
    }

    @Override
    protected void initData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("devcode", token);
        String s = new Gson().toJson(hashMap);
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
        mPresenter.getDoorDevsPresenter(requestBody);
        HashMap<String, String> hashMaps = new HashMap<>();
        hashMaps.put("devcode", token);
        String s1 = new Gson().toJson(hashMap);
        RequestBody requestBody1 = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s1);
        mPresenter.getVisitorMachinesPresenter(requestBody1);
        settings_recy.setLayoutManager(new LinearLayoutManager(getActivity()));
        settings_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = null;
                for (int j = 0; j < data1.size(); j++) {
                    if (data1.get(j).isCheck()) {
                        value = data1.get(j).getId();
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
                    hashMap.put("devs", check);
                    hashMap.put("model", "");
                    String s = new Gson().toJson(hashMap);
                    RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
                    mPresenter.setVisitorMachinesPresenter(requestBody);
                }else {
                    Toast.makeText(getContext(), "至少选择一种模式", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void initView() {
        mWeiboDialogUtils = WeiboDialogUtils.createLoadingDialog(getActivity(), "正在加载....");
        token = App.sharedPreferences.getString("token", null);
        settings_recy = getActivity().findViewById(R.id.settings_recy);
        settings_bt = getActivity().findViewById(R.id.settings_bt);
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override
    public void onDoorDevsSuccess(GetDoorDevsBean data) {
        mWeiboDialogUtils.dismiss();
        if (data.getCode() == 0){
            data1 = data.getData();
            if (model != null && data1 != null) {
                String[] split = model.split(",");
                for (int i = 0; i < data1.size(); i++) {
                    for (int j = 0; j < split.length; j++) {
                        if (data1.get(i).getId().contains(split[j])) {
                            data1.get(i).setCheck(true);
                        }
                    }
                }
            }
            sttingsListAdapter = new SttingsListAdapter(getActivity(), data1, model);
            settings_recy.setAdapter(sttingsListAdapter);
            }
        }


    @Override
    public void onDoorDevsFailure(Throwable e) {

    }

    @Override
    public void onVisitorMachinesSuccess(VisitorMachineBean data) {
        if (data.getCode() == 0){
            model = data.getData().getDevs();
        }
    }

    @Override
    public void onVisitorMachinesFailure(Throwable e) {

    }

    @Override
    public void onsetVisitorMachinesSuccess(VisitorBanedBean data) {
        if (data.getCode() == 0){
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("devcode", token);
            String s = new Gson().toJson(hashMap);
            RequestBody requestBody = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s);
            mPresenter.getDoorDevsPresenter(requestBody);
            HashMap<String, String> hashMaps = new HashMap<>();
            hashMaps.put("devcode", token);
            String s1 = new Gson().toJson(hashMap);
            RequestBody requestBody1 = RequestBody.create(MediaType.get("application/json;charset=UTF-8"), s1);
            mPresenter.getVisitorMachinesPresenter(requestBody1);
        }
    }

    @Override
    public void onsetVisitorMachinesFailure(Throwable e) {

    }
}
