package com.dasom.khuhelper.activity

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import com.dasom.khuhelper.databinding.ActivityPetitionReplyBinding
import com.dasom.khuhelper.dto.Petition
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class PetitionReplyActivity : AppCompatActivity() {
    lateinit var binding: ActivityPetitionReplyBinding

    var petition: Petition? = null
    var isManage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetitionReplyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 데이터 수신
        petition = intent.getSerializableExtra("petition") as Petition
        isManage = intent.getBooleanExtra("isManage", false)

        initView()
    }

    private fun initView() {
        if (petition!!.isCheck) {
            binding.petitionTitleTextView.text = "처리된 민원 확인"
            binding.replyEditText.visibility = View.GONE
            // TODO: 07/09/2020 응답 작성한 것도 표시되도록
        } else {
            binding.petitionTitleTextView.text = "처리되지 않은 민원 확인"

            if (isManage) {
                // 응답기능
                binding.replyEditText.imeOptions = EditorInfo.IME_ACTION_DONE
                binding.replyEditText.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        submitPetition()
                        return@OnEditorActionListener true
                    }
                    false
                })
            } else {
                binding.replyEditText.visibility = View.GONE
            }

        }

        if (isManage) binding.replyApplyButton.text = "민원확인"
        else binding.replyApplyButton.text = "돌아가기"

        binding.userTextView.text = "작성자 : " + petition!!.username + " (" + petition!!.useremail + ")"
        binding.placeTextView.text = "충전소 : " + petition!!.csName + " (" + petition!!.csId + ")"
        binding.titleTextView.text = "제목 : " + petition!!.title
        binding.contentTextView.text = "내용 : " + petition!!.content
    }

    fun setListener() {
        binding.replyApplyButton.setOnClickListener {
            if (isManage) submitPetition()
            else finish()
        }
        binding.searchBackButton.setOnClickListener {
            finish()
        }
    }

    private fun submitPetition() {
        // TODO: 14/07/2020 DB에 관련 내용들이 저장되도록 구현
        petition!!.isCheck = true
        petition!!.reply = binding.replyEditText.text.toString()
        saveFirebase()
        Toast.makeText(this.applicationContext, "민원이 처리되었습니다.", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun saveFirebase() {
        val database = FirebaseDatabase.getInstance().reference
        val childUpdates: MutableMap<String, Any?> = HashMap()
        childUpdates["/petition/" + petition!!.key] = petition
        database.updateChildren(childUpdates)
    }
}