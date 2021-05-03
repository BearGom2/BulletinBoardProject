package com.example.bulletin_board

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class signupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signupBtn.setOnClickListener {
            val id = signupIdEdt.text.toString()
            val password = signupPswEdt.text.toString()
            val repassword = signupRePswEdt.text.toString()
            val name = signupNameEdt.text.toString()
            val addr = signupAddressEdt.text.toString()

            if (password.equals(repassword)) {
                val call_R: Call<Void> = Client.getClient.join(id, password, name, addr)
                call_R.enqueue(object : Callback<Void> {
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(applicationContext, "로그인 실패!" + t.message + t.cause, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {

                        if (response.code() == 200) {
                            Toast.makeText(applicationContext, response.message().toString(), Toast.LENGTH_SHORT).show()
                            val intent = Intent(baseContext, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else if (response.code() == 204) {
                            Toast.makeText(applicationContext, response.message().toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            } else {
                Toast.makeText(applicationContext, "비밀번호를 일치하게 입력해주십시오!",Toast.LENGTH_SHORT).show()
            }
        }
    }
}