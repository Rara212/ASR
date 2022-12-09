package com.example.asr.welcomepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import com.example.asr.R
import com.example.asr.api.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateAccount : AppCompatActivity() {

    lateinit var btnContinue: ImageButton
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText

    val apiKey = ""
    val token = "Bearer $apiKey"

    /*val todoApi = RetrofitHelper.getInstance().create(UserApi::class.java)*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        btnContinue = findViewById(R.id.btnContinue);
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)

        btnContinue.setOnClickListener {
            signUp(etEmail.text.toString(), etPassword.text.toString())
            /*Intent(this, SigninActivity::class.java).also {
                startActivity(it)*/
            }
        }

    private fun signUp(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var data = Users(email = email, password = password)
            var response = todoApi.signUp(token = token, apiKey = apiKey, data = data)

            val bodyResponse = if(response.code() == 200) {
                response.body()?.string()
            } else {
                response.errorBody()?.string().toString()
            }
        }
    }
}


