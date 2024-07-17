package com.kw.restapi.service.upstream.api

import com.kw.restapi.configuration.api.ApiProperty
import com.kw.restapi.dto.upstream.api.infoadapter.account.GetAccountInfoData
import com.kw.restapi.dto.upstream.api.infoadapter.credit.SearchCreditArrangementRequest
import com.kw.restapi.dto.upstream.api.infoadapter.credit.SearchCreditArrangementsData
import com.kw.restapi.dto.upstream.api.infoadapter.deposit.GetDepositAccountData
import com.kw.starter.common.dto.ApiOutput
import com.kw.starter.common.extension.apiresponse.peekOutputStatus
import com.kw.starter.common.http.constant.HeaderFields
import com.kw.starter.common.log.constant.LogbackFields
import com.kw.starter.common.service.api.ApiResponse
import com.kw.starter.common.service.api.ApiService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class InfoAdapterApi(
    restTemplate: RestTemplate,
    apiProperties: ApiProperty,
) : ApiService(restTemplate = restTemplate) {
    private val apiConfig = apiProperties.lmsAdapter
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun searchCreditArrangement(
        requestBody: SearchCreditArrangementRequest,
    ): ApiResponse<ApiOutput<SearchCreditArrangementsData>> {
        val path = "/v1/credit-arrangements"
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.set(HeaderFields.X_ACCESS_KEY, apiConfig.accessKey)
        headers.set(HeaderFields.X_CORRELATION_ID, MDC.get(LogbackFields.CORRELATION_ID))

        return execute(
            httpMethod = HttpMethod.POST,
            uriComponents =
                UriComponentsBuilder
                    .fromHttpUrl(apiConfig.baseUrl)
                    .path(path)
                    .build(),
            httpEntity = HttpEntity(requestBody, headers),
            responseType = object : ParameterizedTypeReference<ApiOutput<SearchCreditArrangementsData>>() {},
        ).also {
            logger.info("Search credit arrangements: [{}]", it.peekOutputStatus())
        }
    }

    fun searchDepositAccount(depositAccountId: String): ApiResponse<ApiOutput<GetDepositAccountData>> {
        val path = "/v1/mambu/deposits/{depositAccountId}"
        val uriVariables = mapOf("depositAccountId" to depositAccountId)
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.set(HeaderFields.X_ACCESS_KEY, apiConfig.accessKey)
        headers.set(HeaderFields.X_CORRELATION_ID, MDC.get(LogbackFields.CORRELATION_ID))

        return execute(
            httpMethod = HttpMethod.GET,
            uriComponents =
                UriComponentsBuilder
                    .fromHttpUrl(apiConfig.baseUrl)
                    .path(path)
                    .queryParam("details_level", "BASIC")
                    .buildAndExpand(uriVariables),
            httpEntity = HttpEntity(headers),
            responseType = object : ParameterizedTypeReference<ApiOutput<GetDepositAccountData>>() {},
        ).also {
            logger.info("Search deposit account: [{}]", it.peekOutputStatus())
        }
    }

    fun getMambuAccount(accountId: String): ApiResponse<ApiOutput<GetAccountInfoData>> {
        val path = "/v1/mambu/accounts/{accountId}"
        val uriVariables = mapOf("accountId" to accountId)
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.set(HeaderFields.X_ACCESS_KEY, apiConfig.accessKey)
        headers.set(HeaderFields.X_CORRELATION_ID, MDC.get(LogbackFields.CORRELATION_ID))

        return execute(
            httpMethod = HttpMethod.GET,
            uriComponents =
                UriComponentsBuilder
                    .fromHttpUrl(apiConfig.baseUrl)
                    .path(path)
                    .queryParam("details_level", "BASIC")
                    .buildAndExpand(uriVariables),
            httpEntity = HttpEntity(headers),
            responseType = object : ParameterizedTypeReference<ApiOutput<GetAccountInfoData>>() {},
        ).also {
            logger.info("Search mambu account: [{}]", it.peekOutputStatus())
        }
    }
}
