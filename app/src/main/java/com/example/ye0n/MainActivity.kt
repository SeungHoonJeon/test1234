package com.example.ye0n

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val CHANNEL_ID = "testChannel01"   // Channel for notification
    private var notificationManager: NotificationManager? = null
    var login:Login? = null
    private lateinit var editTextId: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: TextView
    private lateinit var buttonSignUp: TextView
    private lateinit var buttonalert: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todoit_login)

        editTextId = findViewById(R.id.id_input)
        editTextPassword = findViewById(R.id.pw_input)
        buttonLogin = findViewById(R.id.login)
        buttonSignUp = findViewById(R.id.join)
        buttonalert = findViewById(R.id.button)

        buttonLogin.setOnClickListener {
            val id = editTextId.text.toString()
            val password = editTextPassword.text.toString()

            var retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.0.6:5000")
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
                        val dialog = AlertDialog.Builder(this@MainActivity)
                        dialog.setTitle("알림")
                        dialog.setMessage("로그인 성공")
                        dialog.show()
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

        buttonalert.setOnClickListener {
            var builder = NotificationCompat.Builder(this, "MY_channel")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("알림 제목")
                .setContentText("알림 내용")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // 오레오 버전 이후에는 알림을 받을 때 채널이 필요
                val channel_id = "MY_channel" // 알림을 받을 채널 id 설정
                val channel_name = "채널이름" // 채널 이름 설정
                val descriptionText = "설명글" // 채널 설명글 설정
                val importance = NotificationManager.IMPORTANCE_DEFAULT // 알림 우선순위 설정
                val channel = NotificationChannel(channel_id, channel_name, importance).apply {
                    description = descriptionText
                }

                // 만든 채널 정보를 시스템에 등록
                val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)

                // 알림 표시: 알림의 고유 ID(ex: 1002), 알림 결과
                notificationManager.notify(1002, builder.build())
            }
        }
    }
}