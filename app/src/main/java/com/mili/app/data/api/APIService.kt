package com.mili.app.data.api

import com.mili.app.data.api.APIEndPoint.LIVE_CONVERSION
import com.mili.app.data.api.response.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface APIService {
    @GET(LIVE_CONVERSION)
    suspend fun getConversionFor(@QueryMap params: Map<String, String>): CurrencyResponse
}