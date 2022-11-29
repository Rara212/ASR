package com.example.asr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display.Mode
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.*

class MainActivity : AppCompatActivity() {
    lateinit var listTodo : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listTodo = findViewById(R.id.list_todo)

        val Items = ArrayList<Model>()
        Items.add(Model("1","", "Hello"))
        Items.add(Model("2","", "Go home early"))
        Items.add(Model("3","", "MAP Project"))

        val adapter = TodoAdapter(this, R.layout.todo_item, Items)
        listTodo.adapter = adapter

    }
}