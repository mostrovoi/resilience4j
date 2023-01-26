package com.n26.resilience4j.config

import com.n26.resilience4j.IpHttpService
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Configuration
class IpHttpConfiguration(
    private val circuitBreakerConfiguration: CircuitBreakerConfiguration
) {

    private companion object {
        const val BASE_URL = "http://ip.jsontest.com/"
    }

    private fun buildClient() = OkHttpClient.Builder().build()

    private fun buildRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildClient())
            .build()
    }

    @Bean
    fun ipHttpService(): IpHttpService = buildRetrofit().create(IpHttpService::class.java)
}
