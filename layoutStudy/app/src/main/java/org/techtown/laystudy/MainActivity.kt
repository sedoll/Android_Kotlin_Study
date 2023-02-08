package org.techtown.laystudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv1 = findViewById<TextView>(R.id.tv1)
        val tv2 = findViewById<TextView>(R.id.tv2)

        var count = 0

        tv1.setOnClickListener{
            tv1.text = "누른 횟수 %d".format(++count)
        }

        tv2.setOnClickListener{
            val toast = Toast.makeText(this, "안녕", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}