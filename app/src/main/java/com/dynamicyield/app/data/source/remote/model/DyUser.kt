package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DyUser(
    @SerialName("dyid") val dyId: String,
    @SerialName("dyid_server") val dyIdServer: String,
    @SerialName("active_consent_accepted") val activeConsentAccepted: Boolean,
)