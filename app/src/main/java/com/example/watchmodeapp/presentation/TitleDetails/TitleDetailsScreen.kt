package com.example.watchmodeapp.presentation.TitleDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.watchmodeapp.R
import com.example.watchmodeapp.data.models.TitleDetails
import com.example.watchmodeapp.ui.theme.RobotoFamily
import com.example.watchmodeapp.ui.theme.background
import com.example.watchmodeapp.ui.theme.lightGreen
import com.example.watchmodeapp.ui.theme.white
import com.example.watchmodeapp.utils.ResultState

@Composable
fun TitleDetailsScreen(vm: TitleDetailsViewModel, titleId: Int, navigateBack: () -> Unit) {
    val state = rememberScrollState()
    val context = LocalContext.current
    val result by vm.detailsState.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            vm.deleteDownloadedImage()
        }
    }

    when (result) {
        null -> {
            LaunchedEffect(Unit) {
                vm.getTitleDetails(titleId, context)
            }
        }

        is ResultState.Loading -> {
            TitleDetailsLoading()
        }

        is ResultState.Success -> {
            val it = (result as ResultState.Success<TitleDetails>).data
            Box(
                Modifier
                    .fillMaxSize()
                    .background(background)
            ) {
                Column(
                    Modifier.verticalScroll(state)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp), model = vm.imageFile,
                        error = painterResource(R.drawable.ic_network_error),
                        contentDescription = "TITLE_IMAGE",
                        contentScale = ContentScale.FillBounds
                    )
                    Column(
                        Modifier.padding(top = 15.dp, start = 20.dp, end = 20.dp, bottom = 50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            it.title ?: "NA",
                            color = white,
                            textAlign = TextAlign.Center,
                            fontStyle = FontStyle.Italic,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = RobotoFamily
                        )
                        Text(
                            "${getGenre(it.genre_names)}  ${
                                getDates(
                                    it.type,
                                    it.year,
                                    it.end_year
                                )
                            }",
                            fontFamily = RobotoFamily,
                            textAlign = TextAlign.Center,
                            color = white,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                ScoreValue(it.critic_score?.toString() ?: "NA")
                                ScoreTitle("Critics Rating")
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                ScoreValue(it.us_rating ?: "NA")
                                ScoreTitle("TV Rating")
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                ScoreValue(it.user_rating?.toString() ?: "NA")
                                ScoreTitle("User Rating")
                            }
                        }
                        Text(
                            text = it.plot_overview ?: "",
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center,
                            color = white.copy(alpha = 0.7f),
                            fontFamily = RobotoFamily,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
                Button(
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = white
                    ),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 30.dp, start = 5.dp),
                    onClick = {
                        navigateBack()
                    }) {
                    Icon(
                        modifier = Modifier.size(35.dp),
                        imageVector =
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "BACK_ICON"
                    )
                }
            }
        }

        is ResultState.Failure -> {
            val error = (result as ResultState.Failure).error
            Box(
                Modifier
                    .fillMaxSize()
                    .background(background)
            ) {
                Button(
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = white
                    ),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 30.dp, start = 5.dp),
                    onClick = {
                        navigateBack()
                    }) {
                    Icon(
                        modifier = Modifier.size(35.dp),
                        imageVector =
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "BACK_ICON"
                    )
                }
                Column(
                    Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_network_error),
                        contentDescription = "NETWORK_ERROR",
                        tint = Color.LightGray,
                        modifier = Modifier.size(120.dp)
                    )
                    Text(
                        error,
                        fontFamily = RobotoFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = white,
                        modifier = Modifier.padding(vertical = 15.dp),
                        textAlign = TextAlign.Center
                    )
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = lightGreen,
                            contentColor = white
                        ),
                        onClick = {
                            vm.getTitleDetails(titleId, context)
                        }) {
                        Icon(Icons.Rounded.Refresh, contentDescription = "RETRY_ICON")
                        Text("Retry", fontFamily = RobotoFamily)
                    }
                }
            }

        }
    }
}

fun getGenre(genre: List<String>?): String {
    if (genre.isNullOrEmpty()) {
        return ""
    }
    val result = StringBuilder()
    genre.forEach {
        result.append(it)
        result.append(",")
    }
    result.deleteCharAt(result.length - 1)
    return result.toString()
}

fun getDates(type: String?, startYear: Int?, endYear: Int?): String {
    return when (type) {
        "movie" -> {
            "(${startYear ?: "NA"})"
        }

        else -> {
            "(${startYear ?: "NA"}-${if (endYear == null || endYear == 0) "Present" else "$endYear"})"
        }
    }
}

@Composable
fun ScoreTitle(str: String) {
    Text(
        str, fontFamily = RobotoFamily,
        color = white.copy(alpha = 0.7f),
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun ScoreValue(str: String) {
    Text(
        str, color = lightGreen,
        fontFamily = RobotoFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}


