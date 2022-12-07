package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DyPage(
    @SerialName("location") val location: String,
    @SerialName("referrer") val referrer: String,
    @SerialName("type") val type: String,
    @SerialName("data") val data: List<String>,
)