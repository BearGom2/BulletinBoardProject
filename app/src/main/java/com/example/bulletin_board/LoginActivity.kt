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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.setOnClickListener {
            val id = loginIdEdt.text.toString()
            val password = loginPswEdt.text.toString()

            val call_R: Call<userData> = Client.getClient.login(id, password)
            call_R.enqueue(object : Callback<userData> {
                override fun onFailure(call: Call<userData>, t: Throwable) {
                    Toast.makeText(applicationContext, "로그인 실패!", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<userData>, response: Response<userData>) {
                    if (response.code() == 200) {
                        getSetName.setName(response.body()?.name!!)
                        val intent = Intent(baseContext, ListActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else if (response.code() == 204) {
                        Toast.makeText(
                            applicationContext,
                            response.message().toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }

        gosignupTv.setOnClickListener {
            val intent = Intent(baseContext, signupActivity::class.java)
            startActivity(intent)
        }
    }
}