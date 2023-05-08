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

class Worker : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: ListViewAdapter
    private lateinit var items: MutableList<ListViewItem>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.worker)
        setTitle("직원 식당")

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


        val camp = mapOf("신관" to "13156", "천안" to "13158", "예산" to "13160")
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

                    // 날짜 추출
                if(name=="예산") {
                    str = title.select("span").text()
                    md = str.split(".")
                    date = md[1] + "월 " + md[2] + "일"
                    day = title.select("p").text()
                    day = day.replace("(", "").replace(")", "").replace(" ", "")
                } else { // 신관, 천안
                    str = title.text()
                    str = str.replace("(", ".").replace(")", ".").replace(" ", "")
                    md = str.split(".")
                    date = md[1] + "월 " + md[2] + "일"
                    day = md[3]
                }

                // 점심
                var launch = tables.select("td")[i-1].text()
//                println("$date\n$day\n$launch")

                if(launch == "등록된 식단내용이(가) 없습니다.") {
                    launch = ""
                }

                val item = ListViewItem(day, date, "", launch, "")

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
        Toast.makeText(this, content+" 직원 식당 식단표를 불러옵니다.", Toast.LENGTH_SHORT).show()
    }
}