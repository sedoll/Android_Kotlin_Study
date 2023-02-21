package org.techtown.movie

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: ListViewAdapter
    private lateinit var items: MutableList<ListViewItem>

    val map: MutableMap<Int?, String?> = HashMap()

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

        // 버튼을 클릭했을 때 아이템을 추가합니다.
        val button = findViewById<Button>(R.id.add_button)
        button.setOnClickListener {
            items.clear() // 아이템 리스트 초기화
            jsonTask()
            Toast.makeText(this, "박스오피스 조회", Toast.LENGTH_SHORT).show()
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            val movieCode = map.get(position).toString()
            val intent = Intent(this, SubActivity::class.java)
            intent.putExtra("movieCode", movieCode)
            startActivity(intent)
            println("영화 코드: " + movieCode)
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables", "SimpleDateFormat")
    fun jsonTask() {
        val queue = Volley.newRequestQueue(this)

        val key = BuildConfig.MOVIE_API_KEY
        val time = time()
        val url = "https://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=${key}&targetDt=${time}"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                // JSON 데이터를 가져오는 데 성공한 경우 실행되는 코드
                val movieList = response.getJSONObject("boxOfficeResult").getJSONArray("dailyBoxOfficeList")

                for (i in 0 until movieList.length()) {
                    val movie = movieList.getJSONObject(i)
                    val decimal = DecimalFormat("#,###") // 천 단위 / 구분을 위해 , 표시
                    val rank = movie.getString("rank") // 예매 순위
                    val movieNm = movie.getString("movieNm") // 영화 이름
                    val openDt = movie.getString("openDt") // 영화 개봉날짜
                    val audiAcc = decimal.format(movie.getString("audiAcc").toInt()) // 천 단위 적용, 무조건 정수타입만 가능
                    val audiCnt = decimal.format(movie.getString("audiCnt").toInt()) // 천 단위 적용, 무조건 정수타입만 가능
                    val movieCd = movie.getString("movieCd") // 영화 코드 / ListViewitem을 누른 경우 해당 영화에 대한 소개 페이지로 넘어가기 위해 영화 코드를 받음

                    map[rank.toInt()-1] = movieCd // position이 0부터 시작하므로 키를 0부터 값 대입

                    // TODO: 파싱한 데이터를 활용하는 코드 작성
                    println("출력: ${rank} ${movieNm} ${openDt} ${audiAcc}")
                    val drawable = resources.getDrawable(R.drawable.ic_launcher_foreground, null)
                    val item = ListViewItem(drawable, rank + ". " + movieNm,"개봉일: " + openDt,  "관객 / 어제: " + audiCnt + "  누적: " + audiAcc + " 명")
                    items.add(item)
                    adapter.notifyDataSetChanged()
                }
            },
            { error ->
                // JSON 데이터를 가져오는 데 실패한 경우 실행되는 코드
                Log.e("TAG", "Error: $error")
            })

        queue.add(jsonObjectRequest)
    }

    @SuppressLint("SimpleDateFormat")
    fun time(): Any {
        val calendar = Calendar.getInstance()
        val today = Date()
        calendar.time = today
        calendar.add(Calendar.DAY_OF_YEAR, -1) //변경하고 싶은 원하는 날짜 수를 넣어 준다.
        val date = SimpleDateFormat("yyyyMMdd").format(calendar.time)
        println("시간: " + date)
        return date
    }


}