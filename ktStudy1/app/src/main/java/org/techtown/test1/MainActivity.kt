package org.techtown.test1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.NumberFormatException
import java.util.*

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

        val items = listOf("사과", "바나나", "키위")
        var index = 0
        while (index < items.size) {
            println("item at %d is %s".format(index, items[index]))
            index++
        }

//        println(parseInt("10"))
//        println(parseInt("10.5"))
//        println(parseInt("안녕하세요"))

//        println(getStringLength("10"))
//        println(getStringLength("안녕하세요"))
//        println(getStringLength(10))
//        println(getStringLength(true))

        println(describe(1))
        println(describe("Hello"))
        println(describe(90000L))
        println(describe(true))
        println(describe("안녕"))

        dotdot()

        upDawnStep()

        listFilter()
    }

    // 입력값이 String
    fun parseInt(str: String): Int? {
        var num : Int? = null

        try{
            num = str.toInt()
        } catch (e: NumberFormatException) {
            return null
        }
        return num
    }

    fun getStringLength(obj: Any): Int? {
        if (obj is String && obj.length > 0)
            return obj.length
        return null
    }

    fun describe(obj: Any): String =
        when(obj) {
            1           -> "One"
            "Hello"     -> "Greeting"
            is Long     -> "Long"
            !is String  -> "Not a String"
            else        -> "UnKnown"
        }

    // a..b은 a~b 사이의 범위를 말한다.
    fun dotdot() {
        val x = 10
        val y = 5
        if (x in 1..y+1) {
            println("x: %d는 1~%d범위 안에 속합니다.".format(x, y+1))
        } else {
            println("x: %d는 1~%d범위 안에 속하지 않습니다.".format(x, y+1))
        }
    }
    
    // 위 아래 몇 개씩 이동
    fun upDawnStep() {
        // upstep
        for (x in 1..10 step 2) {
            println(x)
        }
        // downstep
        for (x in 9 downTo 0 step 3) {
            println(x)
        }
    }

    // 리스트 필터
    fun listFilter() {
        val fruits = listOf("banana", "avocado", "apple", "kiwi")
        fruits
            .filter { it.startsWith("a") } // a로 시작하는 것들을 추출
            .sortedBy { it } // avocado와 apple을 오름차순으로 정렬
            .map { it.uppercase() } // avocado와 apple을 대문자로
            .forEach { println(it) } // 출력
    }




}