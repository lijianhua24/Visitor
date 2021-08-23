package com.ysd.visitor.presenter;

import com.ysd.visitor.base.BasePresenter;
import com.ysd.visitor.bean.GetVisitorListBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.model.HomeModel;

import okhttp3.RequestBody;

public class AuthorityPresenter extends BasePresenter<HomeContract.getVisitorList.IView> implements HomeContract.getVisitorList.IPresenter {

    private HomeModel homeModel;

    @Override
    protected void initModel() {
        homeModel = new HomeModel();
    }

    @Override
    public void getVisitorListPresenter(RequestBody params) {
        homeModel.getVisitorListModel(params, new HomeContract.getVisitorList.IModel.IMVisitorListCallback() {
            @Override
            public void onVisitorListSuccess(GetVisitorListBean data) {
                if (data!=null){
                    getView().onVisitorListSuccess(data);
                }
            }

            @Override
            public void onVisitorListFailure(Throwable e) {
                    if (isViewAttached()){
                        getView().onVisitorListFailure(e);
                    }
            }
        });
    }
}
