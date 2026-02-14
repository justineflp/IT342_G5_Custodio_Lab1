package com.it342.custodio.auth.api.model

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    val token: String,
    val type: String? = "Bearer",
    val id: Long,
    val email: String,
    @SerializedName("fullName") val fullName: String
)
