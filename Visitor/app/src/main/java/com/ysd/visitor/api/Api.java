package com.ysd.visitor.api;


import com.ysd.visitor.bean.CheckedModelListBean;
import com.ysd.visitor.bean.GetAdverListBean;
import com.ysd.visitor.bean.GetDoorDevsBean;
import com.ysd.visitor.bean.GetModelListBean;
import com.ysd.visitor.bean.GetUnionCodeListBean;
import com.ysd.visitor.bean.GetVisitorListBean;
import com.ysd.visitor.bean.GetVisitorRightsBean;
import com.ysd.visitor.bean.ListBean;
import com.ysd.visitor.bean.LoginBean;
import com.ysd.visitor.bean.SubmitVisitorInfoBean;
import com.ysd.visitor.bean.VisitorBanedBean;
import com.ysd.visitor.bean.VisitorInOutRecordBean;
import com.ysd.visitor.bean.VisitorMachineBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {
    //登录
    @POST("Login")
    Observable<LoginBean> getLogin(@Body RequestBody params);

    //查询访客列表
    @POST("GetVisitorList")
    Observable<GetVisitorListBean> getVisitorList(@Body RequestBody params);

    //访客详情
    @POST("GetVisitorRights")
    Observable<GetVisitorRightsBean> getVisitorRights(@Body RequestBody params);

    //拉黑访客
    @POST("VisitorBaned")
    Observable<VisitorBanedBean> getVisitorBaned(@Body RequestBody params);

    //解除拉黑
    @POST("VisitorUnbaned")
    Observable<VisitorBanedBean> getVisitorUnbaned(@Body RequestBody params);

    //下发权限
    @POST("VisitorReload")
    Observable<VisitorBanedBean> getVisitorReload(@Body RequestBody params);

    //取消权限
    @POST("VisitorCancel")
    Observable<VisitorBanedBean> getVisitorCancel(@Body RequestBody params);

    //查询模式
    @POST("GetModelList")
    Observable<GetModelListBean> getModelList(@Body RequestBody params);

    //查询参数设置
    @POST("GetVisitorMachine")
    Observable<VisitorMachineBean> getVisitorMachine(@Body RequestBody params);

    //参数设置
    @POST("SetVisitorMachine")
    Observable<VisitorBanedBean> setVisitorMachine(@Body RequestBody params);

    //门禁列表
    @POST("GetDoorDevs")
    Observable<GetDoorDevsBean> getDoorDevsBean(@Body RequestBody params);

    //门禁列表
    @POST("GetVisitorInOutRecord")
    Observable<VisitorInOutRecordBean> getVisitorInOutRecord(@Body RequestBody params);

    //门禁列表
    @POST("SubmitVisitorInfo")
    Observable<SubmitVisitorInfoBean> getSubmitVisitorInfo(@Body RequestBody params);

    //门禁列表
    @POST("SubmitVisitorInfo")
    Observable<ListBean> getSubmitVisitorInfos(@Body RequestBody params);

    //获取广告列表
    @POST("GetAdverList")
    Observable<GetAdverListBean> getAdverList(@Body RequestBody params);

    //获取选中列表
    @POST("GetCheckedModelList")
    Observable<CheckedModelListBean> getCheckedModelList(@Body RequestBody params);

    //获取选中列表
    @POST("OpenDoor")
    Observable<VisitorBanedBean> getOpenDoor(@Body RequestBody params);

    //获取车辆减免列表
    @POST("GetUnionCodeList")
    Observable<GetUnionCodeListBean> getUnionCodeList(@Body RequestBody params);

    //获取车辆减免列表
    @POST("GenerateUnionCodes")
    Observable<SubmitVisitorInfoBean> getUnionCodes(@Body RequestBody params);

    //获取车辆减免列表
    @POST("UseTicket")
    Observable<SubmitVisitorInfoBean> getUseTicket(@Body RequestBody params);


}
