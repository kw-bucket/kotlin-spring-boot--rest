package com.kw.restapi.dto.upstream.api.infoadapter.credit

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class SearchCreditArrangementRequest(
    val lendingAccountNumber: String,
)
