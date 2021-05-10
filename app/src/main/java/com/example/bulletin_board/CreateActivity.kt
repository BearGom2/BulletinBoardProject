package com.example.bulletin_board

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class CreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        setSaveButtonClick()
    }

    override fun onBackPressed() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }

    private fun setBoard(title: String, day: String, content: String, name: String) {
        val call_R: Call<Void> = Client.getClient.board_create(title, day, content, name)
        call_R.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(applicationContext, "잠시후 다시 시도해주십시오!", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "생성 성공!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "잠시후 다시 시도해주십시오!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun setSaveButtonClick() {
        SetSave_btn.setOnClickListener {
            if (SetTitle_tv.text.isNotBlank() && SetContent_tv.text.isNotBlank()) {
                val currentDateTime = Calendar.getInstance().time
                val dateFormat =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(currentDateTime)
                setBoard(
                    SetTitle_tv.text.toString(),
                    dateFormat,
                    SetContent_tv.text.toString(),
                    getSetName.getName()
                )
                val intent = Intent(this, ListActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "모든 값을 입력해주십시오.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}