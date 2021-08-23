package com.ysd.visitor.presenter;

import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.bean.CheckedModelListBean;
import com.ysd.visitor.bean.SubmitVisitorInfoBean;
import com.ysd.visitor.bean.VisitorBanedBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.model.HomeModel;

import okhttp3.RequestBody;

public class RegisterPresenter extends BasePresenter<HomeContract.getSubmitVisitorInfo.IView> implements HomeContract.getSubmitVisitorInfo.IPresenter {

    private HomeModel homeModel;

    @Override
    protected void initModel() {
        homeModel = new HomeModel();
    }

    @Override
    public void getSubmitVisitorInfoPresenter(RequestBody params) {
        homeModel.getSubmitVisitorInfoModel(params, new HomeContract.getSubmitVisitorInfo.IModel.IMSubmitVisitorInfoCallback() {
            @Override
            public void onSubmitVisitorInfoSuccess(Object data) {
                if (data != null) {
                    getView().onSubmitVisitorInfoSuccess(data);
                }
            }

            @Override
            public void onSubmitVisitorInfoFailure(Throwable e) {
                if (isViewAttached()) {
                    getView().onSubmitVisitorInfoFailure(e);
                }
            }
        });
    }

    @Override
    public void getSubmitVisitorInfosPresenter(RequestBody params) {
        homeModel.getSubmitVisitorInfosModel(params, new HomeContract.getSubmitVisitorInfo.IModel.IMSubmitVisitorInfosCallback() {
            @Override
            public void onSubmitVisitorInfosSuccess(Object data) {
                if (data != null) {
                    getView().onSubmitVisitorInfosSuccess(data);
                }
            }

            @Override
            public void onSubmitVisitorInfosFailure(Throwable e) {
                if (isViewAttached()) {
                    getView().onSubmitVisitorInfosFailure(e);
                }
            }
        });
    }

    @Override
    public void getCheckedModelListPresenter(RequestBody params) {
        homeModel.getCheckedModelListModel(params, new HomeContract.getSubmitVisitorInfo.IModel.IMCheckedModelListCallback() {
            @Override
            public void onCheckedModelListSuccess(CheckedModelListBean data) {
                if (data != null) {
                    getView().onCheckedModelListSuccess(data);
                }
            }

            @Override
            public void onCheckedModelListFailure(Throwable e) {
                if (isViewAttached()) {
                    getView().onCheckedModelListFailure(e);
                }
            }
        });
    }

    @Override
    public void getOpenDoorPresenter(RequestBody params) {
        homeModel.getOpenDoorModel(params, new HomeContract.getSubmitVisitorInfo.IModel.IMOpenDoorCallback() {
            @Override
            public void onOpenDoorSuccess(VisitorBanedBean data) {
                if (data != null) {
                    getView().onOpenDoorSuccess(data);
                }
            }

            @Override
            public void onOpenDoorFailure(Throwable e) {
                if (isViewAttached()) {
                    getView().onOpenDoorFailure(e);
                }
            }
        });
    }
}
