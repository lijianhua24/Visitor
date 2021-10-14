package com.ysd.visitor.contract;

import com.ysd.visitor.base.IBaseView;
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

import okhttp3.RequestBody;

public interface HomeContract {
    interface getLogin {
        interface IModel {
            void getLoginModel(RequestBody params, IMLoginCallback callback);

            //model层中的接口回调
            interface IMLoginCallback {
                void onLoginSuccess(LoginBean data);

                void onLoginFailure(Throwable e);
            }

            void getAdverList(RequestBody params, IMAdverListCallback callback);

            //model层中的接口回调
            interface IMAdverListCallback {
                void onAdverListSuccess(GetAdverListBean data);

                void onAdverListFailure(Throwable e);
            }

        }

        //view层  命名必须是IView
        interface IView extends IBaseView {
            void onLoginSuccess(LoginBean data);

            void onLoginFailure(Throwable e);

            void onAdverListSuccess(GetAdverListBean data);

            void onAdverListFailure(Throwable e);

        }

        //presenter层   命名必须是IPresenter
        interface IPresenter {

            void getLoginPresenter(RequestBody params);

            void getAdverListPresenter(RequestBody params);

        }
    }

    interface getVisitorList {
        interface IModel {
            void getVisitorListModel(RequestBody params, IMVisitorListCallback callback);

            //model层中的接口回调
            interface IMVisitorListCallback {
                void onVisitorListSuccess(GetVisitorListBean data);

                void onVisitorListFailure(Throwable e);
            }

        }

        //view层  命名必须是IView
        interface IView extends IBaseView {
            void onVisitorListSuccess(GetVisitorListBean data);

            void onVisitorListFailure(Throwable e);

        }

        //presenter层   命名必须是IPresenter
        interface IPresenter {

            void getVisitorListPresenter(RequestBody params);

        }
    }

    interface getVisitorRights {
        interface IModel {
            void getVisitorRightsModel(RequestBody params, IMVisitorRightsCallback callback);

            //model层中的接口回调
            interface IMVisitorRightsCallback {
                void onVisitorRightsSuccess(GetVisitorRightsBean data);

                void onVisitorRightsFailure(Throwable e);
            }

            void getVisitorBaned(RequestBody params, IMVisitorBanedCallback callback);

            //model层中的接口回调
            interface IMVisitorBanedCallback {
                void onVisitorBanedSuccess(VisitorBanedBean data);

                void onVisitorBanedFailure(Throwable e);
            }

            void getVisitorUnbaned(RequestBody params, IMVisitorUnbanedCallback callback);

            //model层中的接口回调
            interface IMVisitorUnbanedCallback {
                void onVisitorUnbanedSuccess(VisitorBanedBean data);

                void onVisitorUnbanedFailure(Throwable e);
            }

            void getVisitorReload(RequestBody params, IMVisitorReloadCallback callback);

            //model层中的接口回调
            interface IMVisitorReloadCallback {
                void onVisitorReloadSuccess(VisitorBanedBean data);

                void onVisitorReloadFailure(Throwable e);
            }

            void getVisitorCancel(RequestBody params, IMVisitorCancelCallback callback);

            //model层中的接口回调
            interface IMVisitorCancelCallback {
                void onVisitorCancelSuccess(VisitorBanedBean data);

                void onVisitorCancelFailure(Throwable e);
            }

        }

        //view层  命名必须是IView
        interface IView extends IBaseView {
            void onVisitorRightsSuccess(GetVisitorRightsBean data);

            void onVisitorRightsFailure(Throwable e);

            void onVisitorBanedSuccess(VisitorBanedBean data);

            void onVisitorBanedFailure(Throwable e);

            void onVisitorUnbanedSuccess(VisitorBanedBean data);

            void onVisitorUnbanedFailure(Throwable e);

            void onVisitorReloadSuccess(VisitorBanedBean data);

            void onVisitorReloadFailure(Throwable e);

            void onVisitorCancelSuccess(VisitorBanedBean data);

            void onVisitorCancelFailure(Throwable e);

        }

        //presenter层   命名必须是IPresenter
        interface IPresenter {

            void getVisitorRightsPresenter(RequestBody params);

            void getVisitorBanedPresenter(RequestBody params);

            void getVisitorUnbanedPresenter(RequestBody params);

            void getVisitorReloadPresenter(RequestBody params);

            void getVisitorCancelPresenter(RequestBody params);

        }
    }

    interface getModelList {
        interface IModel {
            void getModelListModel(RequestBody params, IMModelListCallback callback);

