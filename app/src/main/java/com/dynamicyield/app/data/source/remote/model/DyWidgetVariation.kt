package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DyWidgetVariation<T : DyWidgetProperties>(
    @SerialName("id") val id: Long,
    @SerialName("payload") val payload: DyWidgetVariationPayload<T>,
)