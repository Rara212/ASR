package com.example.asr.homepage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.asr.R
import com.example.asr.api.ActivityAPI
import com.example.asr.api.RetrofitHelper
import com.example.asr.data.ActivityData
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    lateinit var etActivity : EditText
    lateinit var btnAdd : Button
    lateinit var rgAddActivity : RadioGroup
    lateinit var rbUI: RadioButton
    lateinit var rbNUI: RadioButton
    lateinit var rbUNI: RadioButton
    lateinit var rbNUNI: RadioButton


    val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1maWZ5d2JnY3FrZmFzbmhjaWJkIiwicm9sZSI6ImFub24iLCJpYXQiOjE2Njk5NTUxMzYsImV4cCI6MTk4NTUzMTEzNn0.EqjggAQEzg4acUUzrwVxncdxNOiGP3VYO9Wd2yRz_LA"
    val token = "Bearer $apiKey"

    val ActivityApi = RetrofitHelper.getInstance().create(ActivityAPI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        btnAdd = findViewById(R.id.btnAdd)
        etActivity = findViewById(R.id.etActivity)
        rgAddActivity = findViewById(R.id.rgType)
        rbUI = findViewById(R.id.rb1)
        rbUNI = findViewById(R.id.rb2)
        rbNUI = findViewById(R.id.rb3)
        rbNUNI = findViewById(R.id.rb4)

        rbUI.setOnClickListener {
            Toast.makeText(this, "This is a Quadrant of Urgency", Toast.LENGTH_SHORT).show()
        }
        rbUNI.setOnClickListener {
            Toast.makeText(this, "This is a Quadrant of Ditraction", Toast.LENGTH_SHORT).show()
        }
        rbNUI.setOnClickListener {
            Toast.makeText(this, "This is a Quadrant of Quality", Toast.LENGTH_SHORT).show()
        }
        rbNUNI.setOnClickListener {
            Toast.makeText(this, "This is a Quadrant of Waste", Toast.LENGTH_SHORT).show()
        }


        val sharedPreference = getSharedPreferences(
            "app_preference", Context.MODE_PRIVATE
        )

        var userid = sharedPreference.getString("userid", "[No userid found]")

        btnAdd.setOnClickListener{
            CoroutineScope(Dispatchers.Main).launch {
                val checkedActivityRadioButtonId = rgType.checkedRadioButtonId
                val type= findViewById<RadioButton>(checkedActivityRadioButtonId)
                val data = ActivityData(userid = "$userid", activity = etActivity.text.toString(), category = type.text.toString())
                val response = ActivityApi.create(token = token, apiKey = apiKey, activityData = data)

                Toast.makeText(
                    applicationContext,
                    "New activity successfully added",
                    Toast.LENGTH_SHORT
                ).show()



                finish()
            }
        }
    }
}