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
        setSaveButtonClick() //생성하기 버튼을 눌렀을 때 호출
    }

    private fun setBoard(title: String, day: String, content: String, name: String, id:String) { //서버와 통신하기 위한 코드
        val call_R: Call<Void> = Client.getClient.board_create(title, day, content, name, id)
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
                    Toast.makeText(applicationContext, "에러가 발생했습니다!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun setSaveButtonClick() {
        SetSave_btn.setOnClickListener {
            if (SetTitle_tv.text.isNotBlank() && SetContent_tv.text.isNotBlank()) { //내부의 모든 값이 비어있지 않을 때 통과
                val currentDateTime = Calendar.getInstance().time //현재의 시간을 받아오기 위한 코드
                val dateFormat =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(currentDateTime) //받아온 시간을 String 형태로 변환
                setBoard(
                    SetTitle_tv.text.toString(),
                    dateFormat,
                    SetContent_tv.text.toString(),
                    getSetNameId.getName(),
                    getSetNameId.getId()
                )
                val intent = Intent(this, ListActivity::class.java)
                startActivity(intent) //성공 후에 ListActivity로 이동하는 코드
            } else {
                Toast.makeText(this, "모든 값을 입력해주십시오.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}