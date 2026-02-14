package com.it342.custodio.auth.api

import com.it342.custodio.auth.api.model.AuthResponse
import com.it342.custodio.auth.api.model.LoginRequest
import com.it342.custodio.auth.api.model.RegisterRequest
import com.it342.custodio.auth.api.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("api/auth/logout")
    suspend fun logout(): Response<Map<String, String>>

    @GET("api/user/me")
    suspend fun getMe(): Response<UserResponse>
}
