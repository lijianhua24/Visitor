package com.ysd.visitor.bean;

import java.util.List;

public class GetVisitorListBean  {


    /**
     * code : 0
     * data : [{"visitorid":1697974866,"name":"屈先生","sex":"1","visitor_time":"1628143899630","purpose":"访友","leave_time":"1628145699630","cardid":"410122199008082014","status":"0","visitorPhone":"13783633090","devCode":"4a503cbced5ca626","imgUrl":"http://39.97.176.54:8085/VisitorPhotos/2021-08-05/20210805141139685.jpg"},{"visitorid":2147091658,"name":"赵德光","sex":"1","visitor_time":"1619420489372","purpose":"","leave_time":null,"cardid":null,"status":"0","visitorPhone":"18239969539","devCode":"4a503cbced5ca626","imgUrl":""},{"visitorid":2147091651,"name":"宋章元","sex":"","visitor_time":"1616494867836","purpose":"访谈","leave_time":null,"cardid":null,"status":"0","visitorPhone":"15146978345","devCode":"4a503cbced5ca626","imgUrl":""},{"visitorid":2147091640,"name":"王记安","sex":"1","visitor_time":"1614845536074","purpose":"会友","leave_time":null,"cardid":null,"status":"0","visitorPhone":"13903719194","devCode":null,"imgUrl":""},{"visitorid":2147091646,"name":"张师帝","sex":"1","visitor_time":"1614416266066","purpose":"","leave_time":"1614419940000","cardid":null,"status":"0","visitorPhone":"18530976658","devCode":null,"imgUrl":""},{"visitorid":2147091650,"name":"","sex":"1","visitor_time":"1614040218260","purpose":"","leave_time":null,"cardid":null,"status":"0","visitorPhone":"18272625728","devCode":null,"imgUrl":""},{"visitorid":2147091638,"name":"","sex":"","visitor_time":"1613705190047","purpose":"面试","leave_time":null,"cardid":null,"status":"0","visitorPhone":"","devCode":null,"imgUrl":""},{"visitorid":2147091641,"name":"李彬","sex":"1","visitor_time":"1612176197226","purpose":"其他","leave_time":"1612179000000","cardid":null,"status":"0","visitorPhone":"18538216629","devCode":null,"imgUrl":""},{"visitorid":2147091654,"name":"吕亚利","sex":"","visitor_time":"1610334287659","purpose":"其他","leave_time":"1610348640000","cardid":null,"status":"0","visitorPhone":"16776159590","devCode":null,"imgUrl":""},{"visitorid":2147091639,"name":"钱树凯","sex":"1","visitor_time":"1609897313414","purpose":"访谈","leave_time":"1609904460000","cardid":null,"status":"0","visitorPhone":"15939031786","devCode":null,"imgUrl":""},{"visitorid":2147091657,"name":"李阳","sex":"1","visitor_time":"1609822803149","purpose":"会友","leave_time":null,"cardid":null,"status":"0","visitorPhone":"13598836000","devCode":null,"imgUrl":""},{"visitorid":2147091637,"name":"刘钰鑫","sex":"0","visitor_time":"1609813864912","purpose":"","leave_time":null,"cardid":null,"status":"0","visitorPhone":"13937115346","devCode":null,"imgUrl":""},{"visitorid":2147091652,"name":"王伟","sex":"1","visitor_time":"1609809612353","purpose":"会友","leave_time":"1609816740000","cardid":null,"status":"0","visitorPhone":"18135696896","devCode":null,"imgUrl":""},{"visitorid":2147091649,"name":"","sex":"","visitor_time":"1609806545271","purpose":"商务","leave_time":null,"cardid":null,"status":"0","visitorPhone":"","devCode":null,"imgUrl":""},{"visitorid":2147091635,"name":"赵会亭","sex":"1","visitor_time":"1609380516106","purpose":"","leave_time":null,"cardid":null,"status":"0","visitorPhone":"13837189877","devCode":null,"imgUrl":""},{"visitorid":2147091645,"name":"李程","sex":"1","visitor_time":"1609314552086","purpose":"访谈","leave_time":null,"cardid":null,"status":"0","visitorPhone":"15537191069","devCode":null,"imgUrl":""},{"visitorid":2147091642,"name":"","sex":"","visitor_time":"1609226991846","purpose":"面试","leave_time":null,"cardid":null,"status":"0","visitorPhone":"","devCode":null,"imgUrl":""},{"visitorid":2147091643,"name":"李金兰","sex":"0","visitor_time":"1609137053838","purpose":"","leave_time":null,"cardid":null,"status":"0","visitorPhone":"15890038916","devCode":null,"imgUrl":""},{"visitorid":2147091636,"name":"刘耀辉","sex":"1","visitor_time":"1609136753734","purpose":"","leave_time":null,"cardid":null,"status":"0","visitorPhone":"15515530187","devCode":null,"imgUrl":""},{"visitorid":2147091655,"name":"薛卫杰","sex":"1","visitor_time":"1608867405435","purpose":"商务","leave_time":null,"cardid":null,"status":"0","visitorPhone":"13027551777","devCode":null,"imgUrl":""},{"visitorid":2147091656,"name":"田径","sex":"1","visitor_time":"1608792186033","purpose":"面试","leave_time":"1608799320000","cardid":null,"status":"0","visitorPhone":"18530966106","devCode":null,"imgUrl":""},{"visitorid":2147091648,"name":"朱晓强","sex":"1","visitor_time":"1608622597376","purpose":"会友","leave_time":"1608629760000","cardid":null,"status":"0","visitorPhone":"18203618755","devCode":null,"imgUrl":""},{"visitorid":2147091644,"name":"","sex":"","visitor_time":"1608618558190","purpose":"面试","leave_time":null,"cardid":null,"status":"0","visitorPhone":"","devCode":null,"imgUrl":""},{"visitorid":2147091634,"name":"张宝亮","sex":"1","visitor_time":"1608605515895","purpose":"商务","leave_time":"1608624000000","cardid":null,"status":"0","visitorPhone":"18103816692","devCode":null,"imgUrl":""},{"visitorid":2147091647,"name":"何江帅","sex":"1","visitor_time":"1608101976776","purpose":"面试","leave_time":null,"cardid":null,"status":"0","visitorPhone":"13569628430","devCode":null,"imgUrl":""},{"visitorid":2147091653,"name":"李刘刚","sex":"1","visitor_time":"1607993227288","purpose":"其他","leave_time":null,"cardid":null,"status":"0","visitorPhone":"15238771815","devCode":null,"imgUrl":""}]
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
         * status : 0
         * visitorPhone : 13783633090
         * devCode : 4a503cbced5ca626
         * imgUrl : http://39.97.176.54:8085/VisitorPhotos/2021-08-05/20210805141139685.jpg
         */

        private int visitorid;
        private String name;
        private String sex;
        private String visitor_time;
        private String purpose;
        private String leave_time;
        private String cardid;
        private String status;
        private String visitorPhone;
        private String devCode;
        private String imgUrl;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getVisitorPhone() {
            return visitorPhone;
        }

        public void setVisitorPhone(String visitorPhone) {
            this.visitorPhone = visitorPhone;
        }

        public String getDevCode() {
            return devCode;
        }

        public void setDevCode(String devCode) {
            this.devCode = devCode;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
