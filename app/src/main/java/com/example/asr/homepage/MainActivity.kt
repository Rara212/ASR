package com.example.asr.homepage

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface.OnShowListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.asr.*
import com.example.asr.api.ActivityAPI
import com.example.asr.api.RetrofitHelper
import com.example.asr.homepage.list.Model
import com.example.asr.homepage.list.TodoAdapter
import com.example.asr.homepage.settings.AboutUs
import com.example.asr.homepage.settings.UpdateActivity
import com.example.asr.welcomepage.SigninActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    /*1.Set up variables that will be used*/
    lateinit var listTodo: ListView
    lateinit var btnadd_activity: FloatingActionButton
    lateinit var labelHeader: TextView
    lateinit var spQuadrant: Spinner
    lateinit var btnLogout: ImageButton
    lateinit var btnAboutus: ImageButton

    val apiKey = ""
    val token = "Bearer $apiKey"
    val Items = ArrayList<Model>()

    /*2.Create calling api function*/
    val ActivityAPI = RetrofitHelper.getInstance().create(ActivityAPI::class.java)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*3.Define the references of the variables*/
        listTodo = findViewById(R.id.list_todo)
        labelHeader = findViewById(R.id.lblHeader)

        btnadd_activity = findViewById(R.id.fabAddList)
        spQuadrant = findViewById(R.id.spinner)
        btnLogout = findViewById(R.id.btnLogout)
        btnAboutus = findViewById(R.id.btnAboutUs)

        /*4.Fetching data passed from Signin Activity*/
        val sharedPreference = getSharedPreferences(
            "app_preference", Context.MODE_PRIVATE
        )
        var email = sharedPreference.getString("email", "[No email found]").toString()
        var userid = sharedPreference.getString("userid", "[No userid found]")

        /*5.Show passed e-mail as a label*/
        lblHeader.text = "Hello, $email"


        /*6.Update functionality*/
        listTodo.setOnItemClickListener { adapterView, view, position, id ->
            val item = adapterView.getItemAtPosition(position) as Model
            val intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("activityid", item.Id)
            intent.putExtra("activity", item.Todolist)
            intent.putExtra("category", item.Category)
            startActivity(intent)
        }

        /*10.Delete functionality*/
        listTodo.setOnItemLongClickListener { adapterView, view, position, activityid ->
            val item = adapterView.getItemAtPosition(position) as Model

            val builder = AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle)
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("YES") { dialog, id ->
                    val activityid = item.Id.toString()
                    var queryId = "eq.$activityid"
                    deleteItem(queryId)

                    Toast.makeText(
                        applicationContext,
                        "Your activity succesfully deleted",
                        Toast.LENGTH_SHORT
                    ).show()
                    var queryUserId = "eq.$userid"
                    getItem(userid = queryUserId)
                }
                .setNegativeButton("NO") { dialog, id ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()

            return@setOnItemLongClickListener true
        }


        /*11.Quadrants Activity Filter*/
        spQuadrant.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    var category = adapterView?.getItemAtPosition(position).toString()
                    var queryUserId = "eq.$userid"
                    var queryCategory = "eq.$category"
                    getItem(category = queryCategory, userid = queryUserId)
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }


        /*12.Intent to add activity*/
        btnadd_activity.setOnClickListener{
            Intent(this, AddActivity::class.java).also {
                startActivity(it)
            }
        }
        /*13.Intent to go to setting*/
        btnAboutus.setOnClickListener{
            Intent(this, AboutUs::class.java).also {
                startActivity(it)

            }
        }

        /*14.Logout functionality*/
        btnLogout.setOnClickListener{
            var editor = sharedPreference.edit()
            editor.clear()
            editor.remove("email")
            editor.commit()

            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)

            finish()
        }

    }

    /*7.function Delete Item()*/
    fun deleteItem(id: String) {
        CoroutineScope(Dispatchers.Main).launch {
            ActivityAPI.delete(token = token, apiKey = apiKey, idQuery=id)
        }
    }

    /*8.function getItem()*/
    fun getItem(category: String ="eq.Urgent-Important", userid: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = ActivityAPI.get(token = token, apiKey = apiKey, category = category, userid = userid)
            var Items = ArrayList<Model>()

            response.body()?.forEach {
                Items.add(
                    Model(
                        Id = it.activityid,
                        /*UserId = it.userid,*/
                        Todolist = it.activity,
                        Category = it.category
                    )
                )
            }

            setList(Items)
        }
    }

    /*9.showing the to-do list function*/
    fun setList(Items: ArrayList<Model>) {
        val adapter = TodoAdapter(this, R.layout.todo_item, Items)
        listTodo.adapter = adapter
    }

    /*15.Making sure users can't go to login page once they're signed in*/
    override fun onBackPressed() {
        val sharedPreference = getSharedPreferences(
            "app_preference", Context.MODE_PRIVATE
        )

        var email = sharedPreference.getString("email", "[No email found]").toString()

        if (email.isNotEmpty()) {
            super.onBackPressed()
            return
        }
    }

    /*16.Making sure data are refreshed after user coming back to Main Activity*/
    override fun onResume() {
        super.onResume()

        /*Adding shared preference*/
        val sharedPreference = getSharedPreferences(
            "app_preference", Context.MODE_PRIVATE
        )
        var userid = sharedPreference.getString("userid", "[No userid found]")
        var queryUserId = "eq.$userid"
        getItem(userid = queryUserId)
    }
}
