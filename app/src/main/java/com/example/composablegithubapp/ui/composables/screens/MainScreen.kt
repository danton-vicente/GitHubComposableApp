package com.example.composablegithubapp.ui.composables.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.composablegithubapp.ui.theme.Typography
import com.example.composablegithubapp.ui.viewmodels.MainViewModel
import com.example.composablegithubapp.ui.viewmodels.states.MainScreenViewState
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController,
    onBackPressed: () -> Unit,
    errorToast: (String) -> Unit,
) {

    val state by mainViewModel.state.collectAsState()
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    var page by rememberSaveable { mutableIntStateOf(1) }

    LaunchedEffect(key1 = Unit) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            launch {
                mainViewModel.getData(page)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 16.dp),
            text = "GitHub App"
        )

        when (state.screenStatus) {
            MainScreenViewState.LOADING -> {
                repeat(5) {
                    CardShimmer()
                    Spacer(
                        modifier = Modifier
                            .size(8.dp)
                    )
                }
            }

            MainScreenViewState.SUCCESS,
            MainScreenViewState.NEW_PAGE_LOADING -> {
                state.list.mapIndexed { index, repositoriesResponse ->
                    Card(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 8.dp
                        )
                    ) {

                        ConstraintLayout(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            // Create references for the composables to constrain
                            val (image, name, stars, forks, repoName) = createRefs()

                            AsyncImage(modifier = Modifier
                                .constrainAs(image) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                }
                                .size(32.dp),
                                model = repositoriesResponse.userData.userIcon,
                                contentDescription = null)

                            Text(
                                modifier = Modifier.constrainAs(repoName) {
                                    top.linkTo(image.top)
                                    start.linkTo(image.end, margin = 8.dp)
                                },
                                text = repositoriesResponse.userName,
                                style = Typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )

                            Text(
                                modifier = Modifier.constrainAs(name) {
                                    top.linkTo(repoName.bottom, margin = 8.dp)
                                    start.linkTo(repoName.start)
                                },
                                text = repositoriesResponse.userData.userLogin,
                                style = Typography.labelSmall,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.secondary
                            )

                            Text(
                                modifier = Modifier.constrainAs(stars) {
                                    end.linkTo(parent.end, margin = 16.dp)
                                    top.linkTo(repoName.top)
                                },
                                text = "Estrelas: ${repositoriesResponse.stars}",
                                style = Typography.bodySmall,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.tertiary
                            )

                            Text(
                                modifier = Modifier.constrainAs(forks) {
                                    top.linkTo(stars.bottom, margin = 8.dp)
                                    start.linkTo(stars.start)
                                },
                                text = "Forks: ${repositoriesResponse.forks}",
                                style = Typography.bodySmall,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier
                            .size(8.dp)
                    )
                }

                if (state.screenStatus == MainScreenViewState.NEW_PAGE_LOADING) {
                    CardShimmer()
                } else {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                        onClick = {
                            page += 1
                            mainViewModel.getData(page)
                        }) {
                        Text(
                            text = "Carregar mais...",
                        )
                    }
                }
            }

            MainScreenViewState.ERROR,
            MainScreenViewState.NEW_PAGE_ERROR -> {
                errorToast(state.error ?: "Erro inesperado, tente novamente")
            }
        }
    }
}

@Preview
@Composable
fun previewScreen() {
    MaterialTheme {
    }
}

@Composable
fun CardShimmer() {
    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            val (imageShimmer, nameShimmer, repoNameShimmer) = createRefs()

            Spacer(
                modifier = Modifier
                    .shimmer()
                    .size(width = 32.dp, height = 32.dp)
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .constrainAs(imageShimmer) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
            )

            Spacer(
                modifier = Modifier
                    .constrainAs(repoNameShimmer) {
                        top.linkTo(imageShimmer.top)
                        start.linkTo(imageShimmer.end, margin = 8.dp)
                    }
                    .width(250.dp)
                    .height(20.dp)
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(4.dp)
                    ),
            )

            Spacer(
                modifier = Modifier
                    .constrainAs(nameShimmer) {
                        top.linkTo(repoNameShimmer.bottom, margin = 8.dp)
                        start.linkTo(repoNameShimmer.start)
                    }
                    .width(200.dp)
                    .height(15.dp)
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(4.dp)
                    ),
            )
        }
    }
}
