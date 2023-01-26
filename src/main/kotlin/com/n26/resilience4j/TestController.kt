package com.n26.resilience4j

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController(val ipHttpService: IpHttpService) {

    @GetMapping("/ip")
    fun getIp() : String {
        return ipHttpService.getIpAddress()
    }
}