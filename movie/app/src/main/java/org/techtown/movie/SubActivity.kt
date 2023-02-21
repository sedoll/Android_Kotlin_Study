package org.techtown.movie

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class SubActivity : AppCompatActivity() {

    private lateinit var tv : Any

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.subactivity)

        val movieCd = intent.getStringExtra("movieCode")
        tv = findViewById<TextView>(R.id.tv1)

        if (movieCd != null) {
            jsonTask(movieCd)
        }

        (tv as TextView?)!!.text = "영화 코드" + movieCd
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UseCompatLoadingForDrawables")
    fun jsonTask(movieCd: String) {
        val queue = Volley.newRequestQueue(this)

        val key = BuildConfig.MOVIE_API_KEY
        val url = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=${key}&movieCd=${movieCd}"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                // 전체 데이터 추출
                val movieInfoResult = response.getJSONObject("movieInfoResult")
                val movieInfo = movieInfoResult.getJSONObject("movieInfo")
                val movieCd = movieInfo.getString("movieCd")
                val movieNm = movieInfo.getString("movieNm")
                val movieNmEn = movieInfo.getString("movieNmEn")
                val movieNmOg = movieInfo.optString("movieNmOg")
                val showTm = movieInfo.getString("showTm")
                val prdtYear = movieInfo.optString("prdtYear")
                val openDt = movieInfo.optString("openDt")
                val prdtStatNm = movieInfo.optString("prdtStatNm")
                val typeNm = movieInfo.optString("typeNm")
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
                }

                // 출연 배우 정보 추출
                val actorNames = mutableListOf<String>()
                for (i in 0 until actors.length()) {
                    val actor = actors.getJSONObject(i)
                    val actorNm = actor.getString("peopleNm")
                    actorNames.add(actorNm)
                }

                // 제작사 정보 추출
                val companyNames = mutableListOf<String>()
                for (i in 0 until companys.length()) {
                    val company = companys.getJSONObject(i)
                    val companyNm = company.getString("companyNm")
                    companyNames.add(companyNm)
                }

                // 출력
                println("영화 코드: $movieCd")
                println("영화 제목: $movieNm")
                println("영화 영문 제목: $movieNmEn")
                println("영화 원제: $movieNmOg")
                println("영화 재생 시간: $showTm 분")
                println("제작 년도: $prdtYear")
                println("개봉일: $openDt")
                println("제작 상태: $prdtStatNm")
                println("영화 유형: $typeNm")
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
}