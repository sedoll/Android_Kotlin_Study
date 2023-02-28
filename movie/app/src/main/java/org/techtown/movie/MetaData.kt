package org.techtown.movie

import com.google.gson.annotations.SerializedName

data class MetaData (
    @SerializedName("total_count")
    val totalCount: Int?,

    @SerializedName("is_end")
    val isEnd: Boolean?
    )

//해당 코드는 메인에 집어넣어라

// val thread = Thread({test( "영화 포스터 " + movieNm)}).start() // 쓰레드를 사용해야 Restapi를 사용할 때 오류가 생기지 않음

//fun test(query:String) {
//    val key = "KakaoAK key"
//    val page = 1
//    val size = 1
//    val service = retrofit.create(KakaoSearchService::class.java)
//    val call = service.searchImage(key, query, page, size)
//    val response = call.execute()
//    if (response.isSuccessful) {
//        val responseBody = response.body()
//        println("${query}: "+responseBody)
//    } else {
//        val responseCode = response.code()
//        val errorBody = response.errorBody()?.string()
//        println("Error: $responseCode, $errorBody")
//    }
//}