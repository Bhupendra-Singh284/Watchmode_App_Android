package com.example.watchmodeapp.presentation.TitleDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.watchmodeapp.presentation.shimmerEffect
import com.example.watchmodeapp.ui.theme.darkBlue


@Composable
fun TitleDetailsLoading() {
    val state = rememberScrollState()
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(state)
            .background(color = darkBlue)
            .statusBarsPadding()
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(460.dp)
                .background(Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(10.dp))
                .shimmerEffect()
        ) {

        }
        Box(
            Modifier
                .padding(top = 15.dp)
                .size(180.dp, 40.dp)
                .background(
                    color = Color.Gray.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(10.dp)
                )
                .shimmerEffect()
        ) {

        }
        Row(
            Modifier
                .padding(vertical = 25.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                Modifier
                    .size(80.dp, 50.dp)
                    .background(
                        color = Color.Gray.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .shimmerEffect()
            ) {

            }
            Box(
                Modifier
                    .size(80.dp, 50.dp)
                    .background(
                        color = Color.Gray.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .shimmerEffect()
            ) {

            }
            Box(
                Modifier
                    .size(80.dp, 50.dp)
                    .background(
                        color = Color.Gray.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .shimmerEffect()
            ) {

            }
        }
        Box(
            Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(horizontal = 20.dp)
                .background(
                    color = Color.Gray.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(10.dp)
                )
                .shimmerEffect()
        ) {

        }
    }
}