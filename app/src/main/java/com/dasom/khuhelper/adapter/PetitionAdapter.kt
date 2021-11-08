package com.dasom.khuhelper.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dasom.khuhelper.R
import com.dasom.khuhelper.adapter.PetitionAdapter.PetitionViewHolder
import com.dasom.khuhelper.dto.Petition
import java.util.*

private const val TAG = "PetitionAdapter_euzl"
class PetitionAdapter(private val context: Context, var petitionArrayList: ArrayList<Petition>?) :
    RecyclerView.Adapter<PetitionViewHolder>() {
    /**
     * 뷰홀더 생성 및 셋팅
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetitionViewHolder {
        Log.d(TAG, "onCreateViewHolder: ")
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
        holder.bindInfo(petitionArrayList!![position])
    }

    /**
     * 리사이클러뷰 안에 들어갈 뷰 홀더의 개수
     * (리턴값이 뷰 홀더의 개수와 맞지 않으면 리사이클러뷰가 뜨지 않는다.)
     * @return
     */
    override fun getItemCount(): Int = petitionArrayList?.size ?: 0

    // 클릭 인터페이스 정의
    interface ItemClickListener {
        fun onClick(view: View, position: Int, petition: Petition)
    }

    // 클릭 리스너 선언
    private lateinit var itemClickListener: ItemClickListener
    // 클릭 리스너 등록 메소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    inner class PetitionViewHolder(itemView: View) : ViewHolder(itemView) {
        var titleTextView: TextView = itemView.findViewById(R.id.tv_petition_title)
        var placeTextView: TextView = itemView.findViewById(R.id.tv_petition_place)
        var view: View = itemView

        fun bindInfo(petition: Petition) {
            titleTextView.text = petition.title
            placeTextView.text = "(" + petition.csName + ")"
            if (petition.isCheck) {
                titleTextView.setTextColor(itemView.resources.getColor(R.color.blue))
            }

            itemView.setOnClickListener {
                itemClickListener.onClick(it, layoutPosition, petition)
            }
        }
    }
}