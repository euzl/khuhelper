package com.dasom.khuhelper.activity

import android.content.Intent
import android.view.View
import com.dasom.khuhelper.adapter.PetitionAdapter
import com.dasom.khuhelper.dto.Petition
import java.util.*

class PetitionComfirmActivity : PetitionListActivity() {
    override fun initView() {
        super.initView()
        binding.listTitleTextView.text = "민원확인"
    }

    override fun setListener() {
        super.setListener()

        petitionAdapter.setItemClickListener(object: PetitionAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, petition: Petition) {
                val intent = Intent(this@PetitionComfirmActivity, PetitionReplyActivity::class.java).apply {
                    putExtra("petition", petition)
                    putExtra("isManage", false)
                }
                startActivity(intent)
            }
        })
    }
}