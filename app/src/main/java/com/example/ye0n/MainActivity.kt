package com.example.ye0n

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    var login:Login? = null
    private lateinit var editTextId: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: TextView
    private lateinit var buttonSignUp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todoit_login)

        editTextId = findViewById(R.id.id_input)
        editTextPassword = findViewById(R.id.pw_input)
        buttonLogin = findViewById(R.id.login)
        buttonSignUp = findViewById(R.id.join)

        buttonLogin.setOnClickListener {
            val id = editTextId.text.toString()
            val password = editTextPassword.text.toString()

            var retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.35.178:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            var loginService: LoginService = retrofit.create(LoginService::class.java)

            loginService.requestLogin(id,password).enqueue(object: Callback<Login> {
                override fun onFailure(call: Call<Login>, t: Throwable) {
                    var dialog = AlertDialog.Builder(this@MainActivity)
                    Log.e("LOGIN",t.toString())
                    dialog.setTitle("에러")
                    dialog.setMessage("호출실패했습니다.")
                    dialog.show()
                }

                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    login = response.body()
                    Log.d("LOGIN","msg : "+login?.msg)
                    Log.d("LOGIN","code : "+login?.code)

                    if (login?.code == "200") { // 로그인 성공 시
                        val intent = Intent(this@MainActivity, HomeActivity::class.java)
                        startActivity(intent)
                    } else { // 로그인 실패 시
                        val dialog = AlertDialog.Builder(this@MainActivity)
                        dialog.setTitle(login?.msg)
                        dialog.setMessage(login?.code)
                        dialog.show()
                    }
                }
            })
        }

        buttonSignUp.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}