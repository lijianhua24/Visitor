package com.ysd.visitor.presenter;

import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.bean.GetAdverListBean;
import com.ysd.visitor.bean.LoginBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.model.HomeModel;

import okhttp3.RequestBody;

public class LoginPresenter extends BasePresenter<HomeContract.getLogin.IView> implements HomeContract.getLogin.IPresenter {

    private HomeModel homeModel;

    @Override
    protected void initModel() {
        homeModel = new HomeModel();
    }

    @Override
    public void getLoginPresenter(RequestBody params) {
        homeModel.getLoginModel(params, new HomeContract.getLogin.IModel.IMLoginCallback() {
            @Override
            public void onLoginSuccess(LoginBean data) {
                if (data!=null){
                    getView().onLoginSuccess(data);
                }
            }

            @Override
            public void onLoginFailure(Throwable e) {
                    if (isViewAttached()){
                        getView().onLoginFailure(e);
                    }
            }
        });
    }

    @Override
    public void getAdverListPresenter(RequestBody params) {
        homeModel.getAdverList(params, new HomeContract.getLogin.IModel.IMAdverListCallback() {
            @Override
            public void onAdverListSuccess(GetAdverListBean data) {
                if (data!=null){
                    getView().onAdverListSuccess(data);
                }
            }

            @Override
            public void onAdverListFailure(Throwable e) {
                    if (isViewAttached()){
                        getView().onAdverListFailure(e);
                    }
            }
        });
    }
}
