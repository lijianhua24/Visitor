package com.ysd.visitor.presenter;

import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.bean.GetVisitorRightsBean;
import com.ysd.visitor.bean.VisitorBanedBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.model.HomeModel;

import okhttp3.RequestBody;

public class VisitorDetailsPresenter extends BasePresenter<HomeContract.getVisitorRights.IView> implements HomeContract.getVisitorRights.IPresenter {

    private HomeModel homeModel;

    @Override
    protected void initModel() {
        homeModel = new HomeModel();
    }

    @Override
    public void getVisitorRightsPresenter(RequestBody params) {
        homeModel.getVisitorRightsModel(params, new HomeContract.getVisitorRights.IModel.IMVisitorRightsCallback() {
            @Override
            public void onVisitorRightsSuccess(GetVisitorRightsBean data) {
                if (data != null) {
                    getView().onVisitorRightsSuccess(data);
                }
            }

            @Override
            public void onVisitorRightsFailure(Throwable e) {
                if (isViewAttached()) {
                    getView().onVisitorRightsFailure(e);
                }
            }
        });
    }

    @Override
    public void getVisitorBanedPresenter(RequestBody params) {
        homeModel.getVisitorBaned(params, new HomeContract.getVisitorRights.IModel.IMVisitorBanedCallback() {
            @Override
            public void onVisitorBanedSuccess(VisitorBanedBean data) {
                if (data != null) {
                    getView().onVisitorBanedSuccess(data);
                }
            }

            @Override
            public void onVisitorBanedFailure(Throwable e) {
                if (isViewAttached()) {
                    getView().onVisitorBanedFailure(e);
                }
            }
        });
    }

    @Override
    public void getVisitorUnbanedPresenter(RequestBody params) {
        homeModel.getVisitorUnbaned(params, new HomeContract.getVisitorRights.IModel.IMVisitorUnbanedCallback() {
            @Override
            public void onVisitorUnbanedSuccess(VisitorBanedBean data) {
                if (data != null) {
                    getView().onVisitorUnbanedSuccess(data);
                }
            }

            @Override
            public void onVisitorUnbanedFailure(Throwable e) {
                if (isViewAttached()) {
                    getView().onVisitorUnbanedFailure(e);
                }
            }
        });
    }

    @Override
    public void getVisitorReloadPresenter(RequestBody params) {
        homeModel.getVisitorReload(params, new HomeContract.getVisitorRights.IModel.IMVisitorReloadCallback() {
            @Override
            public void onVisitorReloadSuccess(VisitorBanedBean data) {
                if (data != null) {
                    getView().onVisitorReloadSuccess(data);
                }
            }

            @Override
            public void onVisitorReloadFailure(Throwable e) {
                if (isViewAttached()) {
                    getView().onVisitorReloadFailure(e);
                }
            }
        });
    }

    @Override
    public void getVisitorCancelPresenter(RequestBody params) {
        homeModel.getVisitorCancel(params, new HomeContract.getVisitorRights.IModel.IMVisitorCancelCallback() {
            @Override
            public void onVisitorCancelSuccess(VisitorBanedBean data) {
                if (data != null) {
                    getView().onVisitorCancelSuccess(data);
                }
            }

            @Override
            public void onVisitorCancelFailure(Throwable e) {
                if (isViewAttached()) {
                    getView().onVisitorCancelFailure(e);
                }
            }
        });
    }
}
