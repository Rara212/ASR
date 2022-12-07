package com.example.asr.data

class ActivityItem (
    val id: String,
    val title: String,
    val created_at: String,
    val URGENTIMPORTANT: String? = null,
    val URGENTNOTIMPORTANT: String? = null,
    val NOTURGENTIMPORTANT: String? = null,
    val NOTURGENTNOTIMPORTANT: String? = null,

    )