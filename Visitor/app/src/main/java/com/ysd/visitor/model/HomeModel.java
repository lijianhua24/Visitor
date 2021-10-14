package com.ysd.visitor.model;

import com.ysd.visitor.bean.CheckedModelListBean;
import com.ysd.visitor.bean.GetAdverListBean;
import com.ysd.visitor.bean.GetDoorDevsBean;
import com.ysd.visitor.bean.GetModelListBean;
import com.ysd.visitor.bean.GetUnionCodeListBean;
import com.ysd.visitor.bean.GetVisitorListBean;
import com.ysd.visitor.bean.GetVisitorRightsBean;
import com.ysd.visitor.bean.LoginBean;
import com.ysd.visitor.bean.SubmitVisitorInfoBean;
import com.ysd.visitor.bean.VisitorBanedBean;
import com.ysd.visitor.bean.VisitorInOutRecordBean;
import com.ysd.visitor.bean.VisitorMachineBean;
import com.ysd.visitor.contract.HomeContract;
import com.ysd.visitor.utlis.CommonObserver;
import com.ysd.visitor.utlis.CommonSchedulers;
import com.ysd.visitor.utlis.RequestNet;

import okhttp3.RequestBody;

public class HomeModel implements HomeContract.getLogin.IModel, HomeContract.getVisitorList.IModel, HomeContract.getVisitorRights.IModel, HomeContract.getModelList.IModel,
        HomeContract.getDoorDevs.IModel, HomeContract.getVisitorInOutRecord.IModel, HomeContract.getSubmitVisitorInfo.IModel, HomeContract.getOPendoorList.IModel,HomeContract.getUnionCodeList.IModel,
