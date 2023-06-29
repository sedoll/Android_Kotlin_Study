package com.example.insta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.insta.navigation.AlarmFragment
import com.example.insta.navigation.DetailViewFragment
import com.example.insta.navigation.GridFragment
import com.example.insta.navigation.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                // Handle item selection here
                when (item.itemId) {
                    R.id.action_home -> {
                        var detailViewFragment = DetailViewFragment()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content, detailViewFragment).commit()
                        return true
                    }
                    R.id.action_search -> {
                        var gridFragment = GridFragment()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content, gridFragment).commit()
                        return true
                    }
                    R.id.action_add_photo -> {
                        return true
                    }
                    R.id.action_favorite_alarm -> {
                        var alarmFragment = AlarmFragment()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content, alarmFragment).commit()
                        return true
                    }
                    R.id.action_account -> {
                        var userFragment = UserFragment()
                        supportFragmentManager.beginTransaction().replace(R.id.main_content, userFragment).commit()
                        return true
                    }
                }
                return false
            }
        })
    }

}