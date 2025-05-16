package com.aashushaikh.movieappcompose.movie.presentation.movie_detail_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.aashushaikh.movieappcompose.R
import com.aashushaikh.movieappcompose.movie.domain.models.Movie
import com.aashushaikh.movieappcompose.movie.domain.models.MovieDetail
import com.aashushaikh.movieappcompose.movie.presentation.model.MovieDetailUi
import com.aashushaikh.movieappcompose.movie.presentation.movie_detail_screen.components.MovieHeader
import com.aashushaikh.movieappcompose.movie.presentation.movie_detail_screen.components.MovieInfo
import com.aashushaikh.movieappcompose.movie.presentation.movie_list_sreen.MovieListViewModel
import com.google.gson.Gson

@Composable
fun MovieDetailScreenRoot(
    movieDetailViewModel: MovieDetailViewModel = hiltViewModel(),
    movieId: Int,
) {

    val movieDetailState = movieDetailViewModel.movieDetailState.collectAsState()
    Log.d("Movie", "MovieDetailScreenRoot: $movieId")
    LaunchedEffect(movieId) {
        movieDetailViewModel.getMovieById(movieId)
    }

    MovieDetailScreen(
        movieDetailState.value.movie,
        onBookmarkClick = { bookmark ->
            movieDetailViewModel.onAction(
                MovieDetailAction.ToggleBookmark(
                    !bookmark
                )
            )
        },
        modifier = Modifier.statusBarsPadding()
    )

}

@Composable
fun MovieDetailScreen(
    movie: MovieDetailUi?,
    onBookmarkClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Log.d("Movie", "movie: $movie")
    if (movie == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Loading...")
        }
    } else {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            MovieHeader(
                movieDetailUi = movie,
                onBookmarkClick = onBookmarkClick,
                modifier = modifier
            )
            MovieInfo(
                movie = movie
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewMovieDetail(modifier: Modifier = Modifier) {
    MovieDetailScreen(movie = dummyMovieDetailUi, onBookmarkClick = {})
}

val dummyMovieDetailUi = MovieDetailUi(
    title = "Inception",
    overview = "A skilled thief is offered a chance to have his past crimes forgiven if he implants another person's idea into a target's subconscious.",
    posterPath = "/dDlfjR7gllmr8HTeN6rfrYhTdwX.jpg",
    voteAverage = 8.8,
    releaseDate = "2010-07-16",
    backdropPath = "/dDlfjR7gllmr8HTeN6rfrYhTdwX.jpg",
    adult = false,
    originalLanguage = "en",
    originalTitle = "Inception",
    popularity = 82.5,
    voteCount = 30000,
    video = false,
    bookmared = true,

    // Nullable fields
    genres = listOf("Action", "Sci-Fi", "Thriller"),
    runtime = 148,
    originCountry = listOf("US"),
    spokenLanguages = listOf("English", "Japanese", "French"),
    movieId = 27205
)
