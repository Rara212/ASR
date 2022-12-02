package com.example.asr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class WelcomeActivity : AppCompatActivity() {

    lateinit var btnSignIn : Button
    lateinit var btnNewAccount: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        btnSignIn = findViewById(R.id.btnSignIn)
        btnNewAccount = findViewById(R.id.btnNewAccount)

        /*Moving to main activity*/
        btnSignIn.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
        /*Moving to creat account*/
        btnNewAccount.setOnClickListener {
            Intent(this, CreateAccount::class.java).also {
                startActivity(it)
            }
        }
    }
}