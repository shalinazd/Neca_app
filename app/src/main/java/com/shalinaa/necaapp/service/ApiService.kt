package com.shalinaa.necaapp.service

import com.shalinaa.necaapp.model.ResponseNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("news?")
    fun getNewsData(
        @Query("access_key") apiKey : String?,
        @Query("categories") categories : String?,
        @Query("country") country : String?
    ): Call<ResponseNews>
}