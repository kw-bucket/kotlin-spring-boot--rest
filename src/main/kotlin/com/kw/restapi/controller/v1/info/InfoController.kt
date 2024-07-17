package com.kw.restapi.controller.v1.info

import com.kw.restapi.dto.downstream.info.GatheredInfo
import com.kw.restapi.service.info.InfoService
import com.kw.starter.common.dto.ApiOutput
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/info")
class InfoController(private val infoService: InfoService) {
    @GetMapping("/account/{id}")
    fun gatherInfoForAccount(
        @PathVariable(name = "id") id: String,
    ): ResponseEntity<ApiOutput<GatheredInfo>> =
        infoService.gatherInfoForAccount(accountId = id).let { ResponseEntity(it.first, it.second) }
}
