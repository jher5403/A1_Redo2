package com.lab1.a1_redo2.Data

import com.squareup.moshi.Json

data class Task(
    @Json(name = "description")
    var taskDesc: String,

    @Json(name = "completed")
    var isCompleted: Boolean = false,
)
