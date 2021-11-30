package com.example.simpleweatherapp.data.network

import com.example.simpleweatherapp.data.network.responses.CityWeatherResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface MyApi {


    @GET("weather?q={cityName}&appid={appid}")
    suspend fun getCityWeather(
        @Path("cityName") cityName: String,
        @Path("appid") appid: String,
    ): Response<CityWeatherResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyApi {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

}

