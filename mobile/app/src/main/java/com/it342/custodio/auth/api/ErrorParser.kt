package com.it342.custodio.auth.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Response

object ErrorParser {
    private val gson = Gson()

    fun parseError(response: Response<*>): String {
        val body = response.errorBody()?.string() ?: return "Request failed (${response.code()})"
        return try {
            val json = gson.fromJson(body, JsonObject::class.java)
            when {
                json.has("error") -> json.get("error").asString
                json.has("message") -> json.get("message").asString
                json.has("details") -> {
                    val details = json.getAsJsonObject("details")
                    details.entrySet().joinToString(". ") { it.value.asString }
                }
                else -> "Request failed (${response.code()})"
            }
        } catch (_: Exception) {
            "Request failed (${response.code()})"
        }
    }
}
