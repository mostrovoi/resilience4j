package com.n26.resilience4j.config

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.context.annotation.Configuration

@Configuration
class CircuitBreakerConfiguration {

    fun getConfiguration() = CircuitBreakerConfig.custom()
        .failureRateThreshold(3.0f).build()

    fun getCircuitBreaker() = CircuitBreakerRegistry.of(getConfiguration())
        .circuitBreaker("test")

}