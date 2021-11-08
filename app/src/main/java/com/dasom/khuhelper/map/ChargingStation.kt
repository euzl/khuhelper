package com.dasom.khuhelper.map

import java.io.Serializable

class ChargingStation : Serializable {
    var statTag // 마커용 tag (csList index)
            = 0
    var statId // 충전소ID (고유값)
            : String? = null
    var statNm // 충전소 이름
            : String? = null
    var chgerType // 충전기 타입
            = 0
    var stat // 충전기상태
            = 0
    var addrDoro // 도로명주소
            : String? = null
    var lat // 위도
            : Float? = null
    var lng // 경도
            : Float? = null
    var useTime // 이용시간
            : String? = null
}