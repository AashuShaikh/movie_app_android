package com.aashushaikh.movieappcompose.movie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aashushaikh.movieappcompose.utils.IntListConverter

@Database(entities = [MovieEntity::class], version = 1)
@TypeConverters(IntListConverter::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao

}