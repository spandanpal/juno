package com.juno.retrofit


import com.juno.DateResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {

  /*  @GET("api_key={KEY}")
        fun getDetails(
            @Path("KEY") api_key: String?
        ): Call<DateResponse?>?*/

    @GET("apod?")
    fun getDetails(@Query("api_key") name: String?): Call<DateResponse?>?

    @GET("apod?")
    fun getDateDescription(
        @Query("api_key") api_key: String?,
        @Query("date") date: String?
    ): Call<DateResponse?>?


}