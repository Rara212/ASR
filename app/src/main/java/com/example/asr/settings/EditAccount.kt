package com.example.asr.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.asr.R
import com.example.asr.homepage.MainActivity
import com.example.asr.homepage.btnAdd

class EditAccount : AppCompatActivity() {

    lateinit var btnDone : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account)

        btnDone = findViewById(R.id.btnDone)

        btnDone.setOnClickListener {
            Intent(this, SettingActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}