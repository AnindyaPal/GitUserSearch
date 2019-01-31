package com.example.gitusersearch.di

import com.example.gitusersearch.Constants
import com.example.gitusersearch.repository.GithubRepo
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class ApiRepositoryModule {

    @Provides
    @GitHubAppScope
    fun getLoggerIntercepter(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @GitHubAppScope
    fun getGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @GitHubAppScope
    fun getOkHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
    }

    @Provides
    @GitHubAppScope
    fun getRetrofitBuilder(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Provides
    @GitHubAppScope
    fun createV4Services(httpClient: OkHttpClient.Builder, logging: HttpLoggingInterceptor,
                         builder: Retrofit.Builder): GithubRepo {
        if (!httpClient.interceptors().isEmpty()) {
            httpClient.interceptors().clear()
        }

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("Content-Type", "application/json")
                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        httpClient.addInterceptor(logging)
        val retrofit = builder.client(httpClient.build())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        return retrofit.create(GithubRepo::class.java)
    }
}