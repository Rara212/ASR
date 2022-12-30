package com.example.asr.welcomepage

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.asr.R
import com.example.asr.api.RetrofitHelper
import com.example.asr.api.UserApi
import com.example.asr.data.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class CreateAccount : AppCompatActivity() {

    /*1.Set up variables that will be used*/
    lateinit var btnContinue: Button
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText

    val apiKey = ""
    val token = "Bearer $apiKey"

    /*2.Create calling api function*/
    val todoApi = RetrofitHelper.getInstance().create(UserApi::class.java)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        /*3.Define the references of the variables*/
        btnContinue = findViewById(R.id.btnCont);
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)

        /*5.Save new account data to database*/
        btnContinue.setOnClickListener {
            signUp(etEmail.text.toString(), etPassword.text.toString())
            Intent(this, SigninActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    /*4.Create signUp function*/
    private fun signUp(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var data = Users(email = email, password = password)
            var response = todoApi.signUp(token = token, apiKey = apiKey, data = data)

            val bodyResponse = if(response.code() == 200) {
                response.body()?.string()
            } else {
                response.errorBody()?.string().toString()
            }

            var failed = false
            val jsonResponse = JSONObject(bodyResponse)
            if(jsonResponse.keys().asSequence().toList().contains("error")) {
                failed = true
            }

            var msg = ""
            if (!failed) {
                msg = "Successfully Sign Up!"
            } else {
                var errorMessage = jsonResponse.get("error_description")
                msg += errorMessage
            }

            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(
                    applicationContext,
                    msg,
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
        }
    }
}


