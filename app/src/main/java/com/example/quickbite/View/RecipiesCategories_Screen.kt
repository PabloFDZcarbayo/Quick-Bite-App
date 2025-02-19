package com.example.quickbite.View

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.quickbite.Data.Remote.Repositories.UnsplashRepository
import com.example.quickbite.Data.Remote.UnsplashService
import com.example.quickbite.Model.Category
import com.example.quickbite.R
import com.example.quickbite.ViewModel.UnsplashViewModel


@Composable
fun RecipiesCategories_Screen(modifier: Modifier, viewModel: UnsplashViewModel) {

    val categories = viewModel.categories
    val isLoading = viewModel.isLoading.value

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
                    .background(Color(0xFFFFFFFF)),
                categories, //mandamos las categorias
                isLoading//indicamos si esta cargando
            )
        }
        Spacer(Modifier.weight(1f))
        HomeFooter(Modifier.align(Alignment.CenterHorizontally))

    }
}

@Composable
fun RecipiesCategoriesBody(modifier: Modifier, categories: List<Category>, isLoading: Boolean) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 10.dp, end = 10.dp),
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


        if (!isLoading) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(categories) { category ->
                    CategoryCard(category)
                }
            }
        } else {

            CircularProgressIndicator(
                Modifier
                    .size(60.dp)
                    .padding(top = 80.dp)
                    .align(Alignment.CenterHorizontally),
                color = Color(0xFFfb8500),
                strokeWidth = 5.dp
            )
        }
    }
}

@Composable
fun CategoryCard(category: Category) {
    Card(
        Modifier
            .padding(top = 20.dp, bottom = 20.dp)
            .width(100.dp)
            .height(100.dp)
            .clickable { },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 7.dp),
    ) {

        Row(Modifier.fillMaxWidth()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(category.image)
                    .crossfade(true)
                    .build(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                contentDescription = "Category Image",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.opcion_receta)
            )
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = category.name,
                modifier = Modifier.padding(top = 10.dp),
                fontSize = 15.sp,
                fontWeight = Bold,
                letterSpacing = 0.25.sp,
                color = Color(0xFF6fb8500)
            )
        }

    }
}


