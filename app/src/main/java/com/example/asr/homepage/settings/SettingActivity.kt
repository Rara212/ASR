package com.example.asr.homepage.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.asr.R
import com.example.asr.homepage.MainActivity
import com.example.asr.welcomepage.SigninActivity
import com.example.asr.welcomepage.WelcomeActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    lateinit var btnAccount: ImageButton
    lateinit var btnAboutus: ImageButton
    lateinit var btnBack: ImageButton


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        btnAccount = findViewById(R.id.btnAccount)
        btnAboutus = findViewById(R.id.btnAboutUs)
        btnBack = findViewById(R.id.btnbackSetting)

        btnAccount.setOnClickListener {
            Intent(this, EditAccount::class.java).also {
                startActivity(it)
            }
        }

        btnAboutus.setOnClickListener {
            Intent(this, AboutUs::class.java).also {
                startActivity(it)
            }
        }

        btnBack.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}