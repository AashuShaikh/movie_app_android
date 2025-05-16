package com.aashushaikh.movieappcompose.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.aashushaikh.movieappcompose.auth.data.token_management.AccessTokenInterceptor
import com.aashushaikh.movieappcompose.auth.data.token_management.AuthAuthenticator
import com.aashushaikh.movieappcompose.auth.data.token_management.JwtTokenDataStore
import com.aashushaikh.movieappcompose.auth.data.token_management.RefreshTokenInterceptor
import com.aashushaikh.movieappcompose.auth.data.repositories.AuthRepositoryImpl
import com.aashushaikh.movieappcompose.auth.data.remote.AuthService
import com.aashushaikh.movieappcompose.auth.domain.JwtTokenManager
import com.aashushaikh.movieappcompose.auth.data.remote.RefreshTokenService
import com.aashushaikh.movieappcompose.auth.domain.repositories.AuthRepository
import com.aashushaikh.movieappcompose.movie.data.local.movie.MovieDao
import com.aashushaikh.movieappcompose.movie.data.local.MovieDatabase
import com.aashushaikh.movieappcompose.movie.data.local.bookmarks.BookmarkDao
import com.aashushaikh.movieappcompose.movie.data.remote.MovieService
import com.aashushaikh.movieappcompose.movie.data.repositories.MovieRepositoryImpl
import com.aashushaikh.movieappcompose.movie.domain.repositories.MovieRepository
import com.aashushaikh.movieappcompose.utils.AppConstants
import com.aashushaikh.movieappcompose.utils.AppConstants.AUTH_REFERENCES
import com.aashushaikh.movieappcompose.utils.AuthenticatedClient
import com.aashushaikh.movieappcompose.utils.AuthenticatedService
import com.aashushaikh.movieappcompose.utils.PublicClient
import com.aashushaikh.movieappcompose.utils.PublicService
import com.aashushaikh.movieappcompose.utils.RefreshService
import com.aashushaikh.movieappcompose.utils.TokenRefreshClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        @AuthenticatedService movieService: MovieService,
        movieDao: MovieDao,
        bookmarkDao: BookmarkDao
    ): MovieRepository {
        return MovieRepositoryImpl(movieService, movieDao, bookmarkDao)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        @PublicService authService: AuthService,
        @RefreshService refreshTokenService: RefreshTokenService,
        jwtTokenManager: JwtTokenManager
    ): AuthRepository {
        return AuthRepositoryImpl(authService, refreshTokenService, jwtTokenManager)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = { appContext.preferencesDataStoreFile(AUTH_REFERENCES) }
        )
    }

    @Provides
    @Singleton
    fun provideJwtTokenManager(dataStore: DataStore<Preferences>): JwtTokenManager {
        return JwtTokenDataStore(dataStore = dataStore)
    }

    @[Provides Singleton AuthenticatedClient]
    fun provideAccessOkHttpClient(
        accessTokenInterceptor: AccessTokenInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .authenticator(authAuthenticator)
            .addInterceptor(accessTokenInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @[Provides Singleton TokenRefreshClient]
    fun provideRefreshOkHttpClient(
        refreshTokenInterceptor: RefreshTokenInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(refreshTokenInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @[Provides Singleton PublicClient]
    fun provideUnauthenticatedOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @AuthenticatedService
    fun provideMovieService(@AuthenticatedClient okHttpClient: OkHttpClient): MovieService {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MovieService::class.java)
    }

    @[Provides Singleton PublicService]
    fun provideAuthService(@PublicClient okHttpClient: OkHttpClient): AuthService {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(AuthService::class.java)
    }

    @Provides
    @Singleton
    @RefreshService
    fun provideRefreshTokenService(@TokenRefreshClient okHttpClient: OkHttpClient): RefreshTokenService {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RefreshTokenService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): MovieDatabase {
        return Room.databaseBuilder(
            appContext,
            MovieDatabase::class.java,
            "movie_database" // Name of your database
        ).build()
    }

    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.movieDao()
    }

    @Provides
    fun provideBookmarkDao(movieDatabase: MovieDatabase): BookmarkDao {
        return movieDatabase.bookmarkDao()
    }

}