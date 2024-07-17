package com.kw.restapi.configuration.api

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "api")
data class ApiProperty(
    val lmsAdapter: ApiConfig,
) {
    data class ApiConfig(val baseUrl: String, val accessKey: String?)
}
