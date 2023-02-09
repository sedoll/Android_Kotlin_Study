package org.techtown.login

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        val tv = findViewById<TextView>(R.id.resultTv)
        val list1 = listOf<String>("email", "pw", "name", "age")
        var result = ""

        for(item in list1) {
            if(intent.hasExtra(item) && intent.getStringExtra(item) != "") {
                result += intent.getStringExtra(item) + "\n"
            } else {
                result = "에러가 발생하였습니다."
                break
            }
        }

        tv.text = result

    }
}