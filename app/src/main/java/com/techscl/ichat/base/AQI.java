package com.techscl.ichat.base;

/**
 * Created by songchunlin on 15/9/14.
 */
public class AQI {

    /**
     * errNum : 0
     * retMsg : success
     * retData : {"city":"大连","time":"2015-09-09T16:00:00Z","aqi":60,"level":"良","core":"臭氧8小时"}
     */

    private int errNum;
    private String retMsg;
    private RetDataEntity retData;

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public void setRetData(RetDataEntity retData) {
        this.retData = retData;
    }

    public int getErrNum() {
        return errNum;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public RetDataEntity getRetData() {
        return retData;
    }

    public static class RetDataEntity {
        /**
         * city : 大连
         * time : 2015-09-09T16:00:00Z
         * aqi : 60
         * level : 良
         * core : 臭氧8小时
         */

        private String city;
        private String time;
        private int aqi;
        private String level;
        private String core;

        public void setCity(String city) {
            this.city = city;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setAqi(int aqi) {
            this.aqi = aqi;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public void setCore(String core) {
            this.core = core;
        }

        public String getCity() {
            return city;
        }

        public String getTime() {
            return time;
        }

        public int getAqi() {
            return aqi;
        }

        public String getLevel() {
            return level;
        }

        public String getCore() {
            return core;
        }
    }
}
