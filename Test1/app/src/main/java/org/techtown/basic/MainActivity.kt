package org.techtown.basic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
//            Toast.makeText(applicationContext,
//                "토스트 메세지 출력",
//                Toast.LENGTH_SHORT)
            val edt = edt.text.toString()
            tv1.text = "결과 : $edt"
        }
    }
}