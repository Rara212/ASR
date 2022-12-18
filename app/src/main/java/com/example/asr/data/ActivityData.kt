package com.example.asr.data

data class ActivityData(
    val userid: String,
    val activity: String,
    val category: String,
    val activityid: String,
    val done_at: String? = null,
)