            //model层中的接口回调
            interface IMModelListCallback {
                void onModelListSuccess(GetModelListBean data);

                void onModelListFailure(Throwable e);
            }

            void getVisitorMachineModel(RequestBody params, IMVisitorMachineCallback callback);

            //model层中的接口回调
            interface IMVisitorMachineCallback {
                void onVisitorMachineSuccess(VisitorMachineBean data);

                void onVisitorMachineFailure(Throwable e);
            }

            void setVisitorMachineModel(RequestBody params, IMsetVisitorMachineCallback callback);

            //model层中的接口回调
            interface IMsetVisitorMachineCallback {
                void onsetVisitorMachineSuccess(VisitorBanedBean data);

                void onsetVisitorMachineFailure(Throwable e);
            }

        }

        //view层  命名必须是IView
        interface IView extends IBaseView {
            void onModelListSuccess(GetModelListBean data);

            void onModelListFailure(Throwable e);

            void onVisitorMachineSuccess(VisitorMachineBean data);

            void onVisitorMachineFailure(Throwable e);

            void onsetVisitorMachineSuccess(VisitorBanedBean data);

            void onsetVisitorMachineFailure(Throwable e);

        }

        //presenter层   命名必须是IPresenter
        interface IPresenter {

            void getModelListPresenter(RequestBody params);

            void getVisitorMachinePresenter(RequestBody params);

            void setVisitorMachinePresenter(RequestBody params);

        }
    }

    interface getDoorDevs {
        interface IModel {
            void getDoorDevsModel(RequestBody params, IMDoorDevsCallback callback);

            //model层中的接口回调
            interface IMDoorDevsCallback {
                void onDoorDevsSuccess(GetDoorDevsBean data);

                void onDoorDevsFailure(Throwable e);
            }

            void getVisitorMachinesModel(RequestBody params, IMVisitorMachinesCallback callback);

            //model层中的接口回调
            interface IMVisitorMachinesCallback {
                void onVisitorMachinesSuccess(VisitorMachineBean data);

                void onVisitorMachinesFailure(Throwable e);
            }

            void setVisitorMachinesModel(RequestBody params, IMsetVisitorMachinesCallback callback);

            //model层中的接口回调
            interface IMsetVisitorMachinesCallback {
                void onsetVisitorMachinesSuccess(VisitorBanedBean data);

                void onsetVisitorMachinesFailure(Throwable e);
            }

        }

        //view层  命名必须是IView
        interface IView extends IBaseView {
            void onDoorDevsSuccess(GetDoorDevsBean data);

            void onDoorDevsFailure(Throwable e);

            void onVisitorMachinesSuccess(VisitorMachineBean data);

            void onVisitorMachinesFailure(Throwable e);

            void onsetVisitorMachinesSuccess(VisitorBanedBean data);

            void onsetVisitorMachinesFailure(Throwable e);

        }

        //presenter层   命名必须是IPresenter
        interface IPresenter {

            void getDoorDevsPresenter(RequestBody params);

            void getVisitorMachinesPresenter(RequestBody params);

            void setVisitorMachinesPresenter(RequestBody params);

        }
    }

    interface getVisitorInOutRecord {
        interface IModel {
            void getVisitorInOutRecordModel(RequestBody params, IMVisitorInOutRecordCallback callback);

            //model层中的接口回调
            interface IMVisitorInOutRecordCallback {
                void onVisitorInOutRecordSuccess(VisitorInOutRecordBean data);

                void onVisitorInOutRecordFailure(Throwable e);
            }

        }

        //view层  命名必须是IView
        interface IView extends IBaseView {
            void onVisitorInOutRecordSuccess(VisitorInOutRecordBean data);

            void onVisitorInOutRecordFailure(Throwable e);

        }

        //presenter层   命名必须是IPresenter
        interface IPresenter {

            void getVisitorInOutRecordPresenter(RequestBody params);

        }
    }

    interface getSubmitVisitorInfo {
        interface IModel {
            void getSubmitVisitorInfoModel(RequestBody params, IMSubmitVisitorInfoCallback callback);

            //model层中的接口回调
            interface IMSubmitVisitorInfoCallback {
                void onSubmitVisitorInfoSuccess(Object data);

                void onSubmitVisitorInfoFailure(Throwable e);
            }

            void getSubmitVisitorInfosModel(RequestBody params, IMSubmitVisitorInfosCallback callback);

            //model层中的接口回调
            interface IMSubmitVisitorInfosCallback {
                void onSubmitVisitorInfosSuccess(Object data);

                void onSubmitVisitorInfosFailure(Throwable e);
            }

