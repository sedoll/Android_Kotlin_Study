package org.techtown.test1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)

        btn2.setOnClickListener {
            Log.d("로그", "값")
        }

        btn3.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("str", "안녕하세요")
            startActivity(intent)
        }
    }

    fun btnClick(view: android.view.View) {
        Toast.makeText(this, "버튼1 클릭", Toast.LENGTH_SHORT).show()
    }

}