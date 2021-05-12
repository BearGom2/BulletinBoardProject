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
    var permisson = 0 //중복 체크가 된 경우에는 1로 표시

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signupIdEdt.addTextChangedListener(textWatcher())
        check()//ID 중복 체크 기능
        signUp() //회원가입 기능
    }

    private fun signUp() {
        signupBtn.setOnClickListener { //회원가입 버튼을 눌렀을 때 호출
            if (permisson == 1) { //중복 체크를 했을 때만 통과
                val id = signupIdEdt.text.toString()
                val password = signupPswEdt.text.toString()
                val repassword = signupRePswEdt.text.toString()
                val name = signupNameEdt.text.toString()

                if (password.equals(repassword)) { //password와 다시 입력한 password가 맞는 경우에만 통과
                    val call_R: Call<Void> = Client.getClient.join(id, password, name)
                    call_R.enqueue(object : Callback<Void> {
                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(applicationContext, "서버나 네트워크에 문제가 있습니다.", Toast.LENGTH_SHORT)
                                .show()
                        }

                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.code() == 200) {
                                Toast.makeText(
                                    applicationContext,
                                    "${id}님 가입을 환영합니다!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(baseContext, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }else{
                                Toast.makeText(
                                    applicationContext,
                                    "회원가입 실패",
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
                        permisson = 1 //중복 체크를 확인
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

    private fun textWatcher() = object : TextWatcher { //텍스트에 변화가 있을 경우 호출
        override fun afterTextChanged(s: Editable?) {
            permisson = 0 //ID중복 체크를 먼저 받고 이후 고칠 경우 중복 체크 해제
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }
}