            void getCheckedModelListModel(RequestBody params, IMCheckedModelListCallback callback);

            //model层中的接口回调
            interface IMCheckedModelListCallback {
                void onCheckedModelListSuccess(CheckedModelListBean data);

                void onCheckedModelListFailure(Throwable e);
            }

            void getOpenDoorModel(RequestBody params, IMOpenDoorCallback callback);

            //model层中的接口回调
            interface IMOpenDoorCallback {
                void onOpenDoorSuccess(VisitorBanedBean data);

                void onOpenDoorFailure(Throwable e);
            }

        }

        //view层  命名必须是IView
        interface IView extends IBaseView {
            void onSubmitVisitorInfoSuccess(Object data);

            void onSubmitVisitorInfoFailure(Throwable e);

            void onSubmitVisitorInfosSuccess(Object data);

            void onSubmitVisitorInfosFailure(Throwable e);

            void onCheckedModelListSuccess(CheckedModelListBean data);

            void onCheckedModelListFailure(Throwable e);

            void onOpenDoorSuccess(VisitorBanedBean data);

            void onOpenDoorFailure(Throwable e);

        }

        //presenter层   命名必须是IPresenter
        interface IPresenter {

            void getSubmitVisitorInfoPresenter(RequestBody params);

            void getSubmitVisitorInfosPresenter(RequestBody params);

            void getCheckedModelListPresenter(RequestBody params);

            void getOpenDoorPresenter(RequestBody params);

        }
    }

    interface getOPendoorList {
        interface IModel {
            void getOPendoorListModel(RequestBody params, IMOPendoorListCallback callback);

            //model层中的接口回调
            interface IMOPendoorListCallback {
                void onOPendoorListSuccess(GetDoorDevsBean data);

                void onOPendoorListFailure(Throwable e);
            }

            void getOPendoorModel(RequestBody params, IMOPendoorCallback callback);

            //model层中的接口回调
            interface IMOPendoorCallback {
                void onOPendoorSuccess(VisitorBanedBean data);

                void onOPendoorFailure(Throwable e);
            }

        }


        //view层  命名必须是IView
        interface IView extends IBaseView {
            void onOPendoorListSuccess(GetDoorDevsBean data);

            void onOPendoorListFailure(Throwable e);

            void onOPendoorSuccess(VisitorBanedBean data);

            void onOPendoorFailure(Throwable e);

        }

        //presenter层   命名必须是IPresenter
        interface IPresenter {

            void getOPendoorListPresenter(RequestBody params);

            void getOPendoorPresenter(RequestBody params);

        }
    }

    interface getUnionCodeList {
        interface IModel {
            void getUnionCodeListModel(RequestBody params, IMUnionCodeListCallback callback);

            //model层中的接口回调
            interface IMUnionCodeListCallback {
                void onUnionCodeListSuccess(GetUnionCodeListBean data);

                void onUnionCodeListFailure(Throwable e);
            }

        }

        //view层  命名必须是IView
        interface IView extends IBaseView {

            void onUnionCodeListSuccess(GetUnionCodeListBean data);

            void onUnionCodeListFailure(Throwable e);

        }

        //presenter层   命名必须是IPresenter
        interface IPresenter {

            void getUnionCodeListPresenter(RequestBody params);

        }
    }

    interface getUnionCodes {
        interface IModel {
            void getUnionCodesModel(RequestBody params, IMUnionCodesCallback callback);

            //model层中的接口回调
            interface IMUnionCodesCallback {
                void onUnionCodesSuccess(SubmitVisitorInfoBean data);

                void onUnionCodesFailure(Throwable e);
            }

        }

        //view层  命名必须是IView
        interface IView extends IBaseView {

            void onUnionCodesSuccess(SubmitVisitorInfoBean data);

            void onUnionCodesFailure(Throwable e);

        }

        //presenter层   命名必须是IPresenter
        interface IPresenter {

            void getUnionCodesPresenter(RequestBody params);

        }
    }

    interface getUseTicket {
        interface IModel {
            void getUseTicketModel(RequestBody params, IMUseTicketCallback callback);

            //model层中的接口回调
            interface IMUseTicketCallback {
                void onUseTicketSuccess(SubmitVisitorInfoBean data);

                void onUseTicketFailure(Throwable e);
            }

        }

        //view层  命名必须是IView
        interface IView extends IBaseView {

            void onUseTicketSuccess(SubmitVisitorInfoBean data);

            void onUseTicketFailure(Throwable e);

        }

        //presenter层   命名必须是IPresenter
        interface IPresenter {

            void getUseTicketPresenter(RequestBody params);

        }
    }

}
