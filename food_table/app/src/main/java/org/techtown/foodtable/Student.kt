package org.techtown.foodtable

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class Student : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: ListViewAdapter
    private lateinit var items: MutableList<ListViewItem>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student)
        setTitle("학생 식당")

        // 레이아웃 파일에서 ListView를 찾아옵니다.
        listView = findViewById(R.id.list_view)

        // 아이템 리스트를 초기화합니다.
        items = mutableListOf()

        // 어댑터를 초기화합니다.
        adapter = ListViewAdapter(items)

        // ListView에 어댑터를 설정합니다.
        listView.adapter = adapter

        val btn1 = findViewById<Button>(R.id.button1)
        val btn2 = findViewById<Button>(R.id.button2)
        val btn3 = findViewById<Button>(R.id.button3)

        val camp = mapOf("신관" to "13155", "천안" to "13157", "예산" to "13159")
        var thread:Any

        // 은행사/비전 버튼 클릭
        btn1.setOnClickListener {
            val text = "신관"
            items.clear()
            toastMsg(text)
            thread = Thread({parshing(text, camp[text].toString())}).start()
        }

        // 드림 버튼 클릭
        btn2.setOnClickListener {
            val text = "천안"
            items.clear()
            toastMsg(text)
            thread = Thread({parshing(text, camp[text].toString())}).start()
        }

        // 천안 버튼 클릭
        btn3.setOnClickListener {
            val text = "예산"
            items.clear()
            toastMsg(text)
            thread = Thread({parshing(text, camp[text].toString())}).start()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parshing(name:String, value:String) {
        try {
            val url = "https://www.kongju.ac.kr/kongju/$value/subview.do"
            val document: Document = Jsoup.connect(url).get()

            // 크롤링할 테이블 선택
            val tables = document.select("table")

            for (i in 1..7) {
                val title = tables.select("th")[i]
                var str:String
                var md:List<String>
                var date = ""
                var day = ""
                var launch = ""
                var diner = ""
                // 날짜 추출
                if(name=="예산") {
                    str = title.select("span").text()
                    md = str.split(".")
                    date = md[1] + "월 " + md[2] + "일"
                    day = title.select("p").text()
                    day = day.replace("(", "").replace(")", "").replace(" ", "")
                    launch = tables.select("td")[i-1].text()
                    diner = tables.select("td")[i+6].text()
                } else { // 신관, 천안
                    str = title.text()
                    str = str.replace("(", ".").replace(")", ".").replace(" ", "")
                    md = str.split(".")
                    date = md[1] + "월 " + md[2] + "일"
                    day = md[3]
                    launch = tables.select("td")[i-1].text()
                    diner = ""
                }

                if(launch == "등록된 식단내용이(가) 없습니다.") {
                    launch = ""
                }

                if(diner == "등록된 식단내용이(가) 없습니다.") {
                    diner = ""
                }

//                println("$date\n$day\n$launch")
                val item = ListViewItem(day, date, "", launch, diner)

                runOnUiThread {
                    items.add(item)
                    adapter.notifyDataSetChanged()
                }
            }
        } catch (e:Exception) {
            println(e)
        }
    }

    fun toastMsg(content:String) {
        Toast.makeText(this, content+" 학생 식당 식단표를 불러옵니다.", Toast.LENGTH_SHORT).show()
    }
}