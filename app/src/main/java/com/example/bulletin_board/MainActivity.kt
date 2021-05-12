package com.example.bulletin_board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = intent
        val title = intent.getStringExtra("title")
        val day = intent.getStringExtra("day")
        val content = intent.getStringExtra("content")
        val writerId = intent.getStringExtra("id")
        val name = getSetNameId.getName()
        val userId = getSetNameId.getId()

        TitleTv.text = title
        WriterTv.text = name
        DayTv.text = day
        ContentTv.text = content

        Log.i("test", userId + writerId)

        modifyButtonClick(title, day, content, writerId, userId) //수정하기 버튼을 클릭했을 때
        delButtonClick(title, day, content, name, writerId, userId) //삭제하기 버튼을 클릭했을 때

    }

    fun modifyButtonClick(
        title: String,
        day: String,
        content: String,
        writerId: String,
        userId: String
    ) {
        modifyBtn.setOnClickListener { //수정하기 버튼을 클릭했을 때
            if ((writerId.equals(userId))) { //현재 사용자의 ID와 작성자의 ID가 같을 경우에만 퉁과
                val intent = Intent(this, ModifyActivity::class.java) //값과 화면을 ModifyActivity로 이동
                intent.putExtra("mainTitle", title)
                intent.putExtra("mainDay", day)
                intent.putExtra("mainContent", content)
                intent.putExtra("id", writerId)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "작성자만 수정할 수 있습니다.", Toast.LENGTH_SHORT)
            }

        }
    }

    fun delButtonClick(
        title: String,
        day: String,
        content: String,
        name: String,
        writerId: String,
        userId: String
    ) {
        deleteBtn.setOnClickListener {//삭제하기 버튼을 클릭했을 때
            if (writerId.equals(userId)) { //현재 사용자의 ID와 작성자의 ID가 같을 경우에만 퉁과
                val call_R: Call<Void> = Client.getClient.delete(
                    title, day, content, name, userId
                )
                call_R.enqueue(object : Callback<Void> {
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(
                            applicationContext,
                            "네트워크 혹은 서버에 문제가 있습니다.",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            Toast.makeText(applicationContext, "게시물 삭제 성공!", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(applicationContext, ListActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                })
            } else{
                Toast.makeText(applicationContext, "작성자만 삭제할 수 있습니다.", Toast.LENGTH_SHORT)
            }
        }
    }

}