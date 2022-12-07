package com.dynamicyield.app.data.source.remote

import kotlinx.serialization.SerializationException
import retrofit2.Response
import java.io.IOException

sealed class ApiResultWrapper<out T> {
    // an api operation was successful
    data class Success<out T>(val value: T): ApiResultWrapper<T>()

    // an unknown error occurs during the api operation
    data class Error(val code: Int? = null, val msg: String? = null): ApiResultWrapper<Nothing>()

    // server response parsing problem
    data class UnexpectedServerResponseError(val msg: String? = null): ApiResultWrapper<Nothing>()

    // network problem during api operation
    object ConnectionError: ApiResultWrapper<Nothing>()
}

suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>,
): ApiResultWrapper<T> {
    return try {
        val response = apiCall.invoke()
        val body = response.body()
        when {
            response.isSuccessful && body != null -> ApiResultWrapper.Success(body)
            else -> ApiResultWrapper.Error(response.code(), response.message())
        }
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
        when (throwable) {
            is IOException -> ApiResultWrapper.ConnectionError
            is SerializationException -> ApiResultWrapper.UnexpectedServerResponseError(throwable.message)
            else -> ApiResultWrapper.Error(null, throwable.message)
        }
    }
}
