package com.example.bulletin_board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = intent
        Title_tv.text = intent.getStringExtra("title")
        Writer_tv.text = intent.getStringExtra("writer")
        Day_tv.text = intent.getStringExtra("day")
        Content_tv.text = intent.getStringExtra("content")
    }

    override fun onBackPressed() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }
}