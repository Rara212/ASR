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
import com.example.asr.settings.SettingActivity
import com.example.asr.welcomepage.SigninActivity
import com.example.asr.welcomepage.list.Model
import com.example.asr.welcomepage.list.TodoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var listTodo: ListView
    lateinit var btnadd_activity: FloatingActionButton
    lateinit var btnSetting: ImageButton
    lateinit var labelHeader: TextView
    lateinit var spQuadrant : Spinner
    lateinit var btnLogout : ImageButton

    val apiKey = ""
    val token = "Bearer $apiKey"

    val Items = ArrayList<Model>()
    val ActivityAPI = RetrofitHelper.getInstance().create(ActivityAPI::class.java)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        listTodo = findViewById(R.id.list_todo)
        labelHeader = findViewById(R.id.lblHeader)

        var result = intent.getStringExtra("result")
        labelHeader.text = "Hello, $result?"

        val Items = ArrayList<Model>()
        Items.add(Model("1", "", "Hello"))
        Items.add(Model("2", "", "Go home early"))
        Items.add(Model("3", "", "MAP Project"))

        val adapter = TodoAdapter(this, R.layout.todo_item, Items)
        listTodo.adapter = adapter

        btnadd_activity = findViewById(R.id.fabAddList)
        btnSetting = findViewById(R.id.btnSetting)
        spQuadrant = findViewById(R.id.spinner)
        btnLogout = findViewById(R.id.btnLogout)

        spQuadrant.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
               Toast.makeText(this@MainActivity,
                   "You selected ${adapterView?.getItemAtPosition(position).toString()}",
                   Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        /*Adding shared preference*/
        val sharedPreference = getSharedPreferences(
            "app_preference", Context.MODE_PRIVATE
        )

        var name = sharedPreference.getString("email", "[No email found]").toString()
        lblHeader.text = "Hello, $name"

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
