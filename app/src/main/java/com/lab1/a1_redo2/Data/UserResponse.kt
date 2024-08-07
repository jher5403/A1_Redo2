package com.lab1.a1_redo2.Data

import com.squareup.moshi.Json

// Used in Login and Register requests
data class UserResponse(
    @Json(name = "token")
    val token: String,

    @Json(name = "id")
    val id: Int,

    @Json(name = "name")
    val name: String,

    @Json(name = "email")
    val email: String,

    @Json(name = "enabled")
    @IntToBoolean
    val enabled: Boolean,

    @Json(name = "admin")
    @IntToBoolean
    val admin: Boolean,
)
