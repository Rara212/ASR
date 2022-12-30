package com.example.asr.welcomepage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.asr.homepage.MainActivity
import com.example.asr.R

class WelcomeActivity : AppCompatActivity() {

    /*1.Set up variables that will be used*/
    lateinit var btnSignIn : Button
    lateinit var btnNewAccount: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        /*2.Define the references of the variables*/
        btnSignIn = findViewById(R.id.btnSignIn)
        btnNewAccount = findViewById(R.id.btnNewAccount)

        /*4.Fetch data if exists and bring them to main*/
        val sharedPreference =  getSharedPreferences(
            "app_preference", Context.MODE_PRIVATE
        )

        var email = sharedPreference.getString("email", "").toString()
        var userid = sharedPreference.getString("userid", "").toString()

        if (email.isNotEmpty()&&userid.isNotEmpty()) {
            goToHomePage()
        }

        /*5.Moving to sign in activity*/
        btnSignIn.setOnClickListener {
            Intent(this, SigninActivity::class.java).also {
                startActivity(it)
            }
        }
        /*6.Moving to create account*/
        btnNewAccount.setOnClickListener {
            Intent(this, CreateAccount::class.java).also {
                startActivity(it)
            }
        }
    }

    /*3.Direct user to Main function*/
    private fun goToHomePage() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}