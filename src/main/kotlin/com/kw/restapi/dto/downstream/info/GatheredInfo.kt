package com.kw.restapi.dto.downstream.info

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.kw.restapi.dto.upstream.api.infoadapter.account.GetAccountInfoData
import com.kw.restapi.dto.upstream.api.infoadapter.credit.SearchCreditArrangementsData
import com.kw.restapi.dto.upstream.api.infoadapter.deposit.GetDepositAccountData

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GatheredInfo(
    val creditArrangementsData: SearchCreditArrangementsData? = null,
    val depositAccountData: GetDepositAccountData? = null,
    val accountInfoData: GetAccountInfoData? = null,
)
