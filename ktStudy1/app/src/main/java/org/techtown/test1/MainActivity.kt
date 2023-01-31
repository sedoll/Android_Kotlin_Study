package org.techtown.test1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv = findViewById<TextView>(R.id.tv)
        val key = "str"

        if (intent.hasExtra(key)) {
            tv.text = intent.getStringExtra(key)
            /* "a1"라는 이름의 key에 저장된 값이 있다면
                   textView의 내용을 "a1" key에서 꺼내온 값으로 바꾼다 */
        } else {
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }

    }

}