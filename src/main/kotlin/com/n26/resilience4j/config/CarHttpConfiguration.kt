package com.n26.resilience4j.config

import com.n26.resilience4j.CarHttpService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CarHttpConfiguration(
    private val circuitBreakerConfiguration: CircuitBreakerConfiguration
) {

    private companion object {
        const val BASE_URL = "http://localhost:8081/cars"
    }

    private fun buildClient() = OkHttpClient.Builder().build()

    private fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(buildClient())
        .build()

    @Bean
    fun carHttpService(): CarHttpService = buildRetrofit().create(CarHttpService::class.java)
}