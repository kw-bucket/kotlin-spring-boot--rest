package com.kw.restapi.dto.upstream.api.infoadapter.account

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GetAccountInfoData(
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
        val approveAmount: Double,
        @param:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        @get:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        val lastModifiedDate: LocalDateTime,
        @param:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        @get:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        val closedDatetime: LocalDateTime?,
        val state: String,
        val subState: String?,
        val productName: String,
        val productKey: String,
        val branchKey: String,
        val branchId: String,
        val balances: Balances,
        val disbursementDetails: DisbursementDetails,
        val schedule: ScheduleSettings,
    ) {
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
        data class Balances(
            val principalDue: Double,
            val principalPaid: Double,
            val principalBalance: Double,
            val interestBalance: Double,
            val interestDue: Double,
            val interestFromArrearsBalance: Double,
            val interestFromArrearsDue: Double,
            val interestFromArrearsPaid: Double,
            val feesBalance: Double,
            val feesDue: Double,
            val penaltyBalance: Double,
            val penaltyDue: Double,
            val lendingAccount: LendingAccount?,
            val lenderInformation: LenderInformation?,
        )

        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
        data class DisbursementDetails(
            @param:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
            @get:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            val expectedDisbursementDate: LocalDateTime,
            @param:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
            @get:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            val disbursementDate: LocalDateTime,
            @param:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
            @get:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            val firstRepaymentDate: LocalDateTime?,
        )

        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
        data class ScheduleSettings(
            val gracePeriod: Int,
            val installment: Int,
            val dueDate: Int,
        )

        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
        data class LendingAccount(
            val lendingAccountId: String,
            val lendingAccountNumber: String,
            val lendingProductCode: String,
        )

        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
        data class LenderInformation(
            val lenderApplicationId: String,
            val lenderAccountNumber: String,
            val lenderTransferDate: String,
            val lenderTransferAmount: Double,
        )
    }
}
