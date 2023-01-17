package com.androiddevs.mvvmnewsapp.util

// 상속 받는 자식 클래스의 종류를 제한하는 클래스
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}