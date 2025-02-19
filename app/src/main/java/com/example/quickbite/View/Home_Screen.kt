package com.example.quickbite.View

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickbite.Model.Opcion
import com.example.quickbite.R
import com.example.quickbite.View.Components.MainFooter


@Preview(showBackground = true)
@Composable
fun Home_ScreenPreview() {
    Home_Screen(Modifier, navigateToRecipiesCategories = { })
}

@Composable
fun Home_Screen(modifier: Modifier, navigateToRecipiesCategories: () -> Unit) {
    Column(
        modifier
            .fillMaxSize()
            .background(color = Color(0xFFa1d8cd))
    ) {
        HomeTopSection(
            Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xFFa1d8cd))
        )
        Surface(
            Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(480.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 34.dp,
                        topEnd = 34.dp,
                        bottomEnd = 34.dp,
                        bottomStart = 34.dp
                    )
                )

        ) {
            HomeBody(
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(Color(0xFFFFFFFF)),
                navigateToRecipiesCategories
            )
        }
        Spacer(Modifier.weight(1f))
        MainFooter(Modifier.align(Alignment.CenterHorizontally))
    }
}


@Composable
fun HomeBody(modifier: Modifier, navigateToRecipiesCategories: () -> Unit) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.Center,

        ) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(getOpciones()) { Opcion ->
                OpcionCard(Opcion, navigateToRecipiesCategories)
            }
        }
    }
}

fun getOpciones(): List<Opcion> {
    return listOf(
        Opcion(
            1,
            "Recetas",
            "Explora nuestra amplia variedad de recetas",
            R.drawable.opcion_receta
        ),
        Opcion(
            2,
            "Lista de compra",
            "¿Cansado de pensar que comprar? Prueba nuestra lista de compra automatica",
            R.drawable.opcion_lista_compra
        )
    )
}


@Composable
fun OpcionCard(opcion: Opcion, navigateToRecipiesCategories: () -> Unit) {
    Card(
        Modifier
            .padding(top = 20.dp, bottom = 20.dp)
            .width(340.dp)
            .height(330.dp)
            .clickable { if(opcion.id == 1) navigateToRecipiesCategories() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    )

    {
        Column(
            Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            CardImage(opcion)
            Text(
                opcion.nombre,
                Modifier
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .align(Alignment.CenterHorizontally),
                fontWeight = ExtraBold,
                fontSize = 30.sp,
                letterSpacing = 3.sp,
                color = Color(0xFF6fb8500)
            )
            Text(
                text = opcion.descripcion,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp, start = 20.dp, end = 20.dp, bottom = 10.dp),
                fontWeight = Bold,
                fontSize = 15.sp,
                color = Color(0xFF6fb8500),
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun CardImage(opcion: Opcion) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        Image(
            painter = painterResource(id = opcion.poster),
            contentDescription = "opcion",
            Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent, // Transparente en la parte superior
                            Color.Black.copy(alpha = 0.5f) // Negro semitransparente en la parte inferior
                        ), startY = 0f, // Comienza en la parte superior
                        endY = Float.POSITIVE_INFINITY // Termina en la parte inferior
                    )
                )
        )
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
                    color = Color(0xFFfb8500),
                    fontSize = 15.sp
                )
            },
            colors = SearchBarDefaults.colors(
                containerColor = Color(0xFFFFFFFF)
            ),
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(15.dp),
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Buscar",
                    tint = Color(0xFFfb8500)
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
            color = Color(0xFFfb8500)
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


