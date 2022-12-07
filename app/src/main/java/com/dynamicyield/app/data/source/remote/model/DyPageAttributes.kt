package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DyPageAttributes(
    @SerialName("account_type") val accountType: String,
)