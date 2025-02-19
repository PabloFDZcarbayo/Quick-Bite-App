package com.example.quickbite.View

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.quickbite.Data.Remote.SpoonacularService
import com.example.quickbite.R
import com.example.quickbite.View.Components.MainFooter
import com.example.quickbite.ViewModel.SpoonacularViewModel

@Composable
fun ListOfRecipes_Screen(modifier: Modifier, name: String, viewModel: SpoonacularViewModel) {
    val recipes = viewModel.recipesList
    /* Le pasamos la key a LaunchedEffect esto asegura que
     cada vez que el name cambie, se actualizará */
    LaunchedEffect(name) {
        viewModel.getRecipesType(name)
    }
    Column(
        modifier
            .fillMaxSize()
            .background(color = Color(0xFFa1d8cd))
    ) {
        Text(
            "$name", Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 28.dp),
            textAlign = TextAlign.Centergit ,
            fontSize = 40.sp,
            fontWeight = Bold,
            color = Color(0xFFfb8500)
        )


        Surface(
            Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(top = 15.dp)
                .height(591.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 34.dp,
                        topEnd = 34.dp,
                        bottomEnd = 34.dp,
                        bottomStart = 34.dp
                    )
                )
        ) {
            ListOfRecipesBody(
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(Color(0xFFFFFFFF)),
                recipes = recipes

            )
        }
        Spacer(Modifier.weight(1f))
        MainFooter(Modifier.align(Alignment.CenterHorizontally))

    }
}

@Composable
fun ListOfRecipesBody(modifier: Modifier, recipes: List<SpoonacularService.Recipe>) {
    modifier.padding(top = 20.dp, start = 10.dp, end = 10.dp)

    Column(
        modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)

    ) {
        LazyColumn(
            Modifier.fillMaxSize().padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(recipes) { recipe ->
                RecipeCard(recipe)
            }

        }
    }

}

@Composable
fun RecipeCard(recipe: SpoonacularService.Recipe) {
    Card(
        Modifier
            .padding(top = 5.dp, bottom = 20.dp)
            .width(340.dp)
            .height(220.dp)
            .clickable { },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 7.dp),
    ) {

        Row(Modifier.fillMaxWidth()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(recipe.image)
                    .crossfade(true)
                    .build(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(165.dp),
                contentDescription = "Category Image",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.opcion_receta)
            )
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = recipe.title,
                modifier = Modifier.padding(top = 10.dp),
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                fontWeight = Bold,
                letterSpacing = 0.25.sp,
                color = Color(0xFF6fb8500)
            )
        }

    }
}
