package com.ysd.visitor.bean;

import java.util.List;

public class GetVisitorRightsBean {


    /**
     * code : 0
     * data : [{"visitorid":1697974866,"name":"屈先生","sex":"1","visitor_time":"1628143899630","purpose":"访友","leave_time":"1628145699630","cardid":"410122199008082014","visitorPhone":"13783633090","imgUrl":"C:\\website\\智慧社区测试\\VisitorPhotos\\2021-08-05\\20210805141139685.jpg","time":"2021-08-06T09:49:35.43","devcode":"BDD8002DCE0C8F27","devname":"测试门禁","loadstatus":"未下发","blackstatus":"未拉黑"}]
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
         * visitorid : 1697974866
         * name : 屈先生
         * sex : 1
         * visitor_time : 1628143899630
         * purpose : 访友
         * leave_time : 1628145699630
         * cardid : 410122199008082014
         * visitorPhone : 13783633090
         * imgUrl : C:\website\智慧社区测试\VisitorPhotos\2021-08-05\20210805141139685.jpg
         * time : 2021-08-06T09:49:35.43
         * devcode : BDD8002DCE0C8F27
         * devname : 测试门禁
         * loadstatus : 未下发
         * blackstatus : 未拉黑
         */

        private int visitorid;
        private String name;
        private String sex;
        private String visitor_time;
        private String purpose;
        private String leave_time;
        private String cardid;
        private String visitorPhone;
        private String imgUrl;
        private String time;
        private String devcode;
        private String devname;
        private String loadstatus;
        private String blackstatus;

        public int getVisitorid() {
            return visitorid;
        }

        public void setVisitorid(int visitorid) {
            this.visitorid = visitorid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getVisitor_time() {
            return visitor_time;
        }

        public void setVisitor_time(String visitor_time) {
            this.visitor_time = visitor_time;
        }

        public String getPurpose() {
            return purpose;
        }

        public void setPurpose(String purpose) {
            this.purpose = purpose;
        }

        public String getLeave_time() {
            return leave_time;
        }

        public void setLeave_time(String leave_time) {
            this.leave_time = leave_time;
        }

        public String getCardid() {
            return cardid;
        }

        public void setCardid(String cardid) {
            this.cardid = cardid;
        }

        public String getVisitorPhone() {
            return visitorPhone;
        }

        public void setVisitorPhone(String visitorPhone) {
            this.visitorPhone = visitorPhone;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDevcode() {
            return devcode;
        }

        public void setDevcode(String devcode) {
            this.devcode = devcode;
        }

        public String getDevname() {
            return devname;
        }

        public void setDevname(String devname) {
            this.devname = devname;
        }

        public String getLoadstatus() {
            return loadstatus;
        }

        public void setLoadstatus(String loadstatus) {
            this.loadstatus = loadstatus;
        }

        public String getBlackstatus() {
            return blackstatus;
        }

        public void setBlackstatus(String blackstatus) {
            this.blackstatus = blackstatus;
        }
    }
}
