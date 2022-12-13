package com.example.asr.welcomepage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.asr.homepage.MainActivity
import com.example.asr.R

class WelcomeActivity : AppCompatActivity() {

    lateinit var btnSignIn : Button
    lateinit var btnNewAccount: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        btnSignIn = findViewById(R.id.btnSignIn)
        btnNewAccount = findViewById(R.id.btnNewAccount)

        val sharedPreference =  getSharedPreferences(
            "app_preference", Context.MODE_PRIVATE
        )

        var email = sharedPreference.getString("email", "").toString()
        if (email.isNotEmpty()) {
           goToHomePage()
        }

        /*Moving to main activity*/
        btnSignIn.setOnClickListener {
            Intent(this, SigninActivity::class.java).also {
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
    private fun goToHomePage() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}