package com.example.ye0n

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignupActivity : AppCompatActivity() {
    var signup:Signup? = null
    private lateinit var back: ImageView
    private lateinit var editTextId: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextCheckPassword: EditText
    private lateinit var make_account: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todoit_join)

        back = findViewById(R.id.back1)
        editTextId = findViewById(R.id.id_join)
        editTextPassword = findViewById(R.id.pw_join)
        editTextCheckPassword = findViewById(R.id.pw_check)
        make_account = findViewById(R.id.make_account)

        make_account.setOnClickListener{
            val id = editTextId.text.toString()
            val password = editTextPassword.text.toString()
            val passwordCheck = editTextCheckPassword.text.toString()

            /*
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("알람")
            dialog.setMessage("id = " + id + "pw = " + pw + "pw_check = " + pw_check)
            dialog.show()
             */

            var retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.35.178:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            var SignupService: SignupService = retrofit.create(SignupService::class.java)

            SignupService.requestLogin(id,password,passwordCheck).enqueue(object: Callback<Signup> {
                override fun onFailure(call: Call<Signup>, t: Throwable) {
                    var dialog = AlertDialog.Builder(this@SignupActivity)
                    Log.e("Signup",t.toString())
                    dialog.setTitle("에러")
                    dialog.setMessage("호출실패했습니다.")
                    dialog.show()
                }

                override fun onResponse(call: Call<Signup>, response: Response<Signup>) {
                    signup = response.body()
                    Log.d("LOGIN","msg : "+signup?.msg)
                    Log.d("LOGIN","code : "+signup?.code)

                    if (signup?.code == "200") { // 회원가입 성공 시
                        val intent = Intent(this@SignupActivity, MainActivity::class.java)
                        startActivity(intent)
                    } else { // 회원가입 실패 시
                        val dialog = AlertDialog.Builder(this@SignupActivity)
                        dialog.setTitle(signup?.msg)
                        dialog.setMessage(signup?.code)
                        dialog.show()
                    }
                }
            })
        }

        back.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}