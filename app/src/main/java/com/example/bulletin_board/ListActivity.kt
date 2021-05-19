package com.example.bulletin_board

import android.content.Intent
import android.os.Bundle
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
    private var mBackWait: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val adapter = ViewHolder(setList()) //기본 recyclerView adapter 선언

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener { //EditText의 text가 바뀌면 이벤트 호출
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)//adapter의 getFilter로 선언한 Filter 호출
                return false
            }
        })

        newBtn.setOnClickListener { //생성하기 버튼을 누르면 호출
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent) // CreateActivity로 이동
        }

        logoutBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            getSetNameId.setId("")
            getSetNameId.setName("")
            startActivity(intent) // CreateActivity로 이동
        }
    }

    private fun setList(): ArrayList<boardData> { //response.body에 담겨있는 내용을 반환해주는 함수
        val responseData = arrayListOf<boardData>()
        val call_R: Call<ArrayList<boardData>> = Client.getClient.boardList()
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
                    }
                } else {
                    Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
                }
            }
        })
        try { //비동기화 처리
            Thread.sleep(80)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return responseData
    }

    override fun onBackPressed() { //뒤로가기 버튼을 누르면 호출
        if (System.currentTimeMillis() - mBackWait >= 2000) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(applicationContext, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show()
        } else {
            finish()
        }
    }
}