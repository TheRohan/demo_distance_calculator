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


}