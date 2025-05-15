package com.aashushaikh.movieappcompose.movie.presentation.movie_list_sreen.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.Coil
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.util.DebugLogger
import com.aashushaikh.movieappcompose.R
import com.aashushaikh.movieappcompose.movie.domain.models.Movie
import com.aashushaikh.movieappcompose.utils.LightBlue
import com.aashushaikh.movieappcompose.utils.SandYellow
import kotlin.math.round

@Composable
fun MovieListItem(
    movie: Movie,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        Log.d("CoilInit", "Setting custom ImageLoader with DebugLogger")
        Coil.setImageLoader(
            ImageLoader.Builder(context)
                .logger(DebugLogger())
                .build()
        )
    }

    Surface(
        shape = RoundedCornerShape(32.dp),
        modifier = modifier
            .clickable { onClick() },
        color = LightBlue.copy(alpha = 0.2f)
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
//                var imageLoadResult by remember {
//                    mutableStateOf<Result<Painter>?>(null)
//                }
//                val fallbackUrl = "https://image.tmdb.org/t/p/w500/dDlfjR7gllmr8HTeN6rfrYhTdwX.jpg"
//                val imageUrl = if (!movie.poster_path.isNullOrBlank()) {
//                    "https://image.tmdb.org/t/p/w500${movie.poster_path}"
//                } else {
//                    fallbackUrl
//                }
//                val painter = rememberAsyncImagePainter(
//                    model = imageUrl,
//                    onSuccess = {
//                        Log.d("ImageLoader", "onSuccess called with painter: ${it.painter}")
//                        Log.d("ImageLoader", "Intrinsic size: ${it.painter.intrinsicSize}")
//                        imageLoadResult = if(it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1){
//                            Log.d("ImageLoader", "Image load success - result updated to success")
//                            Result.success(it.painter)
//                        }else{
//                            Log.d("ImageLoader", "Image load failure - result updated to failure")
//                            Result.failure(Exception("Invalid image size"))
//                        }
//                    },
//                    onError = {
//                        it.result.throwable.printStackTrace()
//                        Log.e("ImageLoader", "onError called - image load failed: ${it.result.throwable}")
//                        imageLoadResult = Result.failure(it.result.throwable)
//                    }
//                )
//
//                Log.d("ImageLoader", "Image URL: $imageUrl")
//                Log.d("ImageLoader", "Painter state: ${painter.state}")
//                Log.d("ImageLoader", "imageLoadResult value: $imageLoadResult")
//
//                when(val result = imageLoadResult){
//                    null -> CircularProgressIndicator()
//                    else -> {
//                        Log.d("ImageLoader", "Final painter used: ${if (result.isSuccess) "Network image" else "Fallback drawable"}")
//                        Image(
//                            painter = if(result.isSuccess) painter else painterResource(R.drawable.ic_movie),
//                            contentDescription = movie.title,
//                            contentScale = if(result.isSuccess){
//                                ContentScale.Crop
//                            }else{
//                                ContentScale.Fit
//                            },
//                            modifier = Modifier
//                                .aspectRatio(
//                                    ratio = 0.65f,
//                                    matchHeightConstraintsFirst = true
//                                )
//                        )
//                    }
//                }
                val fallbackUrl = "https://image.tmdb.org/t/p/w500/dDlfjR7gllmr8HTeN6rfrYhTdwX.jpg"
                val imageUrl = if (!movie.poster_path.isNullOrBlank()) {
                    "https://image.tmdb.org/t/p/w500${movie.poster_path}"
                } else {
                    fallbackUrl
                }

                Log.d("ImageLoader", "Image URL: $imageUrl")

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
                    contentDescription = movie.title,
                    placeholder = painterResource(R.drawable.ic_movie),
                    error = painterResource(R.drawable.ic_movie),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(ratio = 0.65f, matchHeightConstraintsFirst = true)
                )

            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 16.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = movie.release_date,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${round(movie.vote_average * 10) / 10.0}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Icon(
                        imageVector = Icons.Filled.Star,
                        tint = SandYellow,
                        contentDescription = null
                    )
                }

            }

            Icon(
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .padding()
            )

        }

    }
    
}