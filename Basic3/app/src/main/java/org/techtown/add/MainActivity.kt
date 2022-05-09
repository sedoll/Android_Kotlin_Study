package org.techtown.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.techtown.add.constants.mBonus // 패키지 변수
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    var first:Int = 0
    var second:Int = 0

    companion object { // 동반객체, 자바의 static
        const val BONUS = 100 // 상수 선언
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addBtn.setOnClickListener {
            var result = 0
            try{
                first = input1.text.toString().toInt()
                second = input2.text.toString().toInt()
            } catch (e: Exception) {

            } finally {
                // mBonus 패키지 변수
                result = first + second + MainActivity.BONUS + mBonus // 자동 import
                output.text = "결과: $result"
            }

            if(result == 20) {
                output.text = "더하기 결과가 20 입니다."
            }
            if(first == 10) { // 코틀린에서는 문자열에서도 똑같이 ==로 내용 비교 가능
                output.append("\n첫번째 입력된 값이 10입니다.")
            }

            val input1:Any = "안녕" // Any는 어느 타입이든 다 가능
            val input2:Any = 10

            if(input1 is String) { // 문자열인지 확인
                val output2:String = input1 as String // 문자열로 만들어라
                output.text = "input1은 문자열 자료형입니다."
            }
            
            val output3:String? = input1 as String? // String 타입으로 null 포함해서 래핑
        }
    }
}