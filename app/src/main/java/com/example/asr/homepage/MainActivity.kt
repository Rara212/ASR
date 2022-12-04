package com.example.asr.homepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.asr.*
import com.example.asr.settings.SettingActivity
import com.example.asr.welcomepage.list.Model
import com.example.asr.welcomepage.list.TodoAdapter

class MainActivity : AppCompatActivity() {
    lateinit var listTodo: ListView
    lateinit var btnadd_activity: ImageButton
    lateinit var btnSetting: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listTodo = findViewById(R.id.list_todo)

        val Items = ArrayList<Model>()
        Items.add(Model("1", "", "Hello"))
        Items.add(Model("2", "", "Go home early"))
        Items.add(Model("3", "", "MAP Project"))

        val adapter = TodoAdapter(this, R.layout.todo_item, Items)
        listTodo.adapter = adapter

        btnadd_activity = findViewById(R.id.imageButton5)
        btnSetting = findViewById(R.id.imageButton7)


        btnadd_activity.setOnClickListener {
            Intent(this, add_activity::class.java).also {
                startActivity(it)
            }
        }

        btnSetting.setOnClickListener {
            Intent(this, SettingActivity::class.java).also {
                startActivity(it)

            }
        }
    }
}