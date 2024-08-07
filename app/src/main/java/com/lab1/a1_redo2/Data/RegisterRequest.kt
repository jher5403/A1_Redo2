package com.lab1.a1_redo2.Data

import com.squareup.moshi.Json

data class RegisterRequest(
    @Json(name = "email")
    val email: String,

    @Json(name = "name")
    val name: String,

    @Json(name = "password")
    val password: String
)