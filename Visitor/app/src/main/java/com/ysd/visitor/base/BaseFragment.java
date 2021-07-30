package com.ysd.visitor.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.ysd.visitor.app.App;
import com.ysd.visitor.utlis.NetUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.autosize.AutoSizeConfig;


/**
 * 声明规范和使用规范同BaseActivity
 * {@link BaseActivity}
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IBaseView {
    protected P mPresenter;
    private Unbinder mUnbinder;
    private int one=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(provideLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, inflate);
        AutoSizeConfig.getInstance().setCustomFragment(true);
        return inflate;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = providePresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

        boolean b = hasNetwork();
        initView();
        initData();
        if (b){

        }else {
            one++;
            showNoNetTip();

        }
    }

    protected abstract P providePresenter();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int provideLayoutId();

    /**
     * 释放资源
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    /**
     * 释放资源
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    //判断是否有网络
    public boolean hasNetwork() {
        return NetUtil.hasNetwork(context());
    }

    //无网提醒
    public void showNoNetTip() {
        Toast.makeText(context(), "无网，请检查网络", Toast.LENGTH_SHORT).show();
    }

    /**
     * 为presenter层提供上下文环境  ps： 非必须
     */
    @Override
    public Context context() {
        return getContext() == null ? App.getAppContext() : getContext();
    }

}