HomeContract.getUnionCodes.IModel,HomeContract.getUseTicket.IModel{
    @Override
    public void getLoginModel(RequestBody params, IMLoginCallback callback) {
        RequestNet.getInstance().create().getLogin(params)
                .compose(CommonSchedulers.<LoginBean>io2main())
                .subscribe(new CommonObserver<LoginBean>() {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        callback.onLoginSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onLoginFailure(e);
                    }
                });
    }

    @Override
    public void getAdverList(RequestBody params, IMAdverListCallback callback) {
        RequestNet.getInstance().create().getAdverList(params)
                .compose(CommonSchedulers.<GetAdverListBean>io2main())
                .subscribe(new CommonObserver<GetAdverListBean>() {
                    @Override
                    public void onNext(GetAdverListBean loginBean) {
                        callback.onAdverListSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onAdverListFailure(e);
                    }
                });
    }


    @Override
    public void getVisitorListModel(RequestBody params, IMVisitorListCallback callback) {
        RequestNet.getInstance().create().getVisitorList(params)
                .compose(CommonSchedulers.<GetVisitorListBean>io2main())
                .subscribe(new CommonObserver<GetVisitorListBean>() {
                    @Override
                    public void onNext(GetVisitorListBean loginBean) {
                        callback.onVisitorListSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onVisitorListFailure(e);
                    }
                });
    }

    @Override
    public void getVisitorRightsModel(RequestBody params, IMVisitorRightsCallback callback) {
        RequestNet.getInstance().create().getVisitorRights(params)
                .compose(CommonSchedulers.<GetVisitorRightsBean>io2main())
                .subscribe(new CommonObserver<GetVisitorRightsBean>() {
                    @Override
                    public void onNext(GetVisitorRightsBean loginBean) {
                        callback.onVisitorRightsSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onVisitorRightsFailure(e);
                    }
                });
    }

    @Override
    public void getVisitorBaned(RequestBody params, IMVisitorBanedCallback callback) {
        RequestNet.getInstance().create().getVisitorBaned(params)
                .compose(CommonSchedulers.<VisitorBanedBean>io2main())
                .subscribe(new CommonObserver<VisitorBanedBean>() {
                    @Override
                    public void onNext(VisitorBanedBean loginBean) {
                        callback.onVisitorBanedSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onVisitorBanedFailure(e);
                    }
                });
    }

    @Override
    public void getVisitorUnbaned(RequestBody params, IMVisitorUnbanedCallback callback) {
        RequestNet.getInstance().create().getVisitorUnbaned(params)
                .compose(CommonSchedulers.<VisitorBanedBean>io2main())
                .subscribe(new CommonObserver<VisitorBanedBean>() {
                    @Override
                    public void onNext(VisitorBanedBean loginBean) {
                        callback.onVisitorUnbanedSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onVisitorUnbanedFailure(e);
                    }
                });
    }

    @Override
    public void getVisitorReload(RequestBody params, IMVisitorReloadCallback callback) {
        RequestNet.getInstance().create().getVisitorReload(params)
                .compose(CommonSchedulers.<VisitorBanedBean>io2main())
                .subscribe(new CommonObserver<VisitorBanedBean>() {
                    @Override
                    public void onNext(VisitorBanedBean loginBean) {
                        callback.onVisitorReloadSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onVisitorReloadFailure(e);
                    }
                });
    }

    @Override
    public void getVisitorCancel(RequestBody params, IMVisitorCancelCallback callback) {
        RequestNet.getInstance().create().getVisitorCancel(params)
                .compose(CommonSchedulers.<VisitorBanedBean>io2main())
                .subscribe(new CommonObserver<VisitorBanedBean>() {
                    @Override
                    public void onNext(VisitorBanedBean loginBean) {
                        callback.onVisitorCancelSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onVisitorCancelFailure(e);
                    }
                });
    }

    @Override
    public void getModelListModel(RequestBody params, IMModelListCallback callback) {
        RequestNet.getInstance().create().getModelList(params)
                .compose(CommonSchedulers.<GetModelListBean>io2main())
                .subscribe(new CommonObserver<GetModelListBean>() {
                    @Override
                    public void onNext(GetModelListBean loginBean) {
                        callback.onModelListSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onModelListFailure(e);
                    }
                });
    }

    @Override
    public void getVisitorMachineModel(RequestBody params, IMVisitorMachineCallback callback) {
        RequestNet.getInstance().create().getVisitorMachine(params)
                .compose(CommonSchedulers.<VisitorMachineBean>io2main())
                .subscribe(new CommonObserver<VisitorMachineBean>() {
                    @Override
                    public void onNext(VisitorMachineBean loginBean) {
                        callback.onVisitorMachineSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onVisitorMachineFailure(e);
                    }
                });
    }

    @Override
    public void setVisitorMachineModel(RequestBody params, IMsetVisitorMachineCallback callback) {
        RequestNet.getInstance().create().setVisitorMachine(params)
                .compose(CommonSchedulers.<VisitorBanedBean>io2main())
                .subscribe(new CommonObserver<VisitorBanedBean>() {
                    @Override
                    public void onNext(VisitorBanedBean loginBean) {
                        callback.onsetVisitorMachineSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onsetVisitorMachineFailure(e);
                    }
                });
    }

    @Override
    public void getDoorDevsModel(RequestBody params, IMDoorDevsCallback callback) {
        RequestNet.getInstance().create().getDoorDevsBean(params)
                .compose(CommonSchedulers.<GetDoorDevsBean>io2main())
                .subscribe(new CommonObserver<GetDoorDevsBean>() {
                    @Override
                    public void onNext(GetDoorDevsBean loginBean) {
                        callback.onDoorDevsSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onDoorDevsFailure(e);
                    }
                });
    }

    @Override
    public void getVisitorMachinesModel(RequestBody params, IMVisitorMachinesCallback callback) {
        RequestNet.getInstance().create().getVisitorMachine(params)
                .compose(CommonSchedulers.<VisitorMachineBean>io2main())
                .subscribe(new CommonObserver<VisitorMachineBean>() {
                    @Override
                    public void onNext(VisitorMachineBean loginBean) {
                        callback.onVisitorMachinesSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onVisitorMachinesFailure(e);
                    }
                });
    }

    @Override
    public void setVisitorMachinesModel(RequestBody params, IMsetVisitorMachinesCallback callback) {
        RequestNet.getInstance().create().setVisitorMachine(params)
                .compose(CommonSchedulers.<VisitorBanedBean>io2main())
                .subscribe(new CommonObserver<VisitorBanedBean>() {
                    @Override
                    public void onNext(VisitorBanedBean loginBean) {
                        callback.onsetVisitorMachinesSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onsetVisitorMachinesFailure(e);
                    }
                });
    }

    @Override
    public void getVisitorInOutRecordModel(RequestBody params, IMVisitorInOutRecordCallback callback) {
        RequestNet.getInstance().create().getVisitorInOutRecord(params)
                .compose(CommonSchedulers.<VisitorInOutRecordBean>io2main())
                .subscribe(new CommonObserver<VisitorInOutRecordBean>() {
                    @Override
                    public void onNext(VisitorInOutRecordBean loginBean) {
                        callback.onVisitorInOutRecordSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onVisitorInOutRecordFailure(e);
                    }
                });
    }

    @Override
    public void getSubmitVisitorInfoModel(RequestBody params, IMSubmitVisitorInfoCallback callback) {
        RequestNet.getInstance().create().getSubmitVisitorInfo(params)
                .compose(CommonSchedulers.<Object>io2main())
                .subscribe(new CommonObserver<Object>() {
                    @Override
                    public void onNext(Object loginBean) {
                        callback.onSubmitVisitorInfoSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onSubmitVisitorInfoFailure(e);
                    }
                });
    }

    @Override
    public void getSubmitVisitorInfosModel(RequestBody params, IMSubmitVisitorInfosCallback callback) {
        RequestNet.getInstance().create().getSubmitVisitorInfos(params)
                .compose(CommonSchedulers.<Object>io2main())
                .subscribe(new CommonObserver<Object>() {
                    @Override
                    public void onNext(Object loginBean) {
                        callback.onSubmitVisitorInfosSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onSubmitVisitorInfosFailure(e);
                    }
                });
    }

    @Override
    public void getCheckedModelListModel(RequestBody params, IMCheckedModelListCallback callback) {
        RequestNet.getInstance().create().getCheckedModelList(params)
                .compose(CommonSchedulers.<CheckedModelListBean>io2main())
                .subscribe(new CommonObserver<CheckedModelListBean>() {
                    @Override
                    public void onNext(CheckedModelListBean loginBean) {
                        callback.onCheckedModelListSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onCheckedModelListFailure(e);
                    }
                });
    }

    @Override
    public void getOpenDoorModel(RequestBody params, IMOpenDoorCallback callback) {
        RequestNet.getInstance().create().getOpenDoor(params)
                .compose(CommonSchedulers.<VisitorBanedBean>io2main())
                .subscribe(new CommonObserver<VisitorBanedBean>() {
                    @Override
                    public void onNext(VisitorBanedBean loginBean) {
                        callback.onOpenDoorSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onOpenDoorFailure(e);
                    }
                });
    }

    @Override
    public void getOPendoorListModel(RequestBody params, IMOPendoorListCallback callback) {
        RequestNet.getInstance().create().getDoorDevsBean(params)
                .compose(CommonSchedulers.<GetDoorDevsBean>io2main())
                .subscribe(new CommonObserver<GetDoorDevsBean>() {
                    @Override
                    public void onNext(GetDoorDevsBean loginBean) {
                        callback.onOPendoorListSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onOPendoorListFailure(e);
                    }
                });
    }

    @Override
    public void getOPendoorModel(RequestBody params, IMOPendoorCallback callback) {
        RequestNet.getInstance().create().getOpenDoor(params)
                .compose(CommonSchedulers.<VisitorBanedBean>io2main())
                .subscribe(new CommonObserver<VisitorBanedBean>() {
                    @Override
                    public void onNext(VisitorBanedBean loginBean) {
                        callback.onOPendoorSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onOPendoorFailure(e);
                    }
                });
    }

    @Override
    public void getUnionCodeListModel(RequestBody params, IMUnionCodeListCallback callback) {
        RequestNet.getInstance().create().getUnionCodeList(params)
                .compose(CommonSchedulers.<GetUnionCodeListBean>io2main())
                .subscribe(new CommonObserver<GetUnionCodeListBean>() {
                    @Override
                    public void onNext(GetUnionCodeListBean loginBean) {
                        callback.onUnionCodeListSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onUnionCodeListFailure(e);
                    }
                });
    }

    @Override
    public void getUnionCodesModel(RequestBody params, IMUnionCodesCallback callback) {
        RequestNet.getInstance().create().getUnionCodes(params)
                .compose(CommonSchedulers.<SubmitVisitorInfoBean>io2main())
                .subscribe(new CommonObserver<SubmitVisitorInfoBean>() {
                    @Override
                    public void onNext(SubmitVisitorInfoBean loginBean) {
                        callback.onUnionCodesSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onUnionCodesFailure(e);
                    }
                });
    }

    @Override
    public void getUseTicketModel(RequestBody params, IMUseTicketCallback callback) {
        RequestNet.getInstance().create().getUseTicket(params)
                .compose(CommonSchedulers.<SubmitVisitorInfoBean>io2main())
                .subscribe(new CommonObserver<SubmitVisitorInfoBean>() {
                    @Override
                    public void onNext(SubmitVisitorInfoBean loginBean) {
                        callback.onUseTicketSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onUseTicketFailure(e);
                    }
                });
    }
}
