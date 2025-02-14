package com.example.quickbite.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun RecipiesCategories_ScreenPreview() {
    RecipiesCategories_Screen(Modifier)
}

@Composable
fun RecipiesCategories_Screen(modifier: Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .background(color = Color(0xFFa1d8cd))
    ) {
        Surface(
            Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(680.dp)
                .clip(
                    RoundedCornerShape(
                        bottomEnd = 34.dp,
                        bottomStart = 34.dp
                    )
                )

        ) {
            RecipiesCategoriesBody(
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(Color(0xFFFFFFFF))
            )
        }
        Spacer(Modifier.weight(1f))
        HomeFooter(Modifier.align(Alignment.CenterHorizontally))

    }
}

@Composable
fun RecipiesCategoriesBody(modifier: Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Categorias",
            Modifier.align(Alignment.Start),
            fontSize =
            30.sp,
            fontWeight = Bold,
            color = Color(0xFFfb8500)
        )
        Text(
            "Explora todas nuestras categorias de recetas",
            Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp, bottom = 16.dp),
            fontSize = 15.sp,
            color = Color(0xFF6fb8500)
        )

        HorizontalDivider(Modifier.fillMaxWidth(), thickness = 1.dp, color = Color(0xFF6fb8500))
        Row(
            modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.Center,

            ) {

        }
    }
}



