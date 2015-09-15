package com.techscl.ichat.base;

import java.util.List;

/**
 * Created by songchunlin on 15/9/2.
 */
public class Weather {

    /**
     * resultcode : 200
     * reason : successed!
     * result : {"sk":{"temp":"22","wind_direction":"北风","wind_strength":"0级","humidity":"81%","time":"16:30"},"today":{"temperature":"19℃~24℃","weather":"阵雨转阴","weather_id":{"fa":"03","fb":"02"},"wind":"东风微风","week":"星期二","city":"苏州","date_y":"2015年09月15日","dressing_index":"舒适","dressing_advice":"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。","uv_index":"最弱","comfort_index":"","wash_index":"不宜","travel_index":"较不宜","exercise_index":"较不宜","drying_index":""},"future":[{"temperature":"19℃~24℃","weather":"阵雨转阴","weather_id":{"fa":"03","fb":"02"},"wind":"东风微风","week":"星期二","date":"20150915"},{"temperature":"20℃~27℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"北风微风","week":"星期三","date":"20150916"},{"temperature":"21℃~28℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"北风微风","week":"星期四","date":"20150917"},{"temperature":"21℃~28℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"东北风微风","week":"星期五","date":"20150918"},{"temperature":"22℃~28℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"东北风微风","week":"星期六","date":"20150919"},{"temperature":"22℃~27℃","weather":"多云转阴","weather_id":{"fa":"01","fb":"02"},"wind":"东风3-4 级","week":"星期日","date":"20150920"},{"temperature":"22℃~27℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"东风3-4 级","week":"星期一","date":"20150921"}]}
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    private ResultEntity result;
    private int error_code;

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getResultcode() {
        return resultcode;
    }

    public String getReason() {
        return reason;
    }

    public ResultEntity getResult() {
        return result;
    }

    public int getError_code() {
        return error_code;
    }

    public static class ResultEntity {
        /**
         * sk : {"temp":"22","wind_direction":"北风","wind_strength":"0级","humidity":"81%","time":"16:30"}
         * today : {"temperature":"19℃~24℃","weather":"阵雨转阴","weather_id":{"fa":"03","fb":"02"},"wind":"东风微风","week":"星期二","city":"苏州","date_y":"2015年09月15日","dressing_index":"舒适","dressing_advice":"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。","uv_index":"最弱","comfort_index":"","wash_index":"不宜","travel_index":"较不宜","exercise_index":"较不宜","drying_index":""}
         * future : [{"temperature":"19℃~24℃","weather":"阵雨转阴","weather_id":{"fa":"03","fb":"02"},"wind":"东风微风","week":"星期二","date":"20150915"},{"temperature":"20℃~27℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"北风微风","week":"星期三","date":"20150916"},{"temperature":"21℃~28℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"北风微风","week":"星期四","date":"20150917"},{"temperature":"21℃~28℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"东北风微风","week":"星期五","date":"20150918"},{"temperature":"22℃~28℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"东北风微风","week":"星期六","date":"20150919"},{"temperature":"22℃~27℃","weather":"多云转阴","weather_id":{"fa":"01","fb":"02"},"wind":"东风3-4 级","week":"星期日","date":"20150920"},{"temperature":"22℃~27℃","weather":"多云","weather_id":{"fa":"01","fb":"01"},"wind":"东风3-4 级","week":"星期一","date":"20150921"}]
         */

        private SkEntity sk;
        private TodayEntity today;
        private List<FutureEntity> future;

        public void setSk(SkEntity sk) {
            this.sk = sk;
        }

        public void setToday(TodayEntity today) {
            this.today = today;
        }

        public void setFuture(List<FutureEntity> future) {
            this.future = future;
        }

        public SkEntity getSk() {
            return sk;
        }

        public TodayEntity getToday() {
            return today;
        }

        public List<FutureEntity> getFuture() {
            return future;
        }

        public static class SkEntity {
            /**
             * temp : 22
             * wind_direction : 北风
             * wind_strength : 0级
             * humidity : 81%
             * time : 16:30
             */

            private String temp;
            private String wind_direction;
            private String wind_strength;
            private String humidity;
            private String time;

            public void setTemp(String temp) {
                this.temp = temp;
            }

            public void setWind_direction(String wind_direction) {
                this.wind_direction = wind_direction;
            }

