package com.dasom.khuhelper.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dasom.khuhelper.R
import com.dasom.khuhelper.databinding.ActivityUserBinding
import com.dasom.khuhelper.map.ChargingStation
import com.dasom.khuhelper.map.ChargingStationParser
import com.dasom.khuhelper.map.ChargingStationParserCallBack
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.util.*

class UserActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserBinding

    lateinit var mapView: MapView
    var isSearchMode = false
    var imm: InputMethodManager? = null
    var chargingStationParser: ChargingStationParser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setListener()
        parseChargingStation() // 충전소 api 파싱
    }

    internal inner class CustomCalloutBalloonAdapter : CalloutBalloonAdapter {
        private val view: View =
            layoutInflater.inflate(R.layout.khuhelper_callout_ballon, null)

        override fun getCalloutBalloon(poiItem: MapPOIItem): View {
            val intent = Intent(this@UserActivity, PlaceActivity::class.java)
            intent.putExtra(
                "chargingStation",
                chargingStationParser!!.findStationByTag(poiItem.tag)
            )
            startActivity(intent)

            val nameTv = view.findViewById(R.id.nameTextView) as TextView
            nameTv.text = poiItem.itemName

            return view
        }

        override fun getPressedCalloutBalloon(poiItem: MapPOIItem): View? {
            return null
        }

    }

    fun setListener() {
        binding.userBackButton.setOnClickListener {
            onBackButton()
        }
        binding.petitionButton.setOnClickListener {
            startActivity(
                Intent(
                    this@UserActivity,
                    PetitionComfirmActivity::class.java
                )
            )
        }
    }

    private fun initView() {
        // map view
        mapView = MapView(this)
        binding.mapViewContainer.addView(mapView)
        mapView.setCalloutBalloonAdapter(CustomCalloutBalloonAdapter())
        mapView.setMapCenterPointAndZoomLevel(
            MapPoint.mapPointWithGeoCoord(
                37.518469,
                126.988232
            ), 5, false
        )
        binding.searchEditText.apply {
            imeOptions = EditorInfo.IME_ACTION_DONE
            setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    searchPlace(this.text.toString())
                    isSearchMode = true
                    return@OnEditorActionListener true
                }
                false
            })
        }
    }

    private fun onBackButton() {
        if (isSearchMode) {
            binding.searchEditText.setHint(R.string.search_ev_place)
            isSearchMode = false
        } else {
            val builder = AlertDialog.Builder(this, R.style.DialogStyle)
            builder.setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("확인") { _, _ ->
                    finish()
                    Toast.makeText(this@UserActivity, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("취소", null)
            builder.show()
        }
    }

    /**
     * name이 해당되는 충전소 마커로 표시
     *
     * @param name
     */
    private fun searchPlace(name: String) {
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

        mapView.removeAllPOIItems()
        if (markChargingStation(chargingStationParser!!.getStationsByName(name))) {
            val builder = StringBuilder()
                .append(name)
                .append("(와)과 관련된 충전소가 표시되었습니다.")
            Toast.makeText(this@UserActivity, builder, Toast.LENGTH_SHORT).show()
            mapView.fitMapViewAreaToShowAllPOIItems()
        } else {
            Toast.makeText(this@UserActivity, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun parseChargingStation() {
        // 충전소 파서 실행 (AsyncTask)
        chargingStationParser = ChargingStationParser(object : ChargingStationParserCallBack {
            override fun onSuccess(chargingStation: ChargingStation?) {
                markChargingStation(chargingStation)
            }
        })
        chargingStationParser!!.execute()
    }

    private fun markChargingStation(chargingStations: ArrayList<ChargingStation>): Boolean {
        if (chargingStations.size == 0) return false
        Log.d("Mark Carging Station", "마커표시시작")
        var isSuccess = false
        for (cs in chargingStations) {
            isSuccess = markChargingStation(cs)
        }
        return isSuccess
    }

    private fun markChargingStation(cs: ChargingStation?): Boolean {
        Log.d("Mark Carging Station", "마커표시")
        val mapPOIItem = MapPOIItem()
        mapPOIItem.apply {
            itemName = cs!!.statNm
            tag = cs.statTag
            mapPoint = MapPoint.mapPointWithGeoCoord(cs.lat!!.toDouble(), cs.lng!!.toDouble())
            markerType = MapPOIItem.MarkerType.CustomImage // 마커타입을 커스텀 마커로 지정.
            customImageResourceId = R.drawable.ic_ev_place // 마커 이미지.
            isCustomImageAutoscale = false // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
            setCustomImageAnchor(0.5f, 1.0f) // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
        }
        mapView.addPOIItem(mapPOIItem)
        return true
    }
}