package com.example.weatherapp.di

import com.example.weatherapp.datal.AppDatabase
import android.content.Context
import androidx.room.Room
import com.example.weatherapp.data.repository.CityRepositoryImpl
import com.example.weatherapp.datal.CityDao

import com.example.weatherapp.domain.CityRepository
import com.example.weatherapp.network.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun db(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "weather_db"
        ).build()

    @Provides
    fun dao(db: AppDatabase) = db.cityDao()

    @Provides
    fun repo(dao: CityDao): CityRepository =
        CityRepositoryImpl(dao)

    @Provides
    fun api(): WeatherApi =
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
}