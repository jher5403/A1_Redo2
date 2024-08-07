package com.lab1.a1_redo2.Data

import com.squareup.moshi.Json

data class NewTaskResponse(
    @Json(name = "id")
    val id: Int,

    @Json(name = "description")
    val description: String,

    @Json(name = "completed")
    @IntToBoolean
    val completed: Boolean,

    @Json(name = "meta")
    val meta: Meta? = null
)

