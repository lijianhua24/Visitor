package com.ysd.visitor.bean;

public class VisitorMachineBean {

    /**
     * code : 0
     * data : {"id":1,"commid":1017590870,"devcode":"4a503cbced5ca626","loginno":"test","password":"123","model":"1,2,4","devs":"111,222,333","adverinterval":10}
     * msg : 查询成功
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * id : 1
         * commid : 1017590870
         * devcode : 4a503cbced5ca626
         * loginno : test
         * password : 123
         * model : 1,2,4
         * devs : 111,222,333
         * adverinterval : 10
         */

        private int id;
        private int commid;
        private String devcode;
        private String loginno;
        private String password;
        private String model;
        private String devs;
        private int adverinterval;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCommid() {
            return commid;
        }

        public void setCommid(int commid) {
            this.commid = commid;
        }

        public String getDevcode() {
            return devcode;
        }

        public void setDevcode(String devcode) {
            this.devcode = devcode;
        }

        public String getLoginno() {
            return loginno;
        }

        public void setLoginno(String loginno) {
            this.loginno = loginno;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getDevs() {
            return devs;
        }

        public void setDevs(String devs) {
            this.devs = devs;
        }

        public int getAdverinterval() {
            return adverinterval;
        }

        public void setAdverinterval(int adverinterval) {
            this.adverinterval = adverinterval;
        }
    }
}
