package com.ysd.visitor.presenter;

import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.bean.GetDoorDevsBean;
import com.ysd.visitor.bean.VisitorBanedBean;
import com.ysd.visitor.bean.VisitorMachineBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.model.HomeModel;

import okhttp3.RequestBody;

public class SettingsPresenter extends BasePresenter<HomeContract.getDoorDevs.IView> implements HomeContract.getDoorDevs.IPresenter {

    private HomeModel homeModel;

    @Override
    protected void initModel() {
        homeModel = new HomeModel();
    }

    @Override
    public void getDoorDevsPresenter(RequestBody params) {
        homeModel.getDoorDevsModel(params, new HomeContract.getDoorDevs.IModel.IMDoorDevsCallback() {
            @Override
            public void onDoorDevsSuccess(GetDoorDevsBean data) {
                if (data!=null){
                    getView().onDoorDevsSuccess(data);
                }
            }

            @Override
            public void onDoorDevsFailure(Throwable e) {
                if (isViewAttached()){
                    getView().onDoorDevsFailure(e);
                }
            }
        });
    }

    @Override
    public void getVisitorMachinesPresenter(RequestBody params) {
            homeModel.getVisitorMachinesModel(params, new HomeContract.getDoorDevs.IModel.IMVisitorMachinesCallback() {
                @Override
                public void onVisitorMachinesSuccess(VisitorMachineBean data) {
                    if (data!=null){
                        getView().onVisitorMachinesSuccess(data);
                    }
                }

                @Override
                public void onVisitorMachinesFailure(Throwable e) {
                    if (isViewAttached()){
                        getView().onVisitorMachinesFailure(e);
                    }
                }
            });
    }

    @Override
    public void setVisitorMachinesPresenter(RequestBody params) {
            homeModel.setVisitorMachinesModel(params, new HomeContract.getDoorDevs.IModel.IMsetVisitorMachinesCallback() {
                @Override
                public void onsetVisitorMachinesSuccess(VisitorBanedBean data) {
                    if (data!=null){
                        getView().onsetVisitorMachinesSuccess(data);
                    }
                }

                @Override
                public void onsetVisitorMachinesFailure(Throwable e) {
                    if (isViewAttached()){
                        getView().onsetVisitorMachinesFailure(e);
                    }
                }
            });
    }
}
