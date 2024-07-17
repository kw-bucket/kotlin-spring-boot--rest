package com.kw.restapi.dto.upstream.api.infoadapter.credit

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class SearchCreditArrangementsData(
    val creditArrangements: List<CreditArrangement>,
) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class CreditArrangement(
        val encodedKey: String,
        val id: String,
        val lendingProfileId: String,
        val lendingAccountNumber: String,
        val productCode: String,
        val productName: String,
        val amount: Double,
        val availableCreditAmount: Double,
        val consumedCreditAmount: Double,
        @param:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        @get:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        val startDate: LocalDateTime,
        @param:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        @get:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        val expireDate: LocalDateTime,
        @param:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        @get:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        val approvedDate: LocalDateTime,
        @param:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        @get:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        val creationDate: LocalDateTime,
        @param:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        @get:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        val lastModifiedDate: LocalDateTime,
        val client: Client,
        val lendingInfo: LendingInfo,
    ) {
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
        data class Client(
            val encodedKey: String,
            val firstName: String,
            val lastName: String,
            val moreInfo: MoreInfo,
        ) {
            @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
            data class MoreInfo(
                val thaiId: String,
                val thaiIdHash: String,
                val email: String,
            )
        }

        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
        data class LendingInfo(
            val clientBucket: String,
            val registrationCode: String?,
            val blockCode: String,
        )
    }
}
