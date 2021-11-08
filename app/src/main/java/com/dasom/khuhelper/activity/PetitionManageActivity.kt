package com.dasom.khuhelper.activity

import android.content.Intent
import android.view.View
import com.dasom.khuhelper.adapter.PetitionAdapter
import com.dasom.khuhelper.dto.Petition

class PetitionManageActivity : PetitionListActivity() {
    override fun setListener() {
        super.setListener()

        petitionAdapter.setItemClickListener(object: PetitionAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, petition: Petition) {
                val intent = Intent(this@PetitionManageActivity, PetitionReplyActivity::class.java).apply {
                    putExtra("petition", petition)
                    putExtra("isManage", true)
                }
                startActivity(intent)
            }
        })
    }
}