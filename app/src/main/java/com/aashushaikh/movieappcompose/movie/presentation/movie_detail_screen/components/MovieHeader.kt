package com.aashushaikh.movieappcompose.movie.presentation.movie_detail_screen.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aashushaikh.movieappcompose.R
import com.aashushaikh.movieappcompose.movie.presentation.model.MovieDetailUi
import com.aashushaikh.movieappcompose.movie.presentation.movie_detail_screen.dummyMovieDetailUi
import com.aashushaikh.movieappcompose.utils.DarkBlue

@Composable
fun MovieHeader(
    movieDetailUi: MovieDetailUi,
    onBookmarkClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
    ){
        val fallbackUrl = "https://image.tmdb.org/t/p/w500/dDlfjR7gllmr8HTeN6rfrYhTdwX.jpg"
        val imageUrl = if (!movieDetailUi.backdropPath.isNullOrBlank()) {
            "https://image.tmdb.org/t/p/w500${movieDetailUi.backdropPath}"
        } else {
            fallbackUrl
        }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .listener(
                    onSuccess = { request, result ->
                        Log.d("ImageLoader", "onSuccess called for URL: $imageUrl")
                        Log.d("ImageLoader", "Drawable intrinsic size: ${result.drawable.intrinsicWidth} x ${result.drawable.intrinsicHeight}")
                    },
                    onError = { request, throwable ->
                        Log.e("ImageLoader", "onError called - image load failed: ${throwable.throwable}")
                    }
                )
                .build(),
            contentDescription = movieDetailUi.title,
            placeholder = painterResource(R.drawable.ic_movie),
            error = painterResource(R.drawable.ic_movie),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.66f))
        )
        IconButton(
            onClick = {
                onBookmarkClick(movieDetailUi.bookmared)
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
        ) {
            val bookmarkIcon = if(movieDetailUi.bookmared){
                Icons.Default.BookmarkRemove
            }else{
                Icons.Default.BookmarkAdd
            }
            Icon(
                imageVector = bookmarkIcon,
                contentDescription = "Bookmark",
                tint = DarkBlue,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHeader(modifier: Modifier = Modifier) {
    MovieHeader(
        movieDetailUi = dummyMovieDetailUi,
        onBookmarkClick = {},
        modifier = Modifier.statusBarsPadding()
    )
}