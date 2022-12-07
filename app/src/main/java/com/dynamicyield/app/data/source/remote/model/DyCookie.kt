package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DyCookie(
    @SerialName("name") val name: String,
    @SerialName("value") val value: String,
    @SerialName("maxAge") val maxAge: String,
)