package com.it342.custodio.auth.api.model

data class RegisterRequest(
    val email: String,
    @com.google.gson.annotations.SerializedName("fullName") val fullName: String,
    val password: String
)
