package com.dasom.khuhelper.user;

public class ChargingStation {

    private String statId; // 충전소ID (고유값)
    private String statNm; // 충전소 이름
    private int chgerType; // 충전기 타입
    private String addrDoro; // 도로명주소
    private Float lat; // 위도
    private Float lng; // 경도
    private String useTime; // 이용시간

    public void setStatId(String statId) {
        this.statId = statId;
    }

    public void setStatNm(String statNm) {
        this.statNm = statNm;
    }

    public void setChgerType(int chgerType) {
        this.chgerType = chgerType;
    }

    public void setAddrDoro(String addrDoro) {
        this.addrDoro = addrDoro;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getStatId() {
        return statId;
    }

    public String getStatNm() {
        return statNm;
    }

    public int getChgerType() {
        return chgerType;
    }

    public String getAddrDoro() {
        return addrDoro;
    }

    public Float getLat() {
        return lat;
    }

    public Float getLng() {
        return lng;
    }

    public String getUseTime() {
        return useTime;
    }
}
