package com.ysd.visitor.presenter;

import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.bean.GetDoorDevsBean;
import com.ysd.visitor.bean.VisitorBanedBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.model.HomeModel;

import okhttp3.RequestBody;

public class OpenDoorPresenter extends BasePresenter<HomeContract.getOPendoorList.IView> implements HomeContract.getOPendoorList.IPresenter {

    private HomeModel homeModel;

    @Override
    protected void initModel() {
        homeModel = new HomeModel();
    }

    @Override
    public void getOPendoorListPresenter(RequestBody params) {
        homeModel.getOPendoorListModel(params, new HomeContract.getOPendoorList.IModel.IMOPendoorListCallback() {
            @Override
            public void onOPendoorListSuccess(GetDoorDevsBean data) {
                if (data != null) {
                    getView().onOPendoorListSuccess(data);
                }
            }

            @Override
            public void onOPendoorListFailure(Throwable e) {
                if (isViewAttached()) {
                    getView().onOPendoorListFailure(e);
                }
            }
        });
    }

    @Override
    public void getOPendoorPresenter(RequestBody params) {
        homeModel.getOPendoorModel(params, new HomeContract.getOPendoorList.IModel.IMOPendoorCallback() {
            @Override
            public void onOPendoorSuccess(VisitorBanedBean data) {
                if (data != null) {
                    getView().onOPendoorSuccess(data);
                }
            }

            @Override
            public void onOPendoorFailure(Throwable e) {
                if (isViewAttached()) {
                    getView().onOPendoorFailure(e);
                }
            }
        });
    }
}
