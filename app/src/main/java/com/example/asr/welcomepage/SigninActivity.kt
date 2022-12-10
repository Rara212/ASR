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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class SigninActivity : AppCompatActivity() {

    lateinit var btnLogIn: Button
    lateinit var Email: EditText

    val apiKey = ""
    val token = "Bearer $apiKey"

    val todoApi = RetrofitHelper.getInstance() create (UserApi::class.java)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        btnLogIn = findViewById(R.id.btnLogIn)
        Email = findViewById(R.id.etUsername)
        btnLogIn.setOnClickListener {

            if (Email.text.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Harap isi nama terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            var Email = Email.text.toString()

            val sharedPreference = getSharedPreferences(
                "app_preference", Context.MODE_PRIVATE
            )

            var editor = sharedPreference.edit()
            editor.putString("email", Email)
            editor.commit()

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("result", Email)
            startActivity(intent)
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
                    var email = jsonResponse.getJSONObject("user").get("email")
                    msg = "Successfully login! Welcome back: $email"
                } else {
                    msg += jsonResponse.get("error_description").toString()
                }

                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(
                        applicationContext,
                        msg,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}