package com.example.asr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class CreateAccount : AppCompatActivity() {

    lateinit var btnContinue: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        btnContinue = findViewById(R.id.imageButton4);

        btnContinue.setOnClickListener {
            Intent(this, SigninActivity::class.java).also {
                startActivity(it)

            }
        }
    }
}


