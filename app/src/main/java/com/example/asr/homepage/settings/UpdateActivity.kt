package com.example.asr.homepage.settings

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
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

    /*1.Set up variables that will be used*/
    lateinit var etActivityUpdate: EditText
    lateinit var btnUpdate: Button
    lateinit var type1: RadioButton
    lateinit var type2: RadioButton
    lateinit var type3: RadioButton
    lateinit var type4: RadioButton

    lateinit var id : String
    lateinit var category : String
    val apiKey = ""
    val token = "Bearer $apiKey"

    /*2.Create calling api function*/
    val activityApi = RetrofitHelper.getInstance().create(ActivityAPI::class.java)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        /*3.Define the references of the variables*/
        etActivityUpdate = findViewById(R.id.etActivityUpdate)
        btnUpdate = findViewById(R.id.btnAddupdate)
        type1 = findViewById(R.id.rb1)
        type2 = findViewById(R.id.rb2)
        type3 = findViewById(R.id.rb3)
        type4 = findViewById(R.id.rb4)

        /*4.Fetch data from the previous activity*/
        category = intent.getStringExtra("category").toString()
        id = intent.getStringExtra("activityid").toString()
        var activityidQuery = "eq.$id"

        /*5.Showing existing data*/
        etActivityUpdate.setText(intent.getStringExtra("activity").toString())

        if(category==type1.text.toString()) {
            type1.setChecked(true)
        } else if (category==type2.text.toString()) {
            type1.setChecked(true)
        } else if (category==type3.text.toString()) {
            type3.setChecked(true)
        } else {
            type4.setChecked(true)
        }

        btnUpdate.setOnClickListener{
            /*6.Load data after user finished edited the data*/
            CoroutineScope(Dispatchers.Main).launch {
                val checkedActivityRadioButtonId = rgType.checkedRadioButtonId
                val type= findViewById<RadioButton>(checkedActivityRadioButtonId)
                val data = ActivityData(activity = etActivityUpdate.text.toString(), category = type.text.toString())
                val response = activityApi.update(token = token, apiKey = apiKey, idQuery = activityidQuery, activityData = data)

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
