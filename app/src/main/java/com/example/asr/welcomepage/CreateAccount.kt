package com.example.asr.welcomepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.asr.R

class CreateAccount : AppCompatActivity() {

    lateinit var btnContinue: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener {
            Intent(this, SigninActivity::class.java).also {
                startActivity(it)

            }
        }
    }
}


