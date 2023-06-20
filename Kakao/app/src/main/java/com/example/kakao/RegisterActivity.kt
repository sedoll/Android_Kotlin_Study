package com.example.kakao

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth // 파이어베이스 인증
    private lateinit var mDBRef: DatabaseReference // 실시간 데이터 베이스
    private lateinit var mEtEmail:EditText // 이메일 입력
    private lateinit var mEtPwd:EditText // 비밀번호 입력
    private lateinit var mBtnRegister:Button // 회원가입 버튼

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        mDBRef = FirebaseDatabase.getInstance().getReference("Kakao")

        mEtEmail = findViewById(R.id.et_email)
        mEtPwd = findViewById(R.id.et_pwd)
        mBtnRegister = findViewById(R.id.btn_register)

        mBtnRegister.setOnClickListener{
            // 회원가입 처리 시작
            val strEmail = mEtEmail.text.toString()
            val strPwd = mEtPwd.text.toString()
            
            // Firebase 진행
            mAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    var fireBaseUser:FirebaseUser? = mAuth.currentUser
                    var account = UserAccount()

                    if (fireBaseUser != null) {
                        account.setIdToken(fireBaseUser.uid)
                    }
                    if (fireBaseUser != null) {
                        account.setEmailId(fireBaseUser.email)
                    }
                    account.setPassword(strPwd)

                    if (fireBaseUser != null) {
                        mDBRef.child("UserAccount").child(fireBaseUser.uid).setValue(account)
                    }

                    Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}