package com.example.asr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class SettingActivity : AppCompatActivity() {

    lateinit var btnAccount: ImageButton
    lateinit var btnAboutus: ImageButton
    lateinit var btnLogout: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        btnAccount = findViewById(R.id.imageButton11)
        btnAboutus = findViewById(R.id.imageButton13)
        btnLogout = findViewById(R.id.imageButton14)

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