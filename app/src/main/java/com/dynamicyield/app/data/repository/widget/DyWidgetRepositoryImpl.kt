package com.dynamicyield.app.data.repository.widget

import com.dynamicyield.app.data.repository.CommonError
import com.dynamicyield.app.data.repository.DyResultWrapper
import com.dynamicyield.app.data.source.remote.model.*
import com.dynamicyield.app.data.source.remote.ApiResultWrapper
import com.dynamicyield.app.data.source.remote.DyApi
import com.dynamicyield.app.data.source.remote.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DyWidgetRepositoryImpl(
    private val dyApi: DyApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : DyWidgetRepository {

    override suspend fun chooseDyWidgets(
        vararg widgetNames: com.dynamicyield.templates.ui.DyWidgetName
    ): DyResultWrapper<List<DyWidgetChoice>, CommonError> = withContext(dispatcher) {
        when (val apiResult =
            safeApiCall { dyApi.chooseWidgets(createWidgetChooseRequestStub(*widgetNames)) }) {
            is ApiResultWrapper.Success -> DyResultWrapper.Success(apiResult.value.choices)
            is ApiResultWrapper.UnexpectedServerResponseError -> DyResultWrapper.Error(
                CommonError.UnexpectedServerResponse(apiResult.msg)
            )
            is ApiResultWrapper.ConnectionError -> DyResultWrapper.Error(CommonError.NetworkError())
            is ApiResultWrapper.Error -> DyResultWrapper.RawError(apiResult.code, apiResult.msg)
        }
    }

    private fun createWidgetChooseRequestStub(vararg widgetNames: com.dynamicyield.templates.ui.DyWidgetName) =
        DyWidgetChooseRequest(
            user = DyUser(
                dyId = "-3003900752219024818",
                dyIdServer = "-3003900752219024818",
                activeConsentAccepted = true
            ),
            session = DySession(
                dy = "4rbmp6cdx5bmwq88fyquo16azw8calmy"
            ),
            selector = DySelector(
                names = widgetNames.map { it.selector }
            ),
            context = DyContext(
                page = DyPage(
                    location = "/",
                    referrer = "/",
                    type = "HOMEPAGE",
                    data = emptyList()
                ),
                device = DyDevice(
                    userAgent = "android",
                    ip = ""
                ),
                pageAttributes = DyPageAttributes(
                    accountType = "business"
                )
            )
        )

}