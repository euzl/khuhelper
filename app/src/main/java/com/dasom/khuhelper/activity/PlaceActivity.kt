package com.dasom.khuhelper.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dasom.khuhelper.databinding.ActivityPlaceBinding
import com.dasom.khuhelper.map.ChargingStation

class PlaceActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlaceBinding

    lateinit var chargingStation: ChargingStation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 데이터 수신
        val intent = intent
        chargingStation = intent.getSerializableExtra("chargingStation") as ChargingStation

        setInformation()
        setListener()
    }

    fun setListener() {
        binding.petitionApplyButton.setOnClickListener {
            val intent = Intent(this@PlaceActivity, PetitionApplyActivity::class.java)
            intent.putExtra("chargingStation", chargingStation)
            startActivity(intent)
        }
        binding.searchBackButton.setOnClickListener {
            finish()
        }
    }

    private fun setInformation() {
        binding.nameTextView.text = chargingStation.statNm
        binding.operTimeTextView.text = "Update : " + chargingStation!!.useTime
        when (chargingStation.chgerType) {
            1 -> binding.batteryTypeTextView.text = "DC 차데모"
            2 -> binding.batteryTypeTextView.text = "DC 차데모 + AC 3상"
            6 -> binding.batteryTypeTextView.text = "DC 차데모 + AD 3상 + DC 콤보"
        }
        var stat = "충전기상태 : "
        when (chargingStation.stat) {
            1 -> stat += "통신이상"
            2 -> stat += "충전대기"
            3 -> stat += "충전중"
            4 -> stat += "운영중지"
            5 -> stat += "점검중"
        }
        binding.extraInfoTextView.text = stat
        binding.addressTextView.text = chargingStation.addrDoro
    }
}