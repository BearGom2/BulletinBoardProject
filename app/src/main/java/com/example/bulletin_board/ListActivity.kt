package com.example.bulletin_board

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        recycler_View.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_View.setHasFixedSize(true)
        setList()

        Newbtn.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setList() {

        val call_R: Call<ArrayList<boardData>> = Client.getClient.board_list()
        call_R.enqueue(object : Callback<ArrayList<boardData>> {
            override fun onFailure(call: Call<ArrayList<boardData>>, t: Throwable) {
                Toast.makeText(applicationContext, "잠시후 다시해주십시오!", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ArrayList<boardData>>,
                response: Response<ArrayList<boardData>>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        recycler_View.adapter = ViewHolder(response.body())
                    }
                } else {
                    Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onBackPressed() {
        var mBackWait: Long = 0
        if (System.currentTimeMillis() - mBackWait >= 2000) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(applicationContext, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show()
        } else {
            finish()
        }
    }
}