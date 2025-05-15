package com.aashushaikh.movieappcompose.movie.presentation.movie_list_sreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aashushaikh.movieappcompose.movie.domain.models.Movie
import com.aashushaikh.movieappcompose.movie.presentation.movie_list_sreen.components.MovieList
import com.aashushaikh.movieappcompose.movie.presentation.movie_list_sreen.components.MovieSearchBar
import com.aashushaikh.movieappcompose.utils.DarkBlue
import com.aashushaikh.movieappcompose.utils.DesertWhite
import com.aashushaikh.movieappcompose.utils.SandYellow

@Composable
fun MovieListScreenRoot(
    viewModel: MovieListViewModel = hiltViewModel(),
    onMovieClick: (movie: Movie) -> Unit,
    modifier: Modifier = Modifier
) {

    val state by viewModel.movieListState.collectAsState()

    MovieListScreen(
        state = state,
        onAction = {action ->
            when(action){
                is MovieListAction.OnMovieClicked -> {
                    onMovieClick(action.movie)
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
    
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MovieListScreen(
    state: MovieListState,
    onAction: (MovieListAction) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val pagerState = rememberPagerState { 2 }
    val searchResultListState = rememberLazyListState()
    val favouriteMoviesListState = rememberLazyListState()

    LaunchedEffect(state.searchResults) {
        searchResultListState.animateScrollToItem(0)
    }
    
    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        onAction(MovieListAction.OnTabSelected(pagerState.currentPage))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) { 
        MovieSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChanged = {
                onAction(MovieListAction.OnSearchQueryChanged(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )

        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            color = DesertWhite,
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TabRow(
                    selectedTabIndex = state.selectedTabIndex,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .widthIn(max = 700.dp)
                        .fillMaxWidth(),
                    indicator = {tabPosition ->
                        TabRowDefaults.SecondaryIndicator(
                            color = SandYellow,
                            modifier = Modifier.tabIndicatorOffset(tabPosition[state.selectedTabIndex])
                        )
                    }
                ) {
                    Tab(
                        selected = state.selectedTabIndex == 0,
                        onClick = { onAction(MovieListAction.OnTabSelected(0)) },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = "Movies",
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }

                    Tab(
                        selected = state.selectedTabIndex == 1,
                        onClick = { onAction(MovieListAction.OnTabSelected(1)) },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = "Bookmarks",
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {pageIndex ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        when(pageIndex){
                            0 -> {
                                if(state.isLoading){
                                    CircularProgressIndicator()
                                }else{
                                    when{
                                        state.error != null -> {
                                            Text(
                                                text = state.error,
                                                textAlign = TextAlign.Center,
                                                color = MaterialTheme.colorScheme.error,
                                                style = MaterialTheme.typography.headlineSmall
                                            )
                                        }
                                        state.searchResults.isEmpty() -> {
                                            Text(
                                                text = "OOPs, There aren't Movies",
                                                textAlign = TextAlign.Center,
                                                color = MaterialTheme.colorScheme.error,
                                                style = MaterialTheme.typography.headlineSmall
                                            )
                                        }
                                        else -> {
                                            MovieList(
                                                movies = state.searchResults,
                                                onMovieClick = {
                                                    onAction(MovieListAction.OnMovieClicked(it))
                                                },
                                                modifier = Modifier.fillMaxSize(),
                                                scrollState = searchResultListState
                                            )
                                        }
                                    }
                                }
                            }
                            1 -> {
                                if(state.bookmarkedMovies.isEmpty()){
                                    Text(
                                        text = "OOPs, There aren't any bookmarked Movies",
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.headlineSmall
                                    )
                                } else {
                                    MovieList(
                                        movies = state.bookmarkedMovies,
                                        onMovieClick = {
                                            onAction(MovieListAction.OnMovieClicked(it))
                                        },
                                        modifier = Modifier.fillMaxSize(),
                                        scrollState = favouriteMoviesListState
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

private val movies = (0..100).map {
    Movie(
        id = it,
        adult = false,
        backdrop_path = "/xUkUZ8eOnrOnnJAfusZUqKYZiDu.jpg",
        genre_ids = listOf(1, 2, 3),
        original_language = "en",
        original_title = "A Working Man",
        overview = "Levon Cade left behind a decorated military career in the black ops to live a simple life working construction. But when his boss's daughter, who is like family to him, is taken by human traffickers, his search to bring her home uncovers a world of corruption far greater than he ever could have imagined.",
        popularity = 798.7089,
        poster_path = "/xUkUZ8eOnrOnnJAfusZUqKYZiDu.jpg",
        release_date = "2025-03-2",
        title = "A Working Man",
        video = false,
        vote_average = 6.307,
        vote_count = 429
    )
}

@Preview
@Composable
private fun MovieListScreenPreview() {
    MovieListScreen(
        state = MovieListState(
            searchResults = movies
        ),
        onAction = {}
    )

}

//@Composable
//fun MovieListScreen(navController: NavController, viewModel: MovieListViewModel, modifier: Modifier = Modifier) {
//    val movieListState = viewModel.movieListState.collectAsState()
//    Box(modifier = modifier){
//        Column {
//            Text(
//                text = "Today's Random Movies",
//                style = MaterialTheme.typography.titleLarge,
//                modifier = Modifier.padding(8.dp)
//            )
//            when{
//                movieListState.value.isLoading -> {
//                    Row(
//                        horizontalArrangement = Arrangement.Center,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        CircularProgressIndicator()
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(text = "Loading Movies")
//                    }
//                }
//                movieListState.value.movieList?.isNotEmpty() == true -> {
//                    LazyColumn {
//                        items(movieListState.value.movieList!!){ movie ->
//                            MovieListItem(movie){
////                                val gson = Gson()
////                                val movieJson = gson.toJson(it)
////                                navController.navigate(Screen.MovieDetail.route + "?movie=${Uri.encode(movieJson)}")
//                            }
//                            HorizontalDivider(color = Color.Gray, thickness = 1.dp)
//                        }
//                    }
//                }
//                movieListState.value.error != null -> {
//                    Log.d("response", "Error: ${movieListState.value.error}")
//                    Text(
//                        text = "Error: ${movieListState.value.error}",
//                        style = MaterialTheme.typography.bodyLarge,
//                        modifier = Modifier.padding(8.dp)
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun MovieListItem(movie: MovieListItem, onMovieItemClick: (MovieListItem) -> Unit){
//    Box {
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//                .clickable { onMovieItemClick(movie) }
//        ) {
//            AsyncImage(
//                model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
//                contentDescription = "Movie Poster",
//                modifier = Modifier
//                    .width(50.dp)
//                    .height(50.dp)
//                    .clip(RoundedCornerShape(8.dp))
//            )
//            Spacer(modifier = Modifier.width(8.dp))
//            Column {
//                Row(
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = movie.title,
//                        fontWeight = FontWeight.Bold,
//                        style = MaterialTheme.typography.headlineSmall,
//                        modifier = Modifier.weight(1f),
//                        overflow = TextOverflow.Ellipsis,
//                        maxLines = 1
//                    )
//                    movie.release_date?.let {
//                        Text(
//                            text = it,
//                            fontWeight = FontWeight.Light,
//                            fontStyle = FontStyle.Italic,
//                            style = MaterialTheme.typography.bodyLarge,
//                        )
//                    }
//                }
//                Row(
//                    horizontalArrangement = Arrangement.Absolute.SpaceBetween,
//                    modifier = Modifier.fillMaxWidth()
//                ) {
////                    movie.genres?.let {
////                        Text(
////                            text = it,
////                            fontWeight = FontWeight.Light,
////                            fontStyle = FontStyle.Italic,
////                            style = MaterialTheme.typography.bodyLarge,
////                            overflow = TextOverflow.Ellipsis,
////                            maxLines = 1,
////                            modifier = Modifier.weight(1f)
////                        )
////                    }
//                    movie.original_language?.let {
//                        Text(
//                            text = it,
//                            fontWeight = FontWeight.Light,
//                            fontStyle = FontStyle.Italic,
//                            style = MaterialTheme.typography.bodyLarge,
//                        )
//                    }
//                }
//            }
//        }
//    }
//}