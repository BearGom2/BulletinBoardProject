package com.example.bulletin_board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
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

        val adapter = ViewHolder(setList())

        recycler_View.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_View.setHasFixedSize(true)
        recycler_View.adapter = adapter
        adapter.filter.filter("")

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        Newbtn.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setList(): ArrayList<boardData> {
        var responseData = arrayListOf<boardData>()
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
                        responseData.addAll(response.body()!!)
                        Log.i("responseData", response.body()!!.toString())
                        Log.i("responseData", responseData.toString())
                    }
                } else {
                    Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
                }
            }
        })
        try {
            Thread.sleep(80)
        } catch (e: Exception){
            e.printStackTrace()
        }
        return responseData
    }
    var mBackWait: Long = 0

    override fun onBackPressed() {
        if (System.currentTimeMillis() - mBackWait >= 2000) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(applicationContext, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show()
        } else {
            finish()
        }
    }
}