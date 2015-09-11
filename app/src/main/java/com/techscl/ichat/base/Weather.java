package com.techscl.ichat.base;

/**
 * Created by songchunlin on 15/9/2.
 */
public class Weather {


    /**
     * errNum : 0
     * errMsg : success
     * retData : {"city":"大连","pinyin":"dalian","citycode":"101070201","date":"15-09-10","time":"11:00","postCode":"116000","longitude":121.576,"latitude":38.944,"altitude":"100","weather":"晴","temp":"26","l_tmp":"18","h_tmp":"26","WD":"北风","WS":"5-6级(25~34m/h)","sunrise":"05:30","sunset":"18:11"}
     */

    private int errNum;
    private String errMsg;
    private RetDataEntity retData;

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public void setRetData(RetDataEntity retData) {
        this.retData = retData;
    }

    public int getErrNum() {
        return errNum;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public RetDataEntity getRetData() {
        return retData;
    }

    public static class RetDataEntity {
        /**
         * city : 大连
         * pinyin : dalian
         * citycode : 101070201
         * date : 15-09-10
         * time : 11:00
         * postCode : 116000
         * longitude : 121.576
         * latitude : 38.944
         * altitude : 100
         * weather : 晴
         * temp : 26
         * l_tmp : 18
         * h_tmp : 26
         * WD : 北风
         * WS : 5-6级(25~34m/h)
         * sunrise : 05:30
         * sunset : 18:11
         */

        private String city;
        private String pinyin;
        private String citycode;
        private String date;
        private String time;
        private String postCode;
        private double longitude;
        private double latitude;
        private String altitude;
        private String weather;
        private String temp;
        private String l_tmp;
        private String h_tmp;
        private String WD;
        private String WS;
        private String sunrise;
        private String sunset;

        public void setCity(String city) {
            this.city = city;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public void setCitycode(String citycode) {
            this.citycode = citycode;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public void setAltitude(String altitude) {
            this.altitude = altitude;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public void setL_tmp(String l_tmp) {
            this.l_tmp = l_tmp;
        }

        public void setH_tmp(String h_tmp) {
            this.h_tmp = h_tmp;
        }

        public void setWD(String WD) {
            this.WD = WD;
        }

        public void setWS(String WS) {
            this.WS = WS;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        public void setSunset(String sunset) {
            this.sunset = sunset;
        }

        public String getCity() {
            return city;
        }

        public String getPinyin() {
            return pinyin;
        }

        public String getCitycode() {
            return citycode;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public String getPostCode() {
            return postCode;
        }

        public double getLongitude() {
            return longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public String getAltitude() {
            return altitude;
        }

        public String getWeather() {
            return weather;
        }

        public String getTemp() {
            return temp;
        }

        public String getL_tmp() {
            return l_tmp;
        }

        public String getH_tmp() {
            return h_tmp;
        }

        public String getWD() {
            return WD;
        }

        public String getWS() {
            return WS;
        }

        public String getSunrise() {
            return sunrise;
        }

        public String getSunset() {
            return sunset;
        }
    }
}
