package com.ysd.visitor.bean;

import java.util.List;

public class GetUnionCodeListBean {


    /**
     * code : 0
     * data : [{"id":1569876681,"unionCode":"http://s.appykt.com/zld/qr/c/d83557CF2D7AEf91c75","name":"自助优惠券","type":"4","limit":0,"sheets":1,"expiretime":"2022-05-01T16:55:56","businessid":1762402759,"status":0},{"id":1396102795,"unionCode":"http://s.appykt.com/zld/qr/c/d63756E47441858388c","name":"自助优惠券","type":"4","limit":0,"sheets":1,"expiretime":"2022-06-01T16:34:56","businessid":1762402759,"status":0},{"id":656234763,"unionCode":"http://s.appykt.com/zld/qr/c/d13E66CC7E44748a393","name":"自助优惠券","type":"4","limit":0,"sheets":1,"expiretime":"2022-06-01T16:35:21","businessid":1762402759,"status":0},{"id":1018630419,"unionCode":"http://s.appykt.com/zld/qr/c/da3BF6D7282AB30f271","name":"自助优惠券","type":"4","limit":0,"sheets":1,"expiretime":"2022-07-01T10:22:02","businessid":1762402759,"status":0},{"id":95734777,"unionCode":"http://s.appykt.com/zld/qr/c/d83786A0754F5c8b282","name":"自助优惠券","type":"4","limit":0,"sheets":1,"expiretime":"2022-07-01T16:35:10","businessid":1762402759,"status":0},{"id":1252925343,"unionCode":"http://s.appykt.com/zld/qr/c/zf3626341453538de2e","name":"自助优惠券","type":"4","limit":0,"sheets":1,"expiretime":"2022-07-07T13:29:21","businessid":1762402759,"status":0},{"id":539905469,"unionCode":"http://s.appykt.com/zld/qr/c/za3BE68F23256908b71","name":"自助优惠券","type":"4","limit":0,"sheets":1,"expiretime":"2022-09-01T10:22:15","businessid":1762402759,"status":0}]
     * msg : 查询成功!
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
         * id : 1569876681
         * unionCode : http://s.appykt.com/zld/qr/c/d83557CF2D7AEf91c75
         * name : 自助优惠券
         * type : 4
         * limit : 0
         * sheets : 1
         * expiretime : 2022-05-01T16:55:56
         * businessid : 1762402759
         * status : 0
         */

        private int id;
        private String unionCode;
        private String name;
        private String type;
        private int limit;
        private int sheets;
        private String expiretime;
        private int businessid;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUnionCode() {
            return unionCode;
        }

        public void setUnionCode(String unionCode) {
            this.unionCode = unionCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getSheets() {
            return sheets;
        }

        public void setSheets(int sheets) {
            this.sheets = sheets;
        }

        public String getExpiretime() {
            return expiretime;
        }

        public void setExpiretime(String expiretime) {
            this.expiretime = expiretime;
        }

        public int getBusinessid() {
            return businessid;
        }

        public void setBusinessid(int businessid) {
            this.businessid = businessid;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
