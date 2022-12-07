package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DyWidgetChooseResponse(
    @SerialName("choices") val choices: List<DyWidgetChoice>,
    @SerialName("cookies") val cookies: List<DyCookie>,
)