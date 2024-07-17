package com.kw.restapi.dto.upstream.api.infoadapter.deposit

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GetDepositAccountData(
    val account: Account,
) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class Account(
        val id: String,
        val encodedKey: String,
        val holderType: String,
        val holderKey: String,
        @param:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        @get:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        val createDatetime: LocalDateTime,
        @param:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        @get:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        val approveDatetime: LocalDateTime,
        val state: String,
        val productName: String,
        val productKey: String,
        val branchKey: String,
        val branchId: String,
        val settlementAccountKeys: List<String>?,
        val balances: Balances,
        val lendingAccount: LendingAccount?,
    ) {
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
        data class Balances(
            val totalBalance: Double,
            val overdraftAmount: Double,
            val technicalOverdraftAmount: Double,
            val lockedBalance: Double,
            val availableBalance: Double,
            val holdBalance: Double,
            val overdraftInterestDue: Double,
            val technicalOverdraftInterestDue: Double,
            val feesDue: Double,
            val blockedBalance: Double,
            val forwardAvailableBalance: Double,
        )

        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
        data class LendingAccount(
            val lpDpAccountId: String,
            val lpDpAccountNumber: String,
            val lpDpProductCode: String,
        )
    }
}
