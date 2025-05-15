package com.aashushaikh.movieappcompose.movie.presentation.movie_detail_screen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aashushaikh.movieappcompose.R
import com.aashushaikh.movieappcompose.movie.domain.models.Movie
import com.google.gson.Gson

@Composable
fun MovieDetailScreen(movieJson: String?, modifier: Modifier = Modifier) {
    val gson = Gson()
    val movie = movieJson?.let { gson.fromJson(it, Movie::class.java) }
    Box(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        movie?.let {
            MovieBasicDetail(movie = movie)
        } ?: kotlin.run {
            Text(
                text = "Movie not found",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun MovieBasicDetail(movie: Movie) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (movie.poster_path != null) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                    contentDescription = "Movie Poster",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(150.dp)
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_movie),
                    contentDescription = "Movie Icon",
                    modifier = Modifier
                        .width(150.dp)
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_movie),
                        contentDescription = "Movie Icon",
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = movie.title ?: "Movie Title",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
//                Text(
//                    text = movie.genres ?: "",
//                    style = MaterialTheme.typography.headlineSmall,
//                    fontWeight = FontWeight.Light,
//                    fontStyle = FontStyle.Italic,
//                    maxLines = 2,
//                    overflow = TextOverflow.Ellipsis
//                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movie.release_date ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Language: ${movie.original_language ?: ""}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(thickness = 2.dp)
        MovieOverview(movie = movie)
    }
}

@Composable
fun MovieOverview(movie: Movie) {
    Text(
        text = "Movie Overview",
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 8.dp)
    )
    Text(
        text = movie.overview ?: "No overview available",
        style = MaterialTheme.typography.bodyLarge,
        fontStyle = FontStyle.Italic,
        maxLines = 10,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(8.dp)
    )
}