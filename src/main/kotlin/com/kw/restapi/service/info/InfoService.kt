package com.kw.restapi.service.info

import com.kw.restapi.constant.AppOutputStatus
import com.kw.restapi.dto.downstream.info.GatheredInfo
import com.kw.restapi.dto.upstream.api.infoadapter.credit.SearchCreditArrangementRequest
import com.kw.restapi.extension.apiresponse.peekHttpCode
import com.kw.restapi.extension.apiresponse.peekStatusCode
import com.kw.restapi.service.upstream.api.InfoAdapterApi
import com.kw.starter.common.dto.ApiOutput
import com.kw.starter.common.exception.ApplicationException
import com.kw.starter.common.manager.ThreadPoolManager
import com.kw.starter.common.service.api.ApiResponse
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class InfoService(
    private val infoAdapterApi: InfoAdapterApi,
) {
    fun gatherInfoForAccount(accountId: String): Pair<ApiOutput<GatheredInfo>, HttpStatus> {
        val pool =
            ThreadPoolManager.initFixedThreadPoolTaskExecutor(nThreads = 3).apply {
                setThreadNamePrefix("Thd-GatherInfo-")
            }

        return runBlocking(pool.asCoroutineDispatcher()) {
            val deferredSearchCrArr =
                async {
                    SearchCreditArrangementRequest(
                        lendingAccountNumber = "211005PNE00000009",
                    ).let {
                        makeApiCall { infoAdapterApi.searchCreditArrangement(requestBody = it) }
                    }
                }
            val deferredSearchDepAcc =
                async {
                    makeApiCall {
                        infoAdapterApi.searchDepositAccount(depositAccountId = "8af821968ff10ab9018ff21cfa7c1318")
                    }
                }
            val deferredGetAccInfo =
                async {
                    makeApiCall { infoAdapterApi.getMambuAccount(accountId = "8a8e86df7a73dbe0017a7abace211c56") }
                }

            val (searchCrArrStatus, searchCrArrRespData) = deferredSearchCrArr.await()
            val (searchDepAccStatus, searchDepAccRespData) = deferredSearchDepAcc.await()
            val (getAccApiStatus, getAccApiRespData) = deferredGetAccInfo.await()

            val isAllSet = setOf(searchCrArrStatus, searchDepAccStatus, getAccApiStatus).all { it == "OK2000" }
            if (!isAllSet) {
                throw ApplicationException(
                    httpStatus = HttpStatus.BAD_GATEWAY,
                    apiOutputStatus = AppOutputStatus.L502_0,
                )
            }

            ApiOutput.fromStatus(
                apiOutputStatus = AppOutputStatus.L200_0,
                data =
                    GatheredInfo(
                        creditArrangementsData = searchCrArrRespData,
                        depositAccountData = searchDepAccRespData,
                        accountInfoData = getAccApiRespData,
                    ),
            ) to HttpStatus.OK
        }.also {
            pool.shutdown()
        }
    }

    private fun <T : Any> makeApiCall(callApi: () -> ApiResponse<ApiOutput<T>>): Pair<String, T?> =
        when (
            val apiResponse = callApi()
        ) {
            is ApiResponse.Success,
            is ApiResponse.Failure,
            -> {
                apiResponse.peekStatusCode() to apiResponse.body?.data
            }
            is ApiResponse.Error -> {
                apiResponse.peekHttpCode() to null
            }
        }
}
