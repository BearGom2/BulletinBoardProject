package com.example.bulletin_board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.setOnClickListener {
            val id = loginIdEdt.text.toString()
            val password = loginPswEdt.text.toString()

            val call_R: Call<Void> = Client.getClient.login(id, password)
            call_R.enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(applicationContext, "로그인 실패!" + t.message + t.cause, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {

                    if (response.code() == 200) {
                        Toast.makeText(applicationContext, response.message().toString(), Toast.LENGTH_SHORT).show()
                        val intent = Intent(baseContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else if(response.code() == 204){
                        Toast.makeText(applicationContext, response.message().toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

        gosignupBtn.setOnClickListener {
            val intent = Intent(baseContext, signupActivity::class.java)
            startActivity(intent)
        }
    }
}