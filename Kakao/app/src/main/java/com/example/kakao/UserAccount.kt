package com.example.kakao

/*
* 사용자 계정 정보 모델 클래스
* */
class UserAccount {
    private var idToken:String? = null // firebase 고유 토큰 정보
    private var emailId: String? = null // 이메일
    private var password: String? = null // 비밀번호

    constructor() {

    }

    fun setIdToken(token:String) {
        idToken = token
    }

    fun setEmailId(email: String?) {
        emailId = email
    }

    fun setPassword(pwd: String) {
        password = pwd
    }

    fun getIdToken(): String? {
        return idToken
    }

    fun getEmailId(): String? {
        return emailId
    }

    fun getPassword(): String? {
        return password
    }


}