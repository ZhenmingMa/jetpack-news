package com.ma.jetpack.news.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetClient {


    companion object {
        private val retrofit = initRetrofit()

        private fun initRetrofit(): Retrofit {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor {
                    val newReq = it.request().newBuilder()
                        .addHeader("app_id", "oftglmfdybbnskrq")
                        .addHeader("app_secret", "dTZicks0L2ZpdEdrZEtrL1N2RXAvdz09")
                        .build()
                    it.proceed(newReq)
                }
                .addInterceptor(httpLoggingInterceptor)
                .build()
            return Retrofit.Builder()
                .baseUrl("https://www.mxnzp.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun <T> create(service: Class<T>): T {
            return retrofit.create(service)
        }
    }
}