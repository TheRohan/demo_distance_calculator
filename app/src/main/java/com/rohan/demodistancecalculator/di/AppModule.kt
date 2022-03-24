package com.rohan.demodistancecalculator.di

import android.content.Context
import androidx.room.Room
import com.rohan.demodistancecalculator.data.db.DistanceInfoDAO
import com.rohan.demodistancecalculator.data.db.DistanceInfoDatabase
import com.rohan.demodistancecalculator.other.Constants.DISTANCE_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ): DistanceInfoDatabase = Room.databaseBuilder(
        app,
        DistanceInfoDatabase::class.java,
        DISTANCE_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(db: DistanceInfoDatabase): DistanceInfoDAO = db.dao

}