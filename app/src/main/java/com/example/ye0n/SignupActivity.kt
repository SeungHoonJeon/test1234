package com.example.ye0n

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class SignupActivity : AppCompatActivity() {

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
            val pw = editTextPassword.text.toString()
            val pw_check = editTextCheckPassword.text.toString()

            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("알람")
            dialog.setMessage("id = " + id + "pw = " + pw + "pw_check = " + pw_check)
            dialog.show()
        }

        back.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}