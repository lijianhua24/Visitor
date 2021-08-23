package com.ysd.visitor.bean;

import java.util.List;

public class CheckedModelListBean {

    /**
     * code : 0
     * data : [{"id":190198045,"name":"人脸模式","value":"1"},{"id":1595769132,"name":"二维码模式","value":"2"},{"id":832169044,"name":"快捷模式","value":"4"}]
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
         * id : 190198045
         * name : 人脸模式
         * value : 1
         */

        private int id;
        private String name;
        private String value;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
