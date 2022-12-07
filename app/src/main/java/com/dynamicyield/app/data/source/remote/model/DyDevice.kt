package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DyDevice(
    @SerialName("userAgent") val userAgent: String,
    @SerialName("ip") val ip: String,
)