package org.techtown.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email = findViewById<TextView>(R.id.etEmail)
        val pw = findViewById<TextView>(R.id.etPW)
        val name = findViewById<TextView>(R.id.etName)
        val age = findViewById<TextView>(R.id.etAge)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        println("email 타입 ${email.javaClass}")

        btnLogin.setOnClickListener() {
            val intent = Intent(this, SubActivity::class.java)

            intent.putExtra("email", email.text.toString())
            intent.putExtra("pw", pw.text.toString())
            intent.putExtra("name", name.text.toString())
            intent.putExtra("age", age.text.toString())
            startActivity(intent)

        }


    }
}