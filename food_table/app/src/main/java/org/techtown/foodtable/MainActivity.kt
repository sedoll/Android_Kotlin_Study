package org.techtown.foodtable

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.widget.*
import androidx.annotation.RequiresApi
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: ListViewAdapter
    private lateinit var items: MutableList<ListViewItem>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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


        val camp = mapOf("은행사" to "041301", "드림" to "041302", "천안" to "041303")
        var thread:Any
        // 은행사/비전 버튼 클릭
        btn1.setOnClickListener {
            Toast.makeText(this, "은행사/비전 생활관 식단표를 불러옵니다.", Toast.LENGTH_SHORT).show()
            items.clear()
            thread = Thread({parshing(camp["은행사"].toString())}).start()
        }

        // 드림 버튼 클릭
        btn2.setOnClickListener {
            items.clear()
            Toast.makeText(this, "드림 생활관 식단표를 불러옵니다.", Toast.LENGTH_SHORT).show()
            thread = Thread({parshing(camp["드림"].toString())}).start()
        }

        // 천안 버튼 클릭
        btn3.setOnClickListener {
            items.clear()
            Toast.makeText(this, "천안 생활관 식단표를 불러옵니다.", Toast.LENGTH_SHORT).show()
            thread = Thread({parshing(camp["천안"].toString())}).start()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parshing(value:String) {

        val url = "https://dormi.kongju.ac.kr/HOME/sub.php?code=$value" // 파싱할 URL
        val document: Document = Jsoup.connect(url).get() // 웹 페이지 가져오기

        val foodBoxes = document.select("tr")

        // 요일별로 parshing
        for (i in 1 until 8) {

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
}