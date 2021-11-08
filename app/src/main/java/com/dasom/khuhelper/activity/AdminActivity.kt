package com.dasom.khuhelper.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dasom.khuhelper.R
import com.dasom.khuhelper.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListener()
    }

    fun setListener() {
        binding.firstBtn.setOnClickListener {
            startActivity(
                Intent(
                    this@AdminActivity,
                    OptimalPlaceActivity::class.java
                )
            )
        }
        binding.thirdBtn.setOnClickListener {
            startActivity(
                Intent(
                    this@AdminActivity,
                    PetitionManageActivity::class.java
                )
            )
        }
        binding.logoutBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this, R.style.DialogStyle)
            builder.setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("확인") { dialog, id ->
                    finish()
                    Toast.makeText(this@AdminActivity, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("취소") { dialog, id ->
                    // 취소하면 남겨놓음
                }
            builder.show()
        }
    }
}