package com.dasom.khuhelper;

public class ChargingStation {

    private Integer statId; // 충전소ID (고유값)
    private String statNm; // 충전소 이름
    private Integer chgerType; // 충전기 타입
    private String addrDoro; // 도로명주소
    private Integer lat; // 위도
    private Integer lng; // 경도
    private String useTime; // 이용시간

    public void setStatId(Integer statId) {
        this.statId = statId;
    }

    public void setStatNm(String statNm) {
        this.statNm = statNm;
    }

    public void setChgerType(Integer chgerType) {
        this.chgerType = chgerType;
    }

    public void setAddrDoro(String addrDoro) {
        this.addrDoro = addrDoro;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public void setLng(Integer lng) {
        this.lng = lng;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public Integer getStatId() {
        return statId;
    }

    public String getStatNm() {
        return statNm;
    }

    public Integer getChgerType() {
        return chgerType;
    }

    public String getAddrDoro() {
        return addrDoro;
    }

    public Integer getLat() {
        return lat;
    }

    public Integer getLng() {
        return lng;
    }

    public String getUseTime() {
        return useTime;
    }
}
