package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DyContext(
    @SerialName("page") val page: DyPage,
    @SerialName("device") val device: DyDevice,
    @SerialName("pageAttributes") val pageAttributes: DyPageAttributes,
)