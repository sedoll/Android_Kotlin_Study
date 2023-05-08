package org.techtown.foodtable

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: ListViewAdapter
    private lateinit var items: MutableList<ListViewItem>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dor = findViewById<Button>(R.id.button_dormitory)
        val stu = findViewById<Button>(R.id.button_student)
        val wor = findViewById<Button>(R.id.button_worker)

        dor.setOnClickListener {
            val intent = Intent(this, Dormitory::class.java)
            startActivity(intent)
        }

        stu.setOnClickListener {
            val intent = Intent(this, Student::class.java)
            startActivity(intent)
        }

        wor.setOnClickListener {
            val intent = Intent(this, Worker::class.java)
            startActivity(intent)
        }

    }

    fun toastMsg(content:String) {
        Toast.makeText(this, content+" 식단표를 불러옵니다.", Toast.LENGTH_SHORT).show()
    }
}