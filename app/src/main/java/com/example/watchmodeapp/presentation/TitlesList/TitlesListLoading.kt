package com.example.watchmodeapp.presentation.TitlesList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun TitlesListLoadingScreen() {
    val state = rememberScrollState()
    Column(Modifier.verticalScroll(state)) {
        repeat(6) {
            Box(
                Modifier
                    .padding(top=10.dp,bottom = 10.dp)
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(color = darkBlue, shape = RoundedCornerShape(10.dp))
                    .padding(10.dp)
            ) {
                Row(Modifier.align(Alignment.Center), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier
                            .height(60.dp)
                            .background(
                                color = Color.LightGray.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .weight(1.5f).shimmerEffect()
                    ) {

                    }
                    Spacer(modifier = Modifier.weight(0.5f))
                    Box(
                        Modifier
                            .height(70.dp)
                            .background(
                                color = Color.LightGray.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .weight(6f).shimmerEffect()
                    ) {  }
                }
            }
        }
    }

}