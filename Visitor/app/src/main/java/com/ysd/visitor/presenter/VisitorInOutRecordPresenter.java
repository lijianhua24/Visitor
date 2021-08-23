package com.ysd.visitor.presenter;

import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.bean.VisitorInOutRecordBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.model.HomeModel;

import okhttp3.RequestBody;

public class VisitorInOutRecordPresenter extends BasePresenter<HomeContract.getVisitorInOutRecord.IView> implements HomeContract.getVisitorInOutRecord.IPresenter {

    private HomeModel homeModel;

    @Override
    protected void initModel() {
        homeModel = new HomeModel();
    }

    @Override
    public void getVisitorInOutRecordPresenter(RequestBody params) {
        homeModel.getVisitorInOutRecordModel(params, new HomeContract.getVisitorInOutRecord.IModel.IMVisitorInOutRecordCallback() {
            @Override
            public void onVisitorInOutRecordSuccess(VisitorInOutRecordBean data) {
                if (data != null) {
                    getView().onVisitorInOutRecordSuccess(data);
                }
            }

            @Override
            public void onVisitorInOutRecordFailure(Throwable e) {
                if (isViewAttached()) {
                    getView().onVisitorInOutRecordFailure(e);
                }
            }
        });
    }
}
