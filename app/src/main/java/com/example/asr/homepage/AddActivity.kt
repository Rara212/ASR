package com.example.asr.homepage

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.asr.*


class AddActivity : AppCompatActivity()

lateinit var btnAdd: Button

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add)

    btnAdd = findViewById(R.id.buttonADD)

    btnAdd.setOnClickListener {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }
    }
}