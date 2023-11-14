package com.example.openpaytest.di

import android.content.Context
import androidx.room.Room
import com.example.openpaytest.BuildConfig
import com.example.openpaytest.data.datasources.local.DBDetailPersonDataDao
import com.example.openpaytest.data.datasources.local.DBMostRatedMoviesDataDao
import com.example.openpaytest.data.datasources.local.DBPopularMoviesDataDao
import com.example.openpaytest.data.datasources.local.DBUpcomingMoviesDataDao
import com.example.openpaytest.data.datasources.local.OpenPayDatabase
import com.example.openpaytest.data.datasources.remote.OpenPayApiClient
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.MOVIE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun providesOKHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideConsumeApiClient(retrofit: Retrofit): OpenPayApiClient {
        return retrofit.create(OpenPayApiClient::class.java)
    }

    @Volatile
    private var databaseInstance: OpenPayDatabase? = null

    @Provides
    fun provideDataBase(@ApplicationContext context: Context): OpenPayDatabase =
        databaseInstance ?: synchronized(this) {
            Room.databaseBuilder(context, OpenPayDatabase::class.java, "OpenPayRoomDataBase")
                .fallbackToDestructiveMigration()
                .build()
                .also { databaseInstance = it }
        }

    @Singleton
    @Provides
    fun providePersonDetailViewDataDao(db: OpenPayDatabase): DBDetailPersonDataDao = db.personDetailDataDao()

    @Singleton
    @Provides
    fun providePopularMoviesViewDataDao(db: OpenPayDatabase): DBPopularMoviesDataDao = db.popularMoviesDataDao()

    @Singleton
    @Provides
    fun provideMostRatedMoviesViewDataDao(db: OpenPayDatabase): DBMostRatedMoviesDataDao = db.mostRatedMoviesDataDao()

    @Singleton
    @Provides
    fun provideUpcomingMoviesViewDataDao(db: OpenPayDatabase): DBUpcomingMoviesDataDao = db.upcomingMoviesDataDao()

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

}