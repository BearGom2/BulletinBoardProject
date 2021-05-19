package com.example.bulletin_board

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_modify.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ModifyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        val intent = intent //MainActivity에서 넘오온 값을 수신
        val title = intent.getStringExtra("mainTitle")
        val day = intent.getStringExtra("mainDay")
        val content = intent.getStringExtra("mainContent")

        modifyTitleEdt.setText(title)
        modifyContentEdt.setText(content)

        val currentDateTime = Calendar.getInstance().time //현재 시간을 받아오기 위한 코드
        val dateFormat =
            SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss",
                Locale.KOREA
            ).format(currentDateTime) //String 형태로 format

        modifySaveBtn.setOnClickListener {
            saveButtonClick(
                modifyTitleEdt.editableText.toString(),
                dateFormat,
                modifyContentEdt.editableText.toString(),
                getSetNameId.getName(),
                getSetNameId.getId(),
                title!!,
                day!!,
                content!!
            )
        } //수정하고 저장하기 버튼을 눌렀을 때 호출


    }

    private fun saveButtonClick(
        modifyTitle: String,
        modifyDay: String,
        modifyContent: String,
        name: String,
        id: String,
        title: String,
        day: String,
        content: String
    ) {
        if (modifyTitle.isNotBlank() && modifyContent.isNotBlank()) { //내부의 모든 값이 비어있지 않을 때 퉁과
            val call_R: Call<Void> = Client.getClient.modify(
                modifyTitle, modifyDay, modifyContent, title, day, content, name, id
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
                        Toast.makeText(applicationContext, "게시물 수정 성공!", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(
                            applicationContext,
                            MainActivity::class.java
                        ) //수정 된 값을 MainActivity에 전달
                        intent.putExtra("title", modifyTitle)
                        intent.putExtra("day", modifyDay)
                        intent.putExtra("content", modifyContent)
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        } else {
            Toast.makeText(applicationContext, "모든 값을 입력해주십시오.", Toast.LENGTH_SHORT).show()
        }
    }

}