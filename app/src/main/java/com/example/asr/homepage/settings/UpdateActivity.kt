package com.example.asr.homepage.settings

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.example.asr.R
import com.example.asr.api.ActivityAPI
import com.example.asr.api.RetrofitHelper
import com.example.asr.data.ActivityData
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateActivity : AppCompatActivity() {

    lateinit var etActivityUpdate: EditText
    lateinit var btnUpdate: Button

    lateinit var id : String
    val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1maWZ5d2JnY3FrZmFzbmhjaWJkIiwicm9sZSI6ImFub24iLCJpYXQiOjE2Njk5NTUxMzYsImV4cCI6MTk4NTUzMTEzNn0.EqjggAQEzg4acUUzrwVxncdxNOiGP3VYO9Wd2yRz_LA"
    val token = "Bearer $apiKey"

    val activityApi = RetrofitHelper.getInstance().create(ActivityAPI::class.java)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        etActivityUpdate = findViewById(R.id.etActivityUpdate)
        btnUpdate = findViewById(R.id.btnUpdate)

        id = intent.getStringExtra("activityid").toString()

        etActivity.setText(intent.getStringExtra("activity").toString())

        val sharedPreference = getSharedPreferences(
            "app_preference", Context.MODE_PRIVATE
        )

        var userid = sharedPreference.getString("userid", "[No userid found]")

        btnUpdate.setOnClickListener{
            CoroutineScope(Dispatchers.Main).launch {
                val checkedActivityRadioButtonId = rgType.checkedRadioButtonId
                val type= findViewById<RadioButton>(checkedActivityRadioButtonId)
                val data = ActivityData(userid = "$userid", activity = etActivityUpdate.text.toString(), category = type.text.toString())
                val response = activityApi.update(token = token, apiKey = apiKey, idQuery = id, activityData = data)

                Toast.makeText(
                    applicationContext,
                    "Your activity successfully updated",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
        }
    }
}