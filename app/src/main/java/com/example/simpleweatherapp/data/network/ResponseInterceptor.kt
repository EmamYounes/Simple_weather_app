package com.example.simpleweatherapp.data.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

class ResponseInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response: Response = chain.proceed(request)
        val rawJson: String = response.body()!!.string()

        Log.d("DSResponse", response.code().toString() + " , " + rawJson)
        return response.newBuilder()
            .body(ResponseBody.create(response.body()!!.contentType(), rawJson)).build()
    }
}
