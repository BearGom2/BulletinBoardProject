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
    private var mBackWait: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButtonClick() //로그인 기능

        signupTv.setOnClickListener {
            val intent = Intent(baseContext, signupActivity::class.java)
            startActivity(intent)
        } //회원가입으로 이동
    }

    private fun loginButtonClick() {
        loginBtn.setOnClickListener { //로그인 버튼을 눌렀을 때 이벤트 발생
            val id = loginIdEdt.text.toString() //사용자가 적은 ID를 받아옴
            val password = loginPswEdt.text.toString() //사용자가 적은 Password를 받아옴

            val call_R: Call<userData> =
                Client.getClient.login(id, password) //서버로 이 전에 받아온 id와 password를 전송함
            call_R.enqueue(object : Callback<userData> { //userData 형태로 json 수신
                override fun onFailure(call: Call<userData>, t: Throwable) { //서버가 닫혔을 때
                    Toast.makeText(applicationContext, "네트워크 혹은 서버에 문제가 있습니다.", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<userData>,
                    response: Response<userData>
                ) { //서버 정상 작동
                    if (response.code() == 200) { //id와 password가 같을 때
                        getSetNameId.setName(response.body()?.name!!) //name 저장
                        getSetNameId.setId(response.body()?.id!!) //id 저장
                        val intent = Intent(baseContext, ListActivity::class.java)
                        startActivity(intent) //ListActivity로 이동
                        finish()
                    } else if (response.code() == 204) { //잘못 된 값이 왔을 때
                        Toast.makeText(applicationContext, "아이디 혹은 비밀번호가 다릅니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
        }
    }

    override fun onBackPressed() { //뒤로가기 버튼 누를 시 이벤트 발생
        if (System.currentTimeMillis() - mBackWait >= 2000) { //버튼을 누른지 2초 이상 지날 경우
            mBackWait = System.currentTimeMillis()
            Toast.makeText(applicationContext, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show()
        } else {//2초 안에 버튼을 2번 누를 경우
            finish() //종료
        }
    }
}