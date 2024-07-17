package com.kw.restapi.extension.apiresponse

import com.kw.starter.common.dto.ApiOutput
import com.kw.starter.common.service.api.ApiResponse

fun <T : Any> ApiResponse<ApiOutput<T>>.peekStatusCode(): String =
    this.body?.status?.code ?: this.httpStatus.value().toString()

fun <T> ApiResponse<T>.peekHttpCode(): String = this.httpStatus.value().toString()
