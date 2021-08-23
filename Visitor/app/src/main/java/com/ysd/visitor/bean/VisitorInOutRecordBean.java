package com.ysd.visitor.bean;

import java.util.List;

public class VisitorInOutRecordBean {


    /**
     * code : 0
     * data : [{"id":1536687875,"inOutImgUrl":"http://39.97.176.54:8085/InOutImg/2021-08-12/20210812095051309.jpg","recTarget":"0","recType":"0","limitChecking":null,"perMessage":null,"isOpen":"1","inOutMark":null,"perCode":"1596826617","perName":"屈峰亮","occTime":"1628733050902","devCode":"BDD8002DCE0C8F27","temperature":null,"maskState":null,"isFever":null,"devname":"访客机测试","devaddress":"中兴新业港/南门门禁(右)"}]
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
         * id : 1536687875
         * inOutImgUrl : http://39.97.176.54:8085/InOutImg/2021-08-12/20210812095051309.jpg
         * recTarget : 0
         * recType : 0
         * limitChecking : null
         * perMessage : null
         * isOpen : 1
         * inOutMark : null
         * perCode : 1596826617
         * perName : 屈峰亮
         * occTime : 1628733050902
         * devCode : BDD8002DCE0C8F27
         * temperature : null
         * maskState : null
         * isFever : null
         * devname : 访客机测试
         * devaddress : 中兴新业港/南门门禁(右)
         */

        private int id;
        private String inOutImgUrl;
        private String recTarget;
        private String recType;
        private Object limitChecking;
        private Object perMessage;
        private String isOpen;
        private Object inOutMark;
        private String perCode;
        private String perName;
        private String occTime;
        private String devCode;
        private Object temperature;
        private Object maskState;
        private Object isFever;
        private String devname;
        private String devaddress;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInOutImgUrl() {
            return inOutImgUrl;
        }

        public void setInOutImgUrl(String inOutImgUrl) {
            this.inOutImgUrl = inOutImgUrl;
        }

        public String getRecTarget() {
            return recTarget;
        }

        public void setRecTarget(String recTarget) {
            this.recTarget = recTarget;
        }

        public String getRecType() {
            return recType;
        }

        public void setRecType(String recType) {
            this.recType = recType;
        }

        public Object getLimitChecking() {
            return limitChecking;
        }

        public void setLimitChecking(Object limitChecking) {
            this.limitChecking = limitChecking;
        }

        public Object getPerMessage() {
            return perMessage;
        }

        public void setPerMessage(Object perMessage) {
            this.perMessage = perMessage;
        }

        public String getIsOpen() {
            return isOpen;
        }

        public void setIsOpen(String isOpen) {
            this.isOpen = isOpen;
        }

        public Object getInOutMark() {
            return inOutMark;
        }

        public void setInOutMark(Object inOutMark) {
            this.inOutMark = inOutMark;
        }

        public String getPerCode() {
            return perCode;
        }

        public void setPerCode(String perCode) {
            this.perCode = perCode;
        }

        public String getPerName() {
            return perName;
        }

        public void setPerName(String perName) {
            this.perName = perName;
        }

        public String getOccTime() {
            return occTime;
        }

        public void setOccTime(String occTime) {
            this.occTime = occTime;
        }

        public String getDevCode() {
            return devCode;
        }

        public void setDevCode(String devCode) {
            this.devCode = devCode;
        }

        public Object getTemperature() {
            return temperature;
        }

        public void setTemperature(Object temperature) {
            this.temperature = temperature;
        }

        public Object getMaskState() {
            return maskState;
        }

        public void setMaskState(Object maskState) {
            this.maskState = maskState;
        }

        public Object getIsFever() {
            return isFever;
        }

        public void setIsFever(Object isFever) {
            this.isFever = isFever;
        }

        public String getDevname() {
            return devname;
        }

        public void setDevname(String devname) {
            this.devname = devname;
        }

        public String getDevaddress() {
            return devaddress;
        }

        public void setDevaddress(String devaddress) {
            this.devaddress = devaddress;
        }
    }
}
