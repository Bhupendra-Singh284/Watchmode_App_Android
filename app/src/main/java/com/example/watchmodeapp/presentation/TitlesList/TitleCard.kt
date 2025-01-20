package com.example.watchmodeapp.presentation.TitlesList

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watchmodeapp.ui.theme.RobotoFamily
import com.example.watchmodeapp.ui.theme.darkBlue
import com.example.watchmodeapp.ui.theme.white

@Composable
fun TitleCard(index: String, id: Int, title: String, year: String, onClick: (Int) -> Unit) {
    Card(
        Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
            .height(100.dp)
            .clickable {
                onClick(id)
            },
        border = BorderStroke(width = 0.1f.dp, color = white),
        colors = CardDefaults.cardColors(containerColor = darkBlue, contentColor = white)
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(15.dp)
                .wrapContentSize(align = Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$index.",
                modifier = Modifier.weight(1.5f),
                fontFamily = RobotoFamily,
                fontSize = 15.sp,
                softWrap = true,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Medium
            )
            Text(
                title,
                modifier = Modifier.weight(7.5f),
                fontFamily = RobotoFamily,
                fontSize = 18.sp,
                softWrap = true,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                year,
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 5.dp),
                fontFamily = RobotoFamily,
                fontSize = 16.sp,
                softWrap = true,
                textAlign = TextAlign.End,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
        }
    }
}