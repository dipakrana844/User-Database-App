package com.executor.userdatabaseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.executor.userdatabaseapp.DB.UserDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fabAddBtn.setOnClickListener {
            val intent = Intent(this, UserDetailActivity::class.java)
            startActivity(intent)
        }
    }
}