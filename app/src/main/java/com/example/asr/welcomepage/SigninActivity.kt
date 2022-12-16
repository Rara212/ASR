package com.example.asr.welcomepage

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.asr.homepage.MainActivity
import com.example.asr.R
import com.example.asr.api.RetrofitHelper
import com.example.asr.api.UserApi
import com.example.asr.data.Users
import com.example.asr.homepage.settings.SettingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class SigninActivity : AppCompatActivity() {

    lateinit var btnLogIn: Button
    lateinit var Email: EditText
    lateinit var etPassword: EditText

    val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1maWZ5d2JnY3FrZmFzbmhjaWJkIiwicm9sZSI6ImFub24iLCJpYXQiOjE2Njk5NTUxMzYsImV4cCI6MTk4NTUzMTEzNn0.EqjggAQEzg4acUUzrwVxncdxNOiGP3VYO9Wd2yRz_LA"
    val token = "Bearer $apiKey"

    val todoApi = RetrofitHelper.getInstance().create(UserApi::class.java)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        btnLogIn = findViewById(R.id.btnLogIn)
        Email = findViewById(R.id.Email)
        etPassword = findViewById(R.id.etPassword)

        btnLogIn.setOnClickListener {
           signIn(Email.text.toString(), etPassword.text.toString())
        }
    }

    private fun signIn(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val data = Users(email = email, password = password)
            val response = todoApi.signIn(token = token, apiKey = apiKey, data = data)

            val bodyResponse = if (response.code() == 200) {
                response.body()?.string()
            } else {
                response.errorBody()?.string().toString()
            }

            var failed = false
            val jsonResponse = JSONObject(bodyResponse)
            if (jsonResponse.keys().asSequence().toList().contains("error")) {
                failed = true
            }

            var msg = ""
            if (!failed) {
                var email = jsonResponse.getJSONObject("user").get("email").toString()
                msg = "Successfully login! Welcome back: $email"

                val sharedPreference = getSharedPreferences(
                    "app_preference", Context.MODE_PRIVATE
                )

                var editor = sharedPreference.edit()
                editor.putString("email", email)
                editor.commit()

            } else {
                msg += jsonResponse.get("error_description").toString()
            }

            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(
                    applicationContext,
                    msg,
                    Toast.LENGTH_SHORT
                ).show()

                if (!failed) {
                    goToHome()
                }
            }
        }
    }

    private fun goToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}