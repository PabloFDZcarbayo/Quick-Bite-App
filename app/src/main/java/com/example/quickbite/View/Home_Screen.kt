package com.example.quickbite.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottiePainter
import com.example.quickbite.Model.Category
import com.example.quickbite.R
import com.example.quickbite.View.Components.MainFooter
import com.example.quickbite.View.Components.ShadowBackground
import com.example.quickbite.ViewModel.UnsplashViewModel


/*
@Preview(showBackground = true)
@Composable
fun Home_ScreenPreview() {
    Home_Screen(Modifier, ViewModel = viewModel(), navigateToRecipesCategories = { })
}
 */

@Composable
fun Home_Screen(
    modifier: Modifier,
    viewModel: UnsplashViewModel,
    navigateToRecipesCategories: () -> Unit,
    navigateToRecipesList: (String) -> Unit
) {
    val categories = viewModel.categories.subList(0, 4)
    val isLoading = viewModel.isLoading.value

    Column(
        modifier
            .fillMaxSize()
            .background(color = Color(0xFF0a0c0c))
    ) {
        HomeTopSection(
            Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xFF0a0c0c))
        )
        HomeBody(
            Modifier
                .align(Alignment.CenterHorizontally)
                .background(Color(0xFF0a0c0c)),
            categories,
            isLoading,
            navigateToRecipesCategories,
            navigateToRecipesList
        )

        Spacer(Modifier.weight(2f))
        MainFooter(Modifier.align(Alignment.CenterHorizontally))
    }
}


@Composable
fun HomeBody(
    modifier: Modifier,
    categories: List<Category>,
    isLoading: Boolean,
    navigateToRecipesCategories: () -> Unit,
    navigateToRecipesList: (String) -> Unit

) {

    Column(
        modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                "Categorys",
                fontSize = 24.sp,
                fontWeight = Bold,
                color = Color(0xFFf7fdfd)
            )
            Spacer(Modifier.width(150.dp))

            Text(
                "See all >>",
                Modifier.clickable { navigateToRecipesCategories() },
                fontSize = 16.sp,
                color = Color(0xFF01bd5f)
            )
        }
        if (!isLoading) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { category ->
                    OpcionCard(category, navigateToRecipesList)
                }
            }
        } else {
            CircularProgressIndicator(
                Modifier
                    .size(60.dp)
                    .padding(top = 80.dp)
                    .align(Alignment.CenterHorizontally),
                color = Color(0xFF01bd5f),
                strokeWidth = 5.dp
            )
        }
    }
}

@Composable
fun OpcionCard(category: Category, navigateToRecipesList: (String) -> Unit) {
    Card(
        Modifier
            .padding(top = 20.dp, bottom = 20.dp)
            .width(180.dp)
            .height(180.dp)
            .clickable { navigateToRecipesList(category.name) },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1d2721)),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    )
    {
        Column(
            Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            CardImage(category.image)
            Text(
                category.name,
                Modifier
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp)
                    .align(Alignment.CenterHorizontally),
                fontWeight = Bold,
                maxLines = 1,
                fontSize = 20.sp,
                letterSpacing = 3.sp,
                color = Color(0xFF6fb8500)
            )
        }
    }
}


@Composable
fun CardImage(image: String) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.placeholder_animation))
    val progress by animateLottieCompositionAsState(composition)
    Box(
        Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .fillMaxWidth()
                .height(165.dp),
            contentDescription = "Category Image",
            contentScale = ContentScale.Crop,
            placeholder = rememberLottiePainter(composition = composition, progress = progress)
        )
        ShadowBackground()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopSection(modifier: Modifier) {
    var busqueda by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    Column(modifier.padding(top = 30.dp)) {
        Welcome()
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
            query = busqueda,
            onQueryChange = { busqueda = it },
            onSearch = { },
            active = active,
            onActiveChange = { active = it },
            placeholder = {
                Text(
                    "¿Que necesitas hoy?",
                    color = Color(0xFF57665f),
                    fontSize = 15.sp
                )
            },
            colors = SearchBarDefaults.colors(
                containerColor = Color(0xFF1d2721)
            ),
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(15.dp),
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Buscar",
                    tint = Color(0xFF57665f)
                )
            }
        ) { }

    }

}

@Composable
fun Welcome() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "¡Bienvenido, User!",
            fontSize = 25.sp,
            fontWeight = Bold,
            color = Color(0xFFf7fdfd)
        )
        Spacer(Modifier.weight(1f))
        Image(
            painterResource(id = R.drawable.prueba_imagen_perfil),
            contentDescription = "User",
            Modifier
                .size(80.dp)
                .clip(shape = CircleShape)
        )

    }
}


