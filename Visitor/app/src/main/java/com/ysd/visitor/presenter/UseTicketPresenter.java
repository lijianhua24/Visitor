package com.ysd.visitor.presenter;

import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.bean.SubmitVisitorInfoBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.model.HomeModel;

import okhttp3.RequestBody;

public class UseTicketPresenter extends BasePresenter<HomeContract.getUseTicket.IView> implements HomeContract.getUseTicket.IPresenter {

    private HomeModel homeModel;

    @Override
    protected void initModel() {
        homeModel = new HomeModel();
    }

    @Override
    public void getUseTicketPresenter(RequestBody params) {
        homeModel.getUseTicketModel(params, new HomeContract.getUseTicket.IModel.IMUseTicketCallback() {
            @Override
            public void onUseTicketSuccess(SubmitVisitorInfoBean data) {
                if (data!=null){
                    getView().onUseTicketSuccess(data);
                }
            }

            @Override
            public void onUseTicketFailure(Throwable e) {
                    if (isViewAttached()){
                        getView().onUseTicketFailure(e);
                    }
            }
        });
    }
}
