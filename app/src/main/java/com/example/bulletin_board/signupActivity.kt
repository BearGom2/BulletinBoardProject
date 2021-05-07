package com.example.bulletin_board

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class signupActivity : AppCompatActivity() {
    var permisson = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signupIdEdt.addTextChangedListener(textWatcher())
        check()
        signUp()
    }

    private fun signUp() {
        signupBtn.setOnClickListener {
            if (permisson == 1) {
                val id = signupIdEdt.text.toString()
                val password = signupPswEdt.text.toString()
                val repassword = signupRePswEdt.text.toString()
                val name = signupNameEdt.text.toString()

                if (password.equals(repassword)) {
                    val call_R: Call<Void> = Client.getClient.join(id, password, name)
                    call_R.enqueue(object : Callback<Void> {
                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(applicationContext, "회원가입 실패!", Toast.LENGTH_SHORT)
                                .show()
                        }

                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.code() == 200) {
                                Toast.makeText(
                                    applicationContext,
                                    response.message().toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(baseContext, LoginActivity::class.java)
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
                } else {
                    Toast.makeText(applicationContext, "비밀번호를 일치하게 입력해주십시오!", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(applicationContext, "ID 중복확인을 해주십시오!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun check() {
        checkBtn.setOnClickListener {
            val id = signupIdEdt.text.toString()

            val call_R: Call<Void> = Client.getClient.check(id)
            call_R.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.code() == 200) {
                        Toast.makeText(applicationContext, "사용 가능한 ID입니다!", Toast.LENGTH_SHORT)
                            .show()
                        permisson = 1
                    } else if (response.code() == 204) {
                        Toast.makeText(applicationContext, "이미있는 ID입니다!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(applicationContext, "알 수 없는 오류!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun textWatcher() = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            permisson = 0
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }
}