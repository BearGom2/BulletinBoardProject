package com.example.bulletin_board

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        val intent = intent
        val title = intent.getStringExtra("mainTitle")
        val day = intent.getStringExtra("mainDay")
        val content = intent.getStringExtra("mainContent")
        val name = intent.getStringExtra("mainName")

        modifyTitleEdt.setText(title)
        modifyContentEdt.setText(content)

        val currentDateTime = Calendar.getInstance().time
        val dateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(currentDateTime)

        modifySaveBtn.setOnClickListener {
            saveButtonClick(
                modifyTitleEdt.editableText.toString(),
                dateFormat,
                modifyContentEdt.editableText.toString(),
                name,
                title,
                day,
                content
            )
        }


    }

    fun saveButtonClick(
        modifyTitle: String,
        modifyDay: String,
        modifyContent: String,
        name: String,
        title: String,
        day: String,
        content: String
    ) {
        Log.i("test", modifyTitle + modifyContent + modifyDay)
        if (modifyTitle.isNotBlank() && modifyContent.isNotBlank()) {
            val call_R: Call<Void> = Client.getClient.modify(
                modifyTitle, modifyDay, modifyContent, name, title, day, content, name
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
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.putExtra("title", modifyTitle)
                        intent.putExtra("name", name)
                        intent.putExtra("day", modifyDay)
                        intent.putExtra("content", modifyContent)
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        } else {
            Toast.makeText(applicationContext, "모든 값을 입력해주십시오.", Toast.LENGTH_SHORT).show()
        }
    }

}