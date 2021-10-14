package com.ysd.visitor.presenter;

import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.bean.GetUnionCodeListBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.model.HomeModel;

import okhttp3.RequestBody;

public class UnionCodeListPresenter extends BasePresenter<HomeContract.getUnionCodeList.IView> implements HomeContract.getUnionCodeList.IPresenter {

    private HomeModel homeModel;

    @Override
    protected void initModel() {
        homeModel = new HomeModel();
    }

    @Override
    public void getUnionCodeListPresenter(RequestBody params) {
        homeModel.getUnionCodeListModel(params, new HomeContract.getUnionCodeList.IModel.IMUnionCodeListCallback() {
            @Override
            public void onUnionCodeListSuccess(GetUnionCodeListBean data) {
                if (data!=null){
                    getView().onUnionCodeListSuccess(data);
                }
            }

            @Override
            public void onUnionCodeListFailure(Throwable e) {
                if (isViewAttached()){
                    getView().onUnionCodeListFailure(e);
                }
            }
        });
    }
}
