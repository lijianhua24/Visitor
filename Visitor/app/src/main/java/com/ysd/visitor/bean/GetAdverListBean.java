package com.ysd.visitor.bean;

import java.util.List;

public class GetAdverListBean {

    /**
     * code : 0
     * data : [{"id":2015686584,"title":"园区访客通行二维码","url":"C:/website/智慧社区20191010/AdverUpload/2020-08-13/微信图片_20200813173622.jpg","md5":"67719072bb44944c653d39be15ff84ch","resolution":"1","begintime":"2020-08-05T16:13:59","endtime":"2021-12-19T15:58:29","status":"0","model":"1","createrid":871368973,"createtime":"2020-08-13T17:37:00.58","updatetime":"2020-08-13T17:37:00.58","tenantid":"1159725686,162299445,46674027,132167059","commid":1017590870}]
     * msg : 查询成功
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2015686584
         * title : 园区访客通行二维码
         * url : C:/website/智慧社区20191010/AdverUpload/2020-08-13/微信图片_20200813173622.jpg
         * md5 : 67719072bb44944c653d39be15ff84ch
         * resolution : 1
         * begintime : 2020-08-05T16:13:59
         * endtime : 2021-12-19T15:58:29
         * status : 0
         * model : 1
         * createrid : 871368973
         * createtime : 2020-08-13T17:37:00.58
         * updatetime : 2020-08-13T17:37:00.58
         * tenantid : 1159725686,162299445,46674027,132167059
         * commid : 1017590870
         */

        private int id;
        private String title;
        private String url;
        private String md5;
        private String resolution;
        private String begintime;
        private String endtime;
        private String status;
        private String model;
        private int createrid;
        private String createtime;
        private String updatetime;
        private String tenantid;
        private int commid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getResolution() {
            return resolution;
        }

        public void setResolution(String resolution) {
            this.resolution = resolution;
        }

        public String getBegintime() {
            return begintime;
        }

        public void setBegintime(String begintime) {
            this.begintime = begintime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public int getCreaterid() {
            return createrid;
        }

        public void setCreaterid(int createrid) {
            this.createrid = createrid;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getTenantid() {
            return tenantid;
        }

        public void setTenantid(String tenantid) {
            this.tenantid = tenantid;
        }

        public int getCommid() {
            return commid;
        }

        public void setCommid(int commid) {
            this.commid = commid;
        }
    }
}