            public void setWind_strength(String wind_strength) {
                this.wind_strength = wind_strength;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getTemp() {
                return temp;
            }

            public String getWind_direction() {
                return wind_direction;
            }

            public String getWind_strength() {
                return wind_strength;
            }

            public String getHumidity() {
                return humidity;
            }

            public String getTime() {
                return time;
            }
        }

        public static class TodayEntity {
            /**
             * temperature : 19℃~24℃
             * weather : 阵雨转阴
             * weather_id : {"fa":"03","fb":"02"}
             * wind : 东风微风
             * week : 星期二
             * city : 苏州
             * date_y : 2015年09月15日
             * dressing_index : 舒适
             * dressing_advice : 建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。
             * uv_index : 最弱
             * comfort_index :
             * wash_index : 不宜
             * travel_index : 较不宜
             * exercise_index : 较不宜
             * drying_index :
             */

            private String temperature;
            private String weather;
            private WeatherIdEntity weather_id;
            private String wind;
            private String week;
            private String city;
            private String date_y;
            private String dressing_index;
            private String dressing_advice;
            private String uv_index;
            private String comfort_index;
            private String wash_index;
            private String travel_index;
            private String exercise_index;
            private String drying_index;

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public void setWeather_id(WeatherIdEntity weather_id) {
                this.weather_id = weather_id;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public void setDate_y(String date_y) {
                this.date_y = date_y;
            }

            public void setDressing_index(String dressing_index) {
                this.dressing_index = dressing_index;
            }

            public void setDressing_advice(String dressing_advice) {
                this.dressing_advice = dressing_advice;
            }

            public void setUv_index(String uv_index) {
                this.uv_index = uv_index;
            }

            public void setComfort_index(String comfort_index) {
                this.comfort_index = comfort_index;
            }

            public void setWash_index(String wash_index) {
                this.wash_index = wash_index;
            }

            public void setTravel_index(String travel_index) {
                this.travel_index = travel_index;
            }

            public void setExercise_index(String exercise_index) {
                this.exercise_index = exercise_index;
            }

            public void setDrying_index(String drying_index) {
                this.drying_index = drying_index;
            }

            public String getTemperature() {
                return temperature;
            }

            public String getWeather() {
                return weather;
            }

            public WeatherIdEntity getWeather_id() {
                return weather_id;
            }

            public String getWind() {
                return wind;
            }

            public String getWeek() {
                return week;
            }

            public String getCity() {
                return city;
            }

            public String getDate_y() {
                return date_y;
            }

            public String getDressing_index() {
                return dressing_index;
            }

            public String getDressing_advice() {
                return dressing_advice;
            }

            public String getUv_index() {
                return uv_index;
            }

            public String getComfort_index() {
                return comfort_index;
            }

            public String getWash_index() {
                return wash_index;
            }

            public String getTravel_index() {
                return travel_index;
            }

            public String getExercise_index() {
                return exercise_index;
            }

            public String getDrying_index() {
                return drying_index;
            }

            public static class WeatherIdEntity {
                /**
                 * fa : 03
                 * fb : 02
                 */

                private String fa;
                private String fb;

                public void setFa(String fa) {
                    this.fa = fa;
                }

                public void setFb(String fb) {
                    this.fb = fb;
                }

                public String getFa() {
                    return fa;
                }

                public String getFb() {
                    return fb;
                }
            }
        }

        public static class FutureEntity {
            /**
             * temperature : 19℃~24℃
             * weather : 阵雨转阴
             * weather_id : {"fa":"03","fb":"02"}
             * wind : 东风微风
             * week : 星期二
             * date : 20150915
             */

            private String temperature;
            private String weather;
            private WeatherIdEntity weather_id;
            private String wind;
            private String week;
            private String date;

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public void setWeather_id(WeatherIdEntity weather_id) {
                this.weather_id = weather_id;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getTemperature() {
                return temperature;
            }

            public String getWeather() {
                return weather;
            }

            public WeatherIdEntity getWeather_id() {
                return weather_id;
            }

            public String getWind() {
                return wind;
            }

            public String getWeek() {
                return week;
            }

            public String getDate() {
                return date;
            }

            public static class WeatherIdEntity {
                /**
                 * fa : 03
                 * fb : 02
                 */

                private String fa;
                private String fb;

                public void setFa(String fa) {
                    this.fa = fa;
                }

                public void setFb(String fb) {
                    this.fb = fb;
                }

                public String getFa() {
                    return fa;
                }

                public String getFb() {
                    return fb;
                }
            }
        }
    }
}
