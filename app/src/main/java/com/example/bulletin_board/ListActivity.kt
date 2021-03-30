package com.example.bulletin_board

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_list.*


class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val bd: boardData_GetSet = boardData_GetSet()

        recycler_View.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_View.setHasFixedSize(true)

        recycler_View.adapter = ViewHolder(bd.boardGet())

        Newbtn.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }


    }
}