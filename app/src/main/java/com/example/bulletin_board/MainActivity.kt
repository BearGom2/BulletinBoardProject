package com.example.bulletin_board

import android.content.Intent
import android.os.Bundle
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
        val name = intent.getStringExtra("name")

        Title_tv.text = title
        Writer_tv.text = name
        Day_tv.text = day
        Content_tv.text = content

        deleteBtn.setOnClickListener {
            val call_R: Call<Void> = Client.getClient.delete(
                title, day, content, name
            )
            call_R.enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(applicationContext, "네트워크 혹은 서버에 문제가 있습니다.", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(applicationContext, "게시물 삭제 성공!", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(applicationContext, ListActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }
}