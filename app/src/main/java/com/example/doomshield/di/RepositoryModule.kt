package com.example.doomshield.di

import com.example.doomshield.data.repository.AppTimeLimitRepositoryImpl
import com.example.doomshield.data.repository.AppUsageRepositoryImpl
import com.example.doomshield.domain.repository.AppTimeLimitRepository
import com.example.doomshield.domain.repository.AppUsageRepository
import com.example.doomshield.domain.repository.StatsRepository
import com.example.doomshield.domain.repository.StatsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStatsRepository(
        statsRepositoryImpl: StatsRepositoryImpl
    ): StatsRepository
    
    @Binds
    @Singleton
    abstract fun bindAppTimeLimitRepository(
        impl: AppTimeLimitRepositoryImpl
    ): AppTimeLimitRepository
    
    @Binds
    @Singleton
    abstract fun bindAppUsageRepository(
        impl: AppUsageRepositoryImpl
    ): AppUsageRepository
}
