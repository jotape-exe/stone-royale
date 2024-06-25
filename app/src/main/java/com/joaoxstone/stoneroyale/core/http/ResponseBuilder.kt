package com.joaoxstone.stoneroyale.core.http

data class ResponseBuilder<T>(
    val message: String = "",
    val status: Int? = null,
    val success: Boolean = true,
    val response: T? = null
)