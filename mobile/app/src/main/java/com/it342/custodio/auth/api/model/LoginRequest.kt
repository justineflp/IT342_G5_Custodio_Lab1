package com.it342.custodio.auth.api.model

data class LoginRequest(
    val email: String,
    val password: String
)
