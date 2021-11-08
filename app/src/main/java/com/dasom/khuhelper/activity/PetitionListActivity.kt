package com.dasom.khuhelper.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dasom.khuhelper.adapter.PetitionAdapter
import com.dasom.khuhelper.databinding.ActivityPetitionListBinding
import com.dasom.khuhelper.dto.Petition
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

private const val TAG = "PetitionListActivity"
open class PetitionListActivity : AppCompatActivity() {
    lateinit var binding: ActivityPetitionListBinding

    lateinit var petitionAdapter: PetitionAdapter
    var petitionList = ArrayList<Petition>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        binding = ActivityPetitionListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        setListener()
    }

    open fun initView() {
        Log.d(TAG, "initView: ")
        petitionAdapter = PetitionAdapter(this, petitionList)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@PetitionListActivity)
            adapter = petitionAdapter
        }
    }

    fun setListener() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        Log.d(TAG, "onStart: ")
        super.onStart()

        openData()
    }

    fun updateView() {
        Log.d(TAG, "updateView: ${petitionAdapter.petitionArrayList}")
        if (petitionAdapter.petitionArrayList.isNullOrEmpty()) {
            binding.recyclerView.visibility = View.GONE
            binding.notiTextView.visibility = View.VISIBLE
        } else {
            // 데이터가 있을 때
            binding.recyclerView.visibility = View.VISIBLE
            binding.notiTextView.visibility = View.GONE
//            binding.recyclerView.setOnClickListener(this) // 필요한가?
        }
    }

    private fun openData() {
        Log.d(TAG, "openData: ")
        // TODO: 04/09/2020 petition 리스트 비동기로 불러오기
        val dbRef = FirebaseDatabase.getInstance().reference.child("petition")

        //Read from the database (message 아래의 child의 이벤트 수신)
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list = ArrayList<Petition>()

                for (data in dataSnapshot.children) {
                    val petition = data.getValue(Petition::class.java)
                    petition!!.key = data.key
                    list.add(petition)
                }

                Log.d(TAG, "onDataChange: ${list.size}")

                petitionAdapter.petitionArrayList = list
                Log.d(TAG, "onDataChange: ${petitionAdapter.petitionArrayList!!.size}")
                petitionAdapter.notifyDataSetChanged()

                updateView()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}