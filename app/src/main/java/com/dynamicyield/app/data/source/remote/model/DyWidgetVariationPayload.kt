package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DyWidgetVariationPayload<T : DyWidgetProperties>(
    @SerialName("type") val type: String,
    @SerialName("data") val properties: T,
)