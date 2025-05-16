package com.aashushaikh.movieappcompose.movie.data.local.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Serializable
//data class MovieEntity(
//    @SerialName("adult") val adult: Boolean,
//    @SerialName("backdrop_path") val backdropPath: String,
//    @SerialName("genre_ids") val genreIds: List<Int>,
//    @SerialName("id") val id: Int,
//    @SerialName("original_language") val originalLanguage: String,
//    @SerialName("original_title") val originalTitle: String,
//    @SerialName("overview") val overview: String,
//    @SerialName("popularity") val popularity: Double,
//    @SerialName("poster_path") val posterPath: String,
//    @SerialName("release_date") val releaseDate: String,
//    @SerialName("title") val title: String,
//    @SerialName("video") val video: Boolean,
//    @SerialName("vote_average") val voteAverage: Double,
//    @SerialName("vote_count") val voteCount: Int
//)

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val movieId: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)
