package com.ysd.visitor.presenter;

import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.bean.SubmitVisitorInfoBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.model.HomeModel;

import okhttp3.RequestBody;

public class UnionCodesPresenter extends BasePresenter<HomeContract.getUnionCodes.IView> implements HomeContract.getUnionCodes.IPresenter {

    private HomeModel homeModel;

    @Override
    protected void initModel() {
        homeModel = new HomeModel();
    }

    @Override
    public void getUnionCodesPresenter(RequestBody params) {
        homeModel.getUnionCodesModel(params, new HomeContract.getUnionCodes.IModel.IMUnionCodesCallback() {
            @Override
            public void onUnionCodesSuccess(SubmitVisitorInfoBean data) {
                if (data!=null){
                    getView().onUnionCodesSuccess(data);
                }
            }

            @Override
            public void onUnionCodesFailure(Throwable e) {
                if (isViewAttached()){
                    getView().onUnionCodesFailure(e);
                }
            }
        });
    }
}
