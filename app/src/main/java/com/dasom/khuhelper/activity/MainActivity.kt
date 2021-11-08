package com.dasom.khuhelper.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dasom.khuhelper.R

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_user_login -> {
                startActivity(Intent(this@MainActivity, UserActivity::class.java))
                Toast.makeText(this@MainActivity, "사용자로 로그인되었습니다.", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_admin_login -> {
                startActivity(Intent(this@MainActivity, AdminActivity::class.java))
                Toast.makeText(this@MainActivity, "관리자로 로그인되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initView() {
        val userLoginBtn = findViewById<Button>(R.id.btn_user_login)
        val adminLoginBtn = findViewById<TextView>(R.id.btn_admin_login)
        userLoginBtn.setOnClickListener(this)
        adminLoginBtn.setOnClickListener(this)
    }
}