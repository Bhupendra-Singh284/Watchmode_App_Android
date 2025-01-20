package com.example.watchmodeapp.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watchmodeapp.R
import com.example.watchmodeapp.data.models.Title
import com.example.watchmodeapp.presentation.TitlesList.TitleCard
import com.example.watchmodeapp.presentation.TitlesList.TitlesListLoadingScreen
import com.example.watchmodeapp.presentation.TitlesList.TitlesListViewModel
import com.example.watchmodeapp.ui.theme.RobotoFamily
import com.example.watchmodeapp.ui.theme.background
import com.example.watchmodeapp.ui.theme.lightGreen
import com.example.watchmodeapp.ui.theme.white
import com.example.watchmodeapp.utils.ResultState
import com.example.watchmodeapp.utils.Tabs

@Composable
fun TitlesListScreen(vm: TitlesListViewModel, navigateToDetails: (Int) -> Unit) {
    val state by vm.listState.collectAsState()
    Column(
        Modifier
            .fillMaxSize()
            .background(background)
            .statusBarsPadding()
            .padding(start = 20.dp, end = 20.dp, bottom = 50.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.watchmode_logo),
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 15.dp)
                .size(150.dp, 40.dp),
            contentScale = ContentScale.FillBounds
        )
        HorizontalDivider(
            thickness = 0.5f.dp,
            color = white,
            modifier = Modifier.padding(bottom = 15.dp)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                border = if (vm.currentTab.value == Tabs.SHOWS) BorderStroke(
                    width = 0.5.dp,
                    color = white
                ) else null,
                enabled = vm.currentTab.value == Tabs.SHOWS,
                contentPadding = PaddingValues(vertical = 10.dp, horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = white,
                    disabledContainerColor = lightGreen,
                    disabledContentColor = white
                ), onClick =
                {
                    vm.currentTab.value = Tabs.MOVIES
                }) {
                Text(
                    "Movies",
                    fontWeight = FontWeight.Bold,
                    fontFamily = RobotoFamily,
                    fontSize = 20.sp
                )
            }
            Button(
                border = if (vm.currentTab.value == Tabs.MOVIES) BorderStroke(
                    width = 0.5.dp,
                    color = white
                ) else null,
                enabled = vm.currentTab.value == Tabs.MOVIES,
                contentPadding = PaddingValues(vertical = 10.dp, horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = white,
                    disabledContainerColor = lightGreen,
                    disabledContentColor = white
                ), onClick =
                {
                    vm.currentTab.value = Tabs.SHOWS
                }) {
                Text(
                    "Shows",
                    fontWeight = FontWeight.Bold,
                    fontFamily = RobotoFamily,
                    fontSize = 20.sp
                )
            }
        }
        when (state) {
            null -> {

                LaunchedEffect(Unit) {
                    vm.getLists()
                }

            }

            is ResultState.Loading -> {
                TitlesListLoadingScreen()

            }

            is ResultState.Success -> {
                val titlesList =
                    (state as ResultState.Success<List<List<Title>>>).data

                when (vm.currentTab.value) {
                    Tabs.MOVIES -> {
                        LazyColumn(Modifier.fillMaxSize()) {
                            item {
                                Modifier.height(5.dp)
                            }
                            items(titlesList[0].size) { index ->
                                TitleCard(
                                    (index + 1).toString(),
                                    titlesList[0][index].id ?: 0,
                                    titlesList[0][index].title ?: "NA",
                                    titlesList[0][index].year?.toString() ?: "NA"
                                ) {
                                    navigateToDetails(titlesList[0][index].id ?: 0)
                                }
                            }
                        }
                    }

                    Tabs.SHOWS -> {
                        LazyColumn(Modifier.fillMaxSize()) {
                            item {
                                Modifier.height(5.dp)
                            }
                            items(titlesList[1].size) { index ->
                                TitleCard(
                                    (index + 1).toString(),
                                    titlesList[1][index].id ?: 0,
                                    titlesList[1][index].title ?: "NA",
                                    titlesList[1][index].year?.toString() ?: "NA"
                                ) {
                                    navigateToDetails(titlesList[1][index].id ?: 0)
                                }
                            }
                        }
                    }
                }
            }

            is ResultState.Failure -> {
                val message = (state as ResultState.Failure).error
                Column(
                    Modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_network_error),
                        contentDescription = "NETWORK_ERROR",
                        modifier = Modifier.size(120.dp),
                        tint = Color.LightGray
                    )
                    Text(
                        modifier = Modifier.padding(vertical = 20.dp),
                        text = message,
                        fontFamily = RobotoFamily,
                        color = white,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = lightGreen,
                            contentColor = white
                        ),
                        onClick = {
                            vm.getLists()
                        }) {
                        Icon(Icons.Rounded.Refresh, contentDescription = "RETRY_ICON")
                        Text("Retry", fontFamily = RobotoFamily)
                    }
                }
            }
        }
    }
}