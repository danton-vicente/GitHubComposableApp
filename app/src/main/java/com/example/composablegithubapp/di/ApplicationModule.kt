package com.example.composablegithubapp.di

import com.example.composablegithubapp.data.apis.MainApi
import com.example.composablegithubapp.data.base.ResultCallAdapterFactory
import com.google.gson.GsonBuilder
import com.valentinilk.shimmer.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    private var mGsonConverterFactory: GsonConverterFactory? = null

    private val gsonConverter: GsonConverterFactory
        get() {
            if (mGsonConverterFactory == null) {
                mGsonConverterFactory = GsonConverterFactory
                    .create(
                        GsonBuilder()
                            .setLenient()
                            .disableHtmlEscaping()
                            .create()
                    )
            }
            return mGsonConverterFactory!!
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE

        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor = interceptor)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(gsonConverter)
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .baseUrl("https://api.github.com/")
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideServiceApi(retrofit: Retrofit): MainApi {
        return retrofit.create(MainApi::class.java)
    }

}