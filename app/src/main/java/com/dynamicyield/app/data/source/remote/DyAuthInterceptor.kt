package com.dynamicyield.app.data.source.remote

import okhttp3.Interceptor
import okhttp3.Response

class DyAuthInterceptor : Interceptor {
    companion object {
        private const val AUTH_HEADER_NAME = "DY-API-Key"
        private const val AUTH_HEADER_VALUE = "90f042df46f40e8922ad46a5f07f9e7d71107388591fe4e6afdcb4705fcdc374"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        // add auth token to header
        request = request.newBuilder()
            .addHeader(AUTH_HEADER_NAME, AUTH_HEADER_VALUE)
            .build()

        return chain.proceed(request)
    }
}