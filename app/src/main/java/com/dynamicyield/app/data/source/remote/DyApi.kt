package com.dynamicyield.app.data.source.remote

import com.dynamicyield.app.data.source.remote.model.DyWidgetChooseRequest
import com.dynamicyield.app.data.source.remote.model.DyWidgetChooseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DyApi {

    @POST("serve/user/choose")
    suspend fun chooseWidgets(@Body request: DyWidgetChooseRequest): Response<DyWidgetChooseResponse>
}