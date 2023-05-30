package com.example.ye0n

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

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

            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("알람")
            dialog.setMessage("id = " + id + "pw = " + password)
            dialog.show()

            // 로그인 로직을 여기에 작성합니다.
            // 예를 들어, 입력된 이메일과 비밀번호를 확인하고 로그인 처리를 수행할 수 있습니다.

            //Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show()
        }

        buttonSignUp.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }


    }
}