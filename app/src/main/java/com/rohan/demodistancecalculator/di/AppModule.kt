package com.rohan.demodistancecalculator.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.rohan.demodistancecalculator.data.db.DistanceInfoDAO
import com.rohan.demodistancecalculator.data.db.DistanceInfoDatabase
import com.rohan.demodistancecalculator.other.Constants.DISTANCE_DATABASE_NAME
import com.rohan.demodistancecalculator.other.Constants.KEY_FIRST_TIME_TOGGLE
import com.rohan.demodistancecalculator.other.Constants.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext app: Context
    ): SharedPreferences = app.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPref: SharedPreferences) = sharedPref.getBoolean(
        KEY_FIRST_TIME_TOGGLE, true
    )
}