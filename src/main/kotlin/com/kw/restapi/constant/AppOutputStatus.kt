package com.kw.restapi.constant

import com.kw.starter.common.constant.ApiOutputStatus

enum class AppOutputStatus(
    override val code: String,
    override val message: String,
    override val description: String,
) : ApiOutputStatus {
    L200_0(code = "L2000", message = "OK", description = "OK"),

    L502_0(code = "L5020", message = "BAD GATEWAY", description = "Invalid response from upstream service"),
}
