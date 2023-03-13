package com.example.android_bankuish_challenge.data.network

import com.example.android_bankuish_challenge.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

/**
 * @author Estefania Figueroa Buitrago
 * estefaniafigbui@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
object RetrofitHelperModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        // Use the Retrofit builder to build a retrofit object and return it
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideGithubApiService(retrofit: Retrofit): GithubApiService {
        return retrofit.create(GithubApiService::class.java)
    }
}