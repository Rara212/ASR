package com.example.asr.homepage

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.asr.*
import com.example.asr.api.ActivityAPI
import com.example.asr.api.RetrofitHelper
import com.example.asr.homepage.settings.SettingActivity
import com.example.asr.welcomepage.SigninActivity
import com.example.asr.homepage.list.Model
import com.example.asr.homepage.list.TodoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var listTodo: ListView
    lateinit var btnadd_activity: FloatingActionButton
    lateinit var btnSetting: ImageButton
    lateinit var labelHeader: TextView
    lateinit var spQuadrant : Spinner
    lateinit var btnLogout : ImageButton

    val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1maWZ5d2JnY3FrZmFzbmhjaWJkIiwicm9sZSI6ImFub24iLCJpYXQiOjE2Njk5NTUxMzYsImV4cCI6MTk4NTUzMTEzNn0.EqjggAQEzg4acUUzrwVxncdxNOiGP3VYO9Wd2yRz_LA"
    val token = "Bearer $apiKey"

    val Items = ArrayList<Model>()
    val ActivityAPI = RetrofitHelper.getInstance().create(ActivityAPI::class.java)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listTodo = findViewById(R.id.list_todo)
        labelHeader = findViewById(R.id.lblHeader)

        /*Adding shared preference*/
        val sharedPreference = getSharedPreferences(
            "app_preference", Context.MODE_PRIVATE
        )

        var name = sharedPreference.getString("email", "[No email found]").toString()
        labelHeader.text = "Hello, $name"

        var userid = sharedPreference.getString("userid", "[No userid found]")


        btnadd_activity = findViewById(R.id.fabAddList)
        btnSetting = findViewById(R.id.btnSetting)
        spQuadrant = findViewById(R.id.spinner)
        btnLogout = findViewById(R.id.btnLogout)


        /*show activity based on spinner selection*/
        spQuadrant.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
               /*Toast.makeText(this@MainActivity,
                   "You selected ${adapterView?.getItemAtPosition(position).toString()}",
                   Toast.LENGTH_LONG).show()*/
                CoroutineScope(Dispatchers.Main).launch {
                    val response = ActivityAPI.get(token = token, apiKey = apiKey)

                    response.body()?.forEach {
                        Items.add(
                            Model(
                                UserId = it.activityid,
                                Id = it.userid,
                                Todolist = it.activity,
                                Category = it.category
                            )
                        )
                    }
                    setList(Items)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }



        /*Intent to add activity*/
        btnadd_activity.setOnClickListener {
            Intent(this, AddActivity::class.java).also {
                startActivity(it)
            }
        }

        /*Intent to go to setting*/
        btnSetting.setOnClickListener {
            Intent(this, SettingActivity::class.java).also {
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

    fun setList(Items: ArrayList<Model>) {
        val adapter = TodoAdapter(this, R.layout.todo_item, Items)
        listTodo.adapter = adapter
    }

    override fun onBackPressed() {
        val sharedPreference =  getSharedPreferences(
            "app_preference", Context.MODE_PRIVATE
        )

        var email = sharedPreference.getString("email", "[No email found]").toString()

        if (email.isNotEmpty()) {
            super.onBackPressed()
            return
        }
    }
}
