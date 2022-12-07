package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DyWidgetChooseRequest(
    @SerialName("user") val user: DyUser,
    @SerialName("session") val session: DySession,
    @SerialName("selector") val selector: DySelector,
    @SerialName("context") val context: DyContext,
)