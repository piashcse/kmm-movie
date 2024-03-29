package ui.toprated

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import data.model.MovieItem
import moe.tlaster.precompose.navigation.Navigator
import navigation.NavigationScreen
import ui.component.MovieList
import ui.component.ProgressIndicator
import utils.AppString
import utils.network.DataState

@Composable
fun TopRated(navigator: Navigator, viewModel: TopRatedViewModel = TopRatedViewModel()) {
    LaunchedEffect(true) {
        viewModel.nowPlayingView(1)
    }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        viewModel.topRatedMovieResponse.collectAsState().value?.let {
            when (it) {
                is DataState.Loading -> {
                    ProgressIndicator()
                }

                is DataState.Success<List<MovieItem>> -> {
                    MovieList(it.data) { movieId ->
                        navigator.navigate(NavigationScreen.MovieDetail.route.plus("/$movieId"))
                    }
                }

                is DataState.Error -> {
                    Text("${AppString.ERROR_TEXT} ${it.exception}")
                }

            }
        }
    }
}