package com.dasom.khuhelper.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dasom.khuhelper.R
import com.dasom.khuhelper.activity.PetitionReplyActivity
import com.dasom.khuhelper.dto.Petition
import com.dasom.khuhelper.adapter.PetitionAdapter.PetitionViewHolder
import java.util.*

class PetitionAdapter(private val context: Context, var petitionArrayList: ArrayList<Petition>?) :
    RecyclerView.Adapter<PetitionViewHolder>() {
    /**
     * 뷰홀더 생성 및 셋팅
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetitionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.petition_item, parent, false) // 뷰 생성
        return PetitionViewHolder(view) // 윗줄에서 생성된 뷰를 뷰홀더로
    }

    /**
     * position에 해당되는 데이터를 뷰홀더에 넣는 과정
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: PetitionViewHolder, position: Int) {
        val petition = petitionArrayList!![position]
        holder.titleTextView.text = petition.title
        holder.placeTextView.text = "(" + petition.csName + ")"
        if (petition.isCheck) {
            holder.titleTextView.setTextColor(holder.itemView.resources.getColor(R.color.blue))
        }

        // TODO: 04/09/2020 사용자가 확인할 때는 다른 액티비티로 이동하거나 다이얼로그가 떠야 한다. 따라서 리스너를 사용하던가 어댑터를 또 만들던가... 해야된다.
        holder.itemView.setOnClickListener {
            val intent = Intent(context, PetitionReplyActivity::class.java)
            intent.putExtra("petition", petition)
            context.startActivity(intent)
        }
    }

    /**
     * 리사이클러뷰 안에 들어갈 뷰 홀더의 개수
     * (리턴값이 뷰 홀더의 개수와 맞지 않으면 리사이클러뷰가 뜨지 않는다.)
     * @return
     */
    override fun getItemCount(): Int = petitionArrayList?.size ?: 0

    inner class PetitionViewHolder(itemView: View) : ViewHolder(itemView) {
        var titleTextView: TextView = itemView.findViewById(R.id.tv_petition_title)
        var placeTextView: TextView = itemView.findViewById(R.id.tv_petition_place)
        var view: View = itemView
    }
}