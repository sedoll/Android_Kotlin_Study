package org.techtown.movie

import android.annotation.SuppressLint
import android.graphics.drawable.AdaptiveIconDrawable
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class SubActivity : AppCompatActivity() {

    private lateinit var listView2: ListView
    private lateinit var adapter: ListViewAdapter
    private lateinit var items: MutableList<ListViewItem>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.subactivity)

        // 레이아웃 파일에서 ListView를 찾아옵니다.
        listView2 = findViewById(R.id.list_view)
            // 아이템 리스트를 초기화합니다.
        items = mutableListOf()

        // 어댑터를 초기화합니다.
        adapter = ListViewAdapter(items)

        // ListView에 어댑터를 설정합니다.
        listView2.adapter = adapter

        val movieCd = intent.getStringExtra("movieCode")

        if (movieCd != null) {
            jsonTask(movieCd)
        }

//        (tv as TextView?)!!.text = "영화 코드" + movieCd
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UseCompatLoadingForDrawables")
    fun jsonTask(movieCd: String) {
        val queue = Volley.newRequestQueue(this)

        val key = BuildConfig.MOVIE_API_KEY
        val url = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=${key}&movieCd=${movieCd}"

        var data = mutableListOf<String>()

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                // 전체 데이터 추출
                val movieInfoResult = response.getJSONObject("movieInfoResult")
                val movieInfo = movieInfoResult.getJSONObject("movieInfo")
                val movieNm = movieInfo.getString("movieNm")
                val movieNmEn = movieInfo.getString("movieNmEn")
                val showTm = movieInfo.getString("showTm")
                val openDt = movieInfo.optString("openDt")

                listItem(movieNm, movieNmEn, "러닝타임: " + showTm + "분  개봉일: " + openDt.slice(2..3) + "-" + openDt.slice(4..5)+ "-" + openDt.slice(6..7))

                val nations = movieInfo.getJSONArray("nations")
                val genres = movieInfo.getJSONArray("genres")
                val directors = movieInfo.getJSONArray("directors")
                val actors = movieInfo.getJSONArray("actors")
                val audits = movieInfo.getJSONArray("audits")
                val companys = movieInfo.getJSONArray("companys")

                // 감독 정보 추출
                val directorNames = mutableListOf<String>()
                for (i in 0 until directors.length()) {
                    val director = directors.getJSONObject(i)
                    val directorNm = director.getString("peopleNm")
                    directorNames.add(directorNm)
                    listItem(directorNm, "감독", null)
                }

                // 출연 배우 정보 추출
                val actorNames = mutableListOf<String>()
                for (i in 0 until actors.length()) {
                    val actor = actors.getJSONObject(i)
                    val actorNm = actor.getString("peopleNm")
                    actorNames.add(actorNm)
                    listItem(actorNm, "배우", null)
                }

                // 제작사 정보 추출
                val companyNames = mutableListOf<String>()
                for (i in 0 until companys.length()) {
                    val company = companys.getJSONObject(i)
                    val companyNm = company.getString("companyNm")
                    companyNames.add(companyNm)
                    listItem(companyNm, "제작", null)
                }

                // 출력
                println("영화 코드: $movieCd")
                println("영화 제목: $movieNm")
                println("영화 영문 제목: $movieNmEn")
                println("영화 재생 시간: $showTm 분")
                println("개봉일: $openDt")
                println("제작 국가: $nations")
                println("장르: $genres")
                println("감독: $directorNames")
                println("출연 배우: $actorNames")
                println("등급: $audits")
                println("제작사: $companyNames")

            },
            Response.ErrorListener { error ->
                // 요청 실패 시 처리할 코드
                println(error.message)
            })

        queue.add(jsonObjectRequest)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun listItem(title:String?, subtitle1:String?, subtitle2:String?) {
        val drawable = resources.getDrawable(R.drawable.ic_launcher_foreground, null)
        val item = ListViewItem(drawable, title, subtitle1,  subtitle2)
        items.add(item)
        adapter.notifyDataSetChanged()
    }

}