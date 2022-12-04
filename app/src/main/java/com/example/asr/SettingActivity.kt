package com.example.asr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SettingActivity : AppCompatActivity() {

    lateinit var btnAccount: Button
    lateinit var btnAboutus: Button
    lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

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

        btnLogout.setOnClickListener {
            Intent(this, WelcomeActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}