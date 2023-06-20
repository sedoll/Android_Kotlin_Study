package com.example.kakao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth // 파이어베이스 인증
    private lateinit var mDBRef: DatabaseReference // 실시간 데이터 베이스
    private lateinit var mEtEmail: EditText // 이메일 입력
    private lateinit var mEtPwd: EditText // 비밀번호 입력

    private lateinit var btn_register:Button
    private lateinit var btn_login:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        mEtEmail = findViewById(R.id.et_email)
        mEtPwd = findViewById(R.id.et_pwd)

        btn_register = findViewById(R.id.btn_register)
        btn_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btn_login = findViewById(R.id.btn_login)
        btn_login.setOnClickListener {
            val strEmail = mEtEmail.text.toString()
            val strPwd = mEtPwd.text.toString()
            
            // 로그인
            mAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // 현재 액티비티 파괴
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                }
                
            }
        }
    }
}