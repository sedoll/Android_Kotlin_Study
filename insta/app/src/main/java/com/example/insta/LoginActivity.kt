package com.example.insta

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class LoginActivity : AppCompatActivity() {
    private lateinit var et_email:EditText
    private lateinit var et_pwd:EditText
    private lateinit var btn_eamil:Button
    private lateinit var btn_facebook:Button
    private lateinit var btn_google:Button

    private lateinit var mAuth:FirebaseAuth

    private var callbackManager:CallbackManager? = null
    private lateinit var loginManager:LoginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        et_email = findViewById(R.id.et_email)
        et_pwd = findViewById(R.id.et_pwd)

        btn_eamil = findViewById(R.id.btn_login)
        btn_eamil.setOnClickListener{
            val str_email = et_email.text.toString()
            val str_pwd = et_pwd.text.toString()
            try{
                signAndSignUp(str_email, str_pwd)
            } catch (e:java.lang.Exception) {
                Toast.makeText(this, "아이디와 비밀번호를 제대로 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }

        btn_facebook = findViewById(R.id.btn_facebook)
        btn_facebook.setOnClickListener {
//            printHashKey()
            signInWithFacebook()
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email", "public_profile"))
        }

        btn_google = findViewById(R.id.btn_google)
        btn_google.setOnClickListener {
            signInWithGoogle()
        }
    }

    // 회원가입 진행
    fun signAndSignUp(email:String, pwd:String) {
        mAuth?.createUserWithEmailAndPassword(email, pwd)?.addOnCompleteListener(this) { task ->
            if (task.isSuccessful) { // 회원가입 성공
                movePage(task.result?.user)
            } else { // 회원가입 실패
                signInEmail(email, pwd)
            }
        }
    }
    
    // 로그인
    fun signInEmail(email:String, pwd:String) {
        mAuth?.signInWithEmailAndPassword(email, pwd)?.addOnCompleteListener(this) { task ->
            if (task.isSuccessful) { // 로그인 성공
                movePage(task.result?.user)
            } else { // 로그인 실패
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    // 페이지 이동
    fun movePage(user:FirebaseUser?) {
        if (user != null) {
            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    // 구글 로그인
    /** 사용자가 "Google로 로그인" 버튼을 클릭할 때 호출됩니다.
    사용자의 이메일 및 ID 토큰 요청과 같은 원하는 인증 옵션을 지정하기 위해 GoogleSignInOptions 객체를 생성합니다.
    옵션에 따라 GoogleSignInClient를 생성하고 로그인 의도를 얻습니다.
    마지막으로 signInLauncher.launch(signInIntent)를 사용하여 로그인 인텐트를 시작합니다. */
    private fun signInWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }

    // 구글 로그인 활동 결과 처리
    /** 사용자가 "Google로 로그인" 버튼을 클릭하면 signInWithGoogle() 함수가 호출됩니다.
    signInWithGoogle() 내부에서 googleSignInClient.signInIntent를 사용하여 로그인 인텐트를 가져옵니다.
    그런 다음 'signInLauncher'는 'signInLauncher.launch(signInIntent)'로 로그인 의도를 시작하는 데 사용됩니다.
    사용자에게 Google 로그인 화면이 표시됩니다.
    로그인 프로세스가 완료되면 로그인 활동의 결과로 'signInLauncher' 콜백이 트리거됩니다.
    signInLauncher 콜백 내에서 결과가 성공했는지 확인합니다(result.resultCode == RESULT_OK).
    로그인에 성공하면 결과에서 account 객체가 추출되고 firebaseAuthWithGoogle() 함수가 호출되어 획득한 Google ID 토큰을 사용하여 Firebase에 사용자를 인증합니다.
    로그인 과정에서 ApiException과 같은 오류가 발생하면 catch 블록에 기록됩니다. */
    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val intent = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                try {
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    Log.e("TAG", "Google sign-in failed", e)
                }
            }
        }

    // 구글 로그인 파이어베이스
    /**
     * 이 함수는 사용자가 Google 계정으로 성공적으로 로그인한 후에 호출됩니다.
    GoogleSignInAccount에서 얻은 idToken을 가져와 GoogleAuthProvider.getCredential()을 사용하여 GoogleAuthProvider 자격 증명을 생성합니다.
    Firebase signInWithCredential() 메서드를 사용하여 획득한 자격 증명으로 사용자를 인증합니다.
    인증 결과는 'addOnCompleteListener' 콜백에서 처리되며, 여기에서 로그인 성공 여부를 확인하고 인증된 사용자의 정보에 액세스할 수 있습니다. */
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    Log.d("TAG", "Signed in with Google: ${user?.displayName}")
                    movePage(user) // 완료 후 페이지 이동
                } else {
                    Log.e("TAG", "Google sign-in failed", task.exception)
                }
            }
    }

    // 페이스북
    open fun printHashKey() {
        try {
            val info: PackageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey: String = String(Base64.encode(md.digest(), 0))
                Log.i(TAG, "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "printHashKey()", e)
        } catch (e: Exception) {
            Log.e(TAG, "printHashKey()", e)
        }
    }

    fun signInWithFacebook() {
        callbackManager = CallbackManager.Factory.create()

        // Register the callback for Facebook login
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                // Login successful
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                // Login canceled by user
                Log.d("FacebookLogin", "Login canceled")
            }

            override fun onError(error: FacebookException) {
                // Login error
                Log.e("FacebookLogin", "Login error: ${error.message}")
            }
        })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        // You can use the Facebook access token to authenticate with your server or perform other operations
        val accessToken = token.token
        Log.d("FacebookLogin", "Access Token: $accessToken")
    }

}