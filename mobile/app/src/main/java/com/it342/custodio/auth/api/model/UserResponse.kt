package com.it342.custodio.auth.api.model

data class UserResponse(
    val id: Long,
    val email: String,
    @com.google.gson.annotations.SerializedName("fullName") val fullName: String
)
