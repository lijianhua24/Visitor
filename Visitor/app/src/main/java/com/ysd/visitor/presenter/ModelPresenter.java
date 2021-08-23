package com.ysd.visitor.presenter;

import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.bean.GetModelListBean;
import com.ysd.visitor.bean.VisitorBanedBean;
import com.ysd.visitor.bean.VisitorMachineBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.model.HomeModel;

import okhttp3.RequestBody;

public class ModelPresenter extends BasePresenter<HomeContract.getModelList.IView> implements HomeContract.getModelList.IPresenter {

    private HomeModel homeModel;

    @Override
    protected void initModel() {
        homeModel = new HomeModel();
    }

    @Override
    public void getModelListPresenter(RequestBody params) {
        homeModel.getModelListModel(params, new HomeContract.getModelList.IModel.IMModelListCallback() {
            @Override
            public void onModelListSuccess(GetModelListBean data) {
                if (data != null) {
                    getView().onModelListSuccess(data);
                }
            }

            @Override
            public void onModelListFailure(Throwable e) {
                if (isViewAttached()) {
                    getView().onModelListFailure(e);
                }
            }
        });
    }

    @Override
    public void getVisitorMachinePresenter(RequestBody params) {
        homeModel.getVisitorMachineModel(params, new HomeContract.getModelList.IModel.IMVisitorMachineCallback() {
            @Override
            public void onVisitorMachineSuccess(VisitorMachineBean data) {
                if (data != null) {
                    getView().onVisitorMachineSuccess(data);
                }
            }

            @Override
            public void onVisitorMachineFailure(Throwable e) {
                if (isViewAttached()) {
                    getView().onVisitorMachineFailure(e);
                }
            }
        });
    }

    @Override
    public void setVisitorMachinePresenter(RequestBody params) {
        homeModel.setVisitorMachineModel(params, new HomeContract.getModelList.IModel.IMsetVisitorMachineCallback() {
            @Override
            public void onsetVisitorMachineSuccess(VisitorBanedBean data) {
                if (data!=null){
                    getView().onsetVisitorMachineSuccess(data);
                }
            }

            @Override
            public void onsetVisitorMachineFailure(Throwable e) {
                if (isViewAttached()){
                    getView().onsetVisitorMachineFailure(e);
                }
            }
        });
    }
}
