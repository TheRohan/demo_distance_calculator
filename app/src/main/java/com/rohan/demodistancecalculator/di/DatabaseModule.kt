package com.rohan.demodistancecalculator.di

import android.content.Context
import androidx.room.Room
import com.rohan.demodistancecalculator.data.db.DistanceInfoDAO
import com.rohan.demodistancecalculator.data.db.DistanceInfoDatabase
import com.rohan.demodistancecalculator.other.Constants
import com.rohan.demodistancecalculator.repositories.DBRepository
import com.rohan.demodistancecalculator.repositories.DBRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ): DistanceInfoDatabase = Room.databaseBuilder(
        app,
        DistanceInfoDatabase::class.java,
        Constants.DISTANCE_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(db: DistanceInfoDatabase): DistanceInfoDAO = db.dao


    @Singleton
    @Provides
    fun provideDBRepository(db: DistanceInfoDatabase): DBRepository {
        return DBRepositoryImpl(db.dao)
    }
}