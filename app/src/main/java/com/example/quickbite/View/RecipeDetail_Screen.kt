package com.example.quickbite.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ShapeDefaults.Medium
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottiePainter
import com.example.quickbite.Data.Remote.SpoonacularService
import com.example.quickbite.R
import com.example.quickbite.View.Components.ShadowBackground
import com.example.quickbite.ViewModel.SpoonacularViewModel


@Composable
fun RecipeDetail_Screen(
    modifier: Modifier,
    recipe: SpoonacularService.Recipe,
    viewModel: SpoonacularViewModel
) {
    val instructions = viewModel.instruction

    LaunchedEffect(recipe.id) {
        viewModel.getInstructions(recipe.id)
    }
    DetailBody(modifier, recipe, instructions)
}


@Composable
fun DetailBody(
    modifier: Modifier,
    recipe: SpoonacularService.Recipe,
    instructions: List<SpoonacularService.Step>
) {
    //Sacamos los ingredientes de todos los pasos y los añadimos a esta lista
    val ingredients = instructions.flatMap { it.ingredients }.distinctBy { it.id }
    Column(
        modifier
            .fillMaxSize()
            .background(Color(0xFF0a0c0c))
            .verticalScroll(rememberScrollState())

    ) {
        DetailImage(recipe.image)

        Card(
            Modifier
                .width(250.dp)
                .height(100.dp)
                .align(Alignment.CenterHorizontally)
                .offset(y = ((-30).dp)),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1d2721)),
            elevation = CardDefaults.cardElevation(defaultElevation = 7.dp),

            ) {
            Text(
                recipe.title,
                Modifier
                    .padding(start = 5.dp, end = 5.dp, top = 5.dp)
                    .align(Alignment.CenterHorizontally),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFf7f7f7)
            )
            HorizontalDivider(
                Modifier.padding(top = 3.dp),
                thickness = 1.dp,
                color = Color(0xFF01bd5f)
            )
        }

        IngredientsSection(
            Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 20.dp), ingredients
        )

        StepsSection(
            Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp),
            instructions
        )


    }

}

@Composable
fun StepsSection(modifier: Modifier, instructions: List<SpoonacularService.Step>) {
    Column(modifier = modifier.padding(top = 20.dp)) {
        Text(
            "Cooking Instructions",
            Modifier
                .padding(bottom = 15.dp, start = 10.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFf7f7f7)
        )

        Column(Modifier.fillMaxWidth()) {
            instructions.forEach { step ->
                StepCard(step)
            }

        }


    }
}

@Composable
fun StepCard(step: SpoonacularService.Step) {
    Card(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(end = 8.dp, bottom = 30.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1d2721)),

        ) {
        Column(Modifier.fillMaxWidth()) {
            Text(
                "STEP : " + step.number,
                Modifier
                    .padding(start = 10.dp, top = 10.dp)
                    .align(Alignment.Start),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF01bd5f)
            )
            Spacer(Modifier.padding(5.dp))
            Text(
                step.step, Modifier
                    .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                    .align(Alignment.Start),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Left,
                minLines = 3,
                color = Color(0xFFf7f7f7)
            )

        }
    }

}

@Composable
fun IngredientsSection(modifier: Modifier, ingredients: List<SpoonacularService.Ent>) {

    Column(modifier = modifier.padding(top = 10.dp)) {
        Text(
            "Ingredients",
            Modifier
                .padding(bottom = 15.dp, start = 10.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFf7f7f7)
        )

        LazyRow(
            Modifier
                .fillMaxWidth()
        ) {
            items(ingredients) { ingredient ->
                IngredientsCard(ingredient)

            }

        }
    }
}

@Composable
fun IngredientsCard(ingredient: SpoonacularService.Ent) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.placeholder_animation))
    val progress by animateLottieCompositionAsState(composition)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .size(80.dp)// Tamaño del círculo
                .clip(CircleShape),// Recorte en forma de círculo
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://spoonacular.com/cdn/ingredients_100x100/${ingredient.image}")
                    .crossfade(true)
                    .build(),
                contentDescription = "Ingredient Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape) // Hace que la imagen sea completamente circular
                ,
                placeholder = rememberLottiePainter(composition = composition, progress = progress)
            )
        }
        Spacer(modifier = Modifier.height(8.dp)) // Espacio entre la imagen y el texto
        Text(
            text = ingredient.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}



@Composable
fun DetailImage(image: String) {
    Surface(
        Modifier
            .fillMaxWidth()
            .height(350.dp)
            .clip(
                RoundedCornerShape(bottomEnd = 60.dp, bottomStart = 60.dp)
            )
    )
    {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
            contentDescription = "Recipe Image",
            contentScale = ContentScale.FillBounds,
            placeholder = painterResource(id = R.drawable.opcion_receta)
        )
        ShadowBackground()
    }
}


@Preview(showBackground = true)
@Composable
fun DetailBodyPreview() {
    DetailBody(
        Modifier,
        recipe = SpoonacularService.Recipe(
            id = 1,
            title = "Delicious Pasta",
            image = ""
        ),
        instructions = listOf(
            SpoonacularService.Step(
                number = 1,
                step = "Este es el paso 1",
                ingredients = emptyList() // Debe ser una lista vacía o con objetos válidos
            ),
            SpoonacularService.Step(
                number = 2,
                step = "Este es el paso 2",
                ingredients = emptyList()
            )
        )
    )
}
