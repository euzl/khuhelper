package com.dasom.khuhelper.activity

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import com.dasom.khuhelper.R
import com.dasom.khuhelper.databinding.ActivityPetitionApplyBinding
import com.dasom.khuhelper.dto.Petition
import com.dasom.khuhelper.map.ChargingStation
import com.google.firebase.database.FirebaseDatabase

class PetitionApplyActivity : AppCompatActivity() {

    lateinit var binding: ActivityPetitionApplyBinding

    var chargingStation: ChargingStation? = null
    var petition: Petition? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetitionApplyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 데이터 수신
        val intent = intent
        chargingStation = intent.getSerializableExtra("chargingStation") as ChargingStation
        initView()
        setListener()
    }

    private fun initView() {
        binding.placeNameTextView.setText(chargingStation!!.statNm)
        binding.contentEditText.apply {
            setImeOptions(EditorInfo.IME_ACTION_DONE)
            setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submitPetition()
                    return@OnEditorActionListener true
                }
                false
            })
        }
    }

    fun setListener() {
        binding.submitButton.setOnClickListener {
            submitPetition()
        }
        binding.petitionBackButton.setOnClickListener {
            finish()
        }
    }

    private fun submitPetition() {
        // TODO: 04/09/2020 테스트끝나면 기존 saveData() 삭제 
        petition = Petition(
            binding.userNameEditText.text.toString(),
            binding.userEmailEditText.text.toString(),
            binding.titleEditText.text.toString(),
            binding.contentEditText.text.toString(),
            chargingStation!!.statId, chargingStation!!.statNm
        )
        saveFirebase()
        Toast.makeText(this.applicationContext, R.string.cp_submit_success, Toast.LENGTH_SHORT)
            .show()
        finish()
    }

    private fun saveFirebase() {
        val database = FirebaseDatabase.getInstance().reference
        database.child("petition").push().setValue(petition)
    }
}