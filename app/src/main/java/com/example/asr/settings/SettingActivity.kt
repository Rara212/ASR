package com.example.asr.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.asr.R
import com.example.asr.welcomepage.SigninActivity
import com.example.asr.welcomepage.WelcomeActivity

class SettingActivity : AppCompatActivity() {

    lateinit var btnAccount: ImageButton
    lateinit var btnAboutus: ImageButton
    lateinit var btnLogout: ImageButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        btnAccount = findViewById(R.id.btnAccount)
        btnAboutus = findViewById(R.id.btnAboutUs)
        btnLogout = findViewById(R.id.btnLogout)

        val sharedPreference =  getSharedPreferences(
            "app_preference", Context.MODE_PRIVATE
        )

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
                var editor = sharedPreference.edit()
                editor.clear()
                editor.remove("email")
                editor.commit()

                val intent = Intent(this, SigninActivity::class.java)
                startActivity(intent)

                finish()
        }
    }
}