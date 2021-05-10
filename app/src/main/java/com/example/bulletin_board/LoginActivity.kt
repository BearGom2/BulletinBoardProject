package com.example.bulletin_board

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    var mBackWait: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButtonClick()

        signupTv.setOnClickListener {
            val intent = Intent(baseContext, signupActivity::class.java)
            startActivity(intent)
        }
    }

    fun loginButtonClick() {
        loginBtn.setOnClickListener {
            val id = loginIdEdt.text.toString()
            val password = loginPswEdt.text.toString()

            val call_R: Call<userData> = Client.getClient.login(id, password)
            call_R.enqueue(object : Callback<userData> {
                override fun onFailure(call: Call<userData>, t: Throwable) {
                    Toast.makeText(applicationContext, "네트워크 혹은 서버에 문제가 있습니다.", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(call: Call<userData>, response: Response<userData>) {
                    if (response.code() == 200) {
                        getSetName.setName(response.body()?.name!!)
                        val intent = Intent(baseContext, ListActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else if (response.code() == 204) {
                        Toast.makeText(applicationContext, "아이디 혹은 비밀번호가 다릅니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
        }
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - mBackWait >= 2000) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(applicationContext, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show()
        } else {
            finish()
        }
    }
}