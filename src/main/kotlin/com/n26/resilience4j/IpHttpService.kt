package com.n26.resilience4j

import retrofit2.Call
import retrofit2.http.GET

interface IpHttpService {
    @GET("/")
    fun getIpAddress() : Call<IpAddress>
}