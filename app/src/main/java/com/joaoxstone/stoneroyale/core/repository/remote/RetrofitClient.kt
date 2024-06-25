package com.joaoxstone.stoneroyale.core.repository.remote

import com.joaoxstone.stoneroyale.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    companion object {

        private lateinit var INSTANCE: Retrofit
        //Voce pode gerar o token de forma gratuita em https://developer.clashroyale.com/
        //Para gerar o token você de um IP específico, consulte https://docs.royaleapi.com/proxy.html
        //Configure o token em seu arquivo local.properties com o nome {TOKEN_CR}
        private var token: String = BuildConfig.TOKEN_CR
        private var URL = "https://proxy.royaleapi.dev/v1/"

        private fun getRetrofitClient(): Retrofit {

            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })

            if (!::INSTANCE.isInitialized) {
                synchronized(RetrofitClient::class.java) {
                    INSTANCE = Retrofit.Builder()
                        .baseUrl(URL)
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }
            return INSTANCE
        }

        fun <T> getService(serviceClass: Class<T>): T {
            return getRetrofitClient().create(serviceClass)
        }
    }

}