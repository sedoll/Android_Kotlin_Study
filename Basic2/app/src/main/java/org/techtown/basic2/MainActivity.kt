package org.techtown.basic2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var name:String = ""
    var mobile:Int = 0
    var test1 = 0 // 타입을 넣지 않으면 알아서 추론을 하여 자료형을 결정한다.
    lateinit var test2:String // 이런식으로 초기값을 선언하지 않을 꺼면 lateinit을 앞에 넣어준다.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            // 코틀린 변수 선언
            name = input1.text.toString()
            mobile = input2.text.toString().toInt() // 문자열을 정수형으로 형변환

            //변수 형식
            // var 변수이름:자료형 = 초기값, var은 변수
            // val 변수이름:자료형 = 초기값, val은 상수
            var id1:String = ""
            val id2:String = ""

            // 토스트 메세지 출력
            Toast.makeText(applicationContext, "이름: $name, 전화번호: $mobile",Toast.LENGTH_LONG).show()
        }
    }
}