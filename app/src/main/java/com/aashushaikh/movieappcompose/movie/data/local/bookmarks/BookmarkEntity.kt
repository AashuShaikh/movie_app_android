package com.aashushaikh.movieappcompose.movie.data.local.bookmarks

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val movieId: Int
)
