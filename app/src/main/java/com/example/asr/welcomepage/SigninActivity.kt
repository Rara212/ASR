package com.example.asr.welcomepage

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.asr.homepage.MainActivity
import com.example.asr.R

class SigninActivity : AppCompatActivity() {

    lateinit var btnLogIn : Button
    lateinit var etUsername : EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        
        btnLogIn = findViewById(R.id.btnLogIn)
        etUsername = findViewById(R.id.etUsername)
        btnLogIn.setOnClickListener {

            if (etUsername.text.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Harap isi nama terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            var username = etUsername.text.toString()

            val sharedPreference =  getSharedPreferences(
                "app_preference", Context.MODE_PRIVATE
            )

            var editor = sharedPreference.edit()
            editor.putString("username", username)
            editor.commit()

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("result", username)
            startActivity(intent)
            }
        }
    }