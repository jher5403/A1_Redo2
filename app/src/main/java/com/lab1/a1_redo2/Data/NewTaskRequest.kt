package com.lab1.a1_redo2.Data

import com.squareup.moshi.Json

data class NewTaskRequest(

    @Json(name = "description")
    val description: String,

    @Json(name = "completed")
    val completed: Boolean,

    @Json(name = "meta")
    val meta: Meta?

)
