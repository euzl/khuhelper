package com.dasom.khuhelper.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dasom.khuhelper.R
import com.dasom.khuhelper.databinding.ActivityOptimalPlaceBinding
import com.dasom.khuhelper.dto.AnalyzePlace
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*

class OptimalPlaceActivity : AppCompatActivity() {
    lateinit var binding: ActivityOptimalPlaceBinding

    var mapView: MapView? = null
    var analyzePlaces = ArrayList<AnalyzePlace>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptimalPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        setListener()

        setAnalyzePlaces()
        markRecommendPlace(0, 426, 10)
        markRecommendPlace(426, 5000, 13)
        markRecommendPlace(5000, 29339, 100)
    }

    private fun initView() {
        mapView = MapView(this)
        binding.mapViewContainer.addView(mapView)
        mapView!!.setCalloutBalloonAdapter(CustomCalloutBalloonAdapter())
        mapView!!.setMapCenterPointAndZoomLevel(
            MapPoint.mapPointWithGeoCoord(
                37.518469,
                126.988232
            ), 5, false
        )
    }

    private fun markRecommendPlace(first: Int, last: Int, term: Int) {
        var mapPOIItem: MapPOIItem
        Log.d("Mark Recommend Place", "마커표시시작 총 개수 - " + analyzePlaces.size)
        var i = first
        while (i < last) {
            mapPOIItem = MapPOIItem()
            mapPOIItem.apply {
                itemName = analyzePlaces[i].finalPoint.toString()
                mapPoint = MapPoint.mapPointWithGeoCoord(analyzePlaces[i].lat, analyzePlaces[i].lng)
                markerType = MapPOIItem.MarkerType.CustomImage // 마커타입을 커스텀 마커로 지정.
                customImageResourceId = getMarkerColorId(analyzePlaces[i].finalPoint) // 마커 이미지.
                isCustomImageAutoscale = false // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
                setCustomImageAnchor(0.5f, 0.5f) // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
            }
            mapView!!.addPOIItem(mapPOIItem)
            i += term
        }
    }

    private fun getMarkerColorId(finalPoint: Double): Int {
        return if (finalPoint >= 50) {
            R.drawable.ic_recommend_red
        } else if (finalPoint >= 40) {
            R.drawable.ic_recommend_orange
        } else if (finalPoint >= 30) {
            R.drawable.ic_recommend_yellow
        } else if (finalPoint >= 20) {
            R.drawable.ic_recommend_green
        } else {
            R.drawable.ic_recommned_sky
        }
    }

    private fun setAnalyzePlaces() {
        val json = jonString
        jsonParsing(json)
    }

    private val jonString: String
        private get() {
            var json = ""
            val assetManager = assets
            try {
                val `is` = assetManager.open("0814.json")
                val fileSize = `is`.available()
                val buffer = ByteArray(fileSize)
                `is`.read(buffer)
                `is`.close()
                json = String(buffer, Charsets.UTF_8)
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
            return json
        }

    private fun jsonParsing(json: String) {
        try {
            val jsonObject = JSONObject(json)
            val finalPointObject = jsonObject.getJSONObject("Final_Point")
            val keys = finalPointObject.keys()
            val lngObject = jsonObject.getJSONObject("Centroid_x")
            val latObject = jsonObject.getJSONObject("Centroid_y")
            while (keys.hasNext()) {
                val key = keys.next()
                analyzePlaces.add(
                    AnalyzePlace(
                        key.toInt(),
                        key,
                        finalPointObject.getDouble(key),
                        latObject.getDouble(key),
                        lngObject.getDouble(key)
                    )
                )
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun setListener() {
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.allButton.setOnClickListener {
            mapView!!.removeAllPOIItems()
            markRecommendPlace(0, 5000, 10)
            markRecommendPlace(5000, 29339, 100)
        }
        binding.redButton.setOnClickListener {
            mapView!!.removeAllPOIItems()
            markRecommendPlace(0, 426, 5)
        }
        binding.orangeButton.setOnClickListener {
            mapView!!.removeAllPOIItems()
            markRecommendPlace(426, 3768, 50)
        }
        binding.yellowButton.setOnClickListener {
            mapView!!.removeAllPOIItems()
            markRecommendPlace(3768, 11483, 50)
        }
        binding.greenButton.setOnClickListener {
            mapView!!.removeAllPOIItems()
            markRecommendPlace(11483, 20285, 50)
        }
        binding.skyButton.setOnClickListener {
            mapView!!.removeAllPOIItems()
            markRecommendPlace(20285, 29339, 50)
        }
    }

    internal inner class CustomCalloutBalloonAdapter : CalloutBalloonAdapter {
        private val mCalloutBalloon: View =
            layoutInflater.inflate(R.layout.khuhelper_callout_ballon, null)
        private var nameTv: TextView? = null

        override fun getCalloutBalloon(poiItem: MapPOIItem): View {
            nameTv = mCalloutBalloon.findViewById(R.id.nameTextView)
            nameTv?.text = "Final Point : " + poiItem.itemName
            return mCalloutBalloon
        }

        override fun getPressedCalloutBalloon(poiItem: MapPOIItem): View? {
            return null
        }

    }
}