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

class Dormitory : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: ListViewAdapter
    private lateinit var items: MutableList<ListViewItem>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dormitory)
        setTitle("기숙사 식당")

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

        val camp = mapOf("은행사/비전" to "041301", "드림" to "041302", "천안" to "13163")
        var thread:Any

        // 은행사/비전 버튼 클릭
        btn1.setOnClickListener {
            val text = "은행사/비전"
            items.clear()
            toastMsg(text)
            thread = Thread({parshing(text, camp[text].toString())}).start()
        }

        // 드림 버튼 클릭
        btn2.setOnClickListener {
            val text = "드림"
            items.clear()
            toastMsg(text)
            thread = Thread({parshing(text, camp[text].toString())}).start()
        }

        // 천안 버튼 클릭
        btn3.setOnClickListener {
            val text = "천안"
            items.clear()
            toastMsg(text)
            thread = Thread({parshing(text, camp[text].toString())}).start()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parshing(name:String, value:String) {
        try {
            if (name=="천안") {
                val url = "https://www.kongju.ac.kr/kongju/$value/subview.do"
                val document: Document = Jsoup.connect(url).get()

                // 크롤링할 테이블 선택
                val tables = document.select("table")

                for (i in 1..7) {
                    val title = tables.select("th")[i]

                    // 날짜 추출
                    val str = title.select("span").text()
                    val md = str.split(".")
                    val date = md[1] + "월 " + md[2] + "일"
                    var day = title.select("p").text()
                    day = day.replace("(", "").replace(")", "").replace(" ", "")

                    // 점심
                    var launch = tables.select("td")[i-1].text()

                    // 저녁
                    var diner = tables.select("td")[i+6].text()

                    if(launch == "등록된 식단내용이(가) 없습니다.") {
                        launch = ""
                    }

                    if(diner == "등록된 식단내용이(가) 없습니다.") {
                        diner = ""
                    }

                    val item = ListViewItem(day, date, "", launch, diner)

                    runOnUiThread {
                        items.add(item)
                        adapter.notifyDataSetChanged()
                    }
                }

            } else {
                val url = "https://dormi.kongju.ac.kr/HOME/sub.php?code=$value" // 파싱할 URL
                val document: Document = Jsoup.connect(url).get() // 웹 페이지 가져오기

                val foodBoxes = document.select("tr")

                // 요일별로 parshing
                for (i in 1 .. 7) {

                    // 요일, 날짜, 아침, 점심, 저녁 순으로 parshing
                    val day = foodBoxes[i].select("td")[0].text()
                    val date = foodBoxes[i].select("td")[1].text()
                    val morning = foodBoxes[i].select("td")[2].text()
                    val launch = foodBoxes[i].select("td")[3].text()
                    val diner = foodBoxes[i].select("td")[4].text()
                    val item = ListViewItem(day, date, morning, launch, diner)

                    runOnUiThread {
                        items.add(item)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        } catch (e:Exception) {
            println(e)
        }
    }

    fun toastMsg(content:String) {
        Toast.makeText(this, content+" 기숙사 식당 식단표를 불러옵니다.", Toast.LENGTH_SHORT).show()
    }
}