package com.example.asr.homepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.asr.R

class AddActivity : AppCompatActivity() {

    lateinit var btnAdd : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        btnAdd = findViewById(R.id.btnAdd)

        btnAdd.setOnClickListener{
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }


    }
}