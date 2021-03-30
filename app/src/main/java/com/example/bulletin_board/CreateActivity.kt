package com.example.bulletin_board

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create.*
import java.text.SimpleDateFormat
import java.util.*

class CreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        val intent = Intent(this, ListActivity::class.java)
        SetSave_btn.setOnClickListener {
            if(SetTitle_tv.text.isNotBlank()&&SetWriter_tv.text.isNotBlank()&&SetContent_tv.text.isNotBlank()){
                val currentDateTime = Calendar.getInstance().time
                var dateFormat =
                    SimpleDateFormat("yyyy.MM.dd HH+7:mm:ss", Locale.KOREA).format(currentDateTime)

                val bd: boardData_GetSet = boardData_GetSet()

                val item: boardData = boardData(
                    SetTitle_tv.text.toString(),
                    SetWriter_tv.text.toString(),
                    dateFormat,
                    SetContent_tv.text.toString()
                )

                bd.boardSet(item)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,"모든 값을 입력해주십시오.",Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onBackPressed() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }
}