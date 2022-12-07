package com.dynamicyield.app.data.repository

sealed class CommonError {
    data class NetworkError(val msg: String = "A network error has occurred") : CommonError()
    data class UnexpectedServerResponse(val msg : String? = "Unexpected server response") : CommonError()
}

sealed class DyResultWrapper<out S, out E> {
    data class Success<out S>(val value: S) : DyResultWrapper<S, Nothing>()

    data class Error<out E>(val error: E) : DyResultWrapper<Nothing, E>()

    data class RawError(
        val code: Int? = null,
        val msg: String? = null
    ) : DyResultWrapper<Nothing, Nothing>()
}

inline fun <S, E> DyResultWrapper<S, E>.onSuccess(
    action: (value: S) -> Unit
): DyResultWrapper<S, E> {
    if (this is DyResultWrapper.Success) action(value)
    return this
}

inline fun <S, E> DyResultWrapper<S, E>.onError(
    action: (error: E) -> Unit
): DyResultWrapper<S, E> {
    if (this is DyResultWrapper.Error) action(error)
    return this
}

inline fun <S, E> DyResultWrapper<S, E>.onRawError(
    action: (code: Int?, msg: String?) -> Unit
): DyResultWrapper<S, E> {
    if (this is DyResultWrapper.RawError) action(code, msg)
    return this
}
