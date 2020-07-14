package com.almissbah.weather.di.component.module

import com.almissbah.weather.data.remote.WeatherApiService
import com.almissbah.weather.data.remote.interceptor.RequestInterceptor
import com.almissbah.weather.utils.BASE_URL
import com.almissbah.weather.utils.CONNECTION_TIMEOUT
import com.almissbah.weather.utils.ENABLE_LOG
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object ApiModule {
    @Provides
    @Singleton
    @JvmStatic
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }


    @Provides
    @Singleton
    @JvmStatic
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }


    @Provides
    @Singleton
    @JvmStatic
    fun provideRequestInterceptor(): RequestInterceptor {
        return RequestInterceptor()
    }


    @Provides
    @Singleton
    @JvmStatic
    fun provideHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        requestInterceptor: RequestInterceptor
    ): OkHttpClient {
        val okHttp =
            OkHttpClient().newBuilder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(requestInterceptor)

        if (ENABLE_LOG) okHttp.addInterceptor(httpLoggingInterceptor)

        return okHttp
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideWeatherService(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }


}