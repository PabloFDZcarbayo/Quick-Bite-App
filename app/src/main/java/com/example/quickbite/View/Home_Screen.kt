package com.example.quickbite.View

import android.util.Log
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
import androidx.compose.runtime.collectAsState
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
import com.example.quickbite.Model.User
import com.example.quickbite.R
import com.example.quickbite.View.Components.MainFooter
import com.example.quickbite.View.Components.ShadowBackground
import com.example.quickbite.ViewModel.UnsplashViewModel
import com.example.quickbite.ViewModel.UserViewModel
import com.google.firebase.auth.FirebaseUser


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
    unsplashViewModel: UnsplashViewModel,
    userViewModel: UserViewModel,
    navigateToRecipesCategories: () -> Unit,
    navigateToRecipesList: (String) -> Unit,
    onLogOut: () -> Unit
) {
    val categories = unsplashViewModel.categories.subList(0, 4)
    val isLoading = unsplashViewModel.isLoading.value
    val userData by userViewModel.userData.collectAsState()
    val userState by userViewModel.userState.collectAsState()

    Log.d("Home_Screen", "User data: $userData, User state: $userState")

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
                .background(Color(0xFF0a0c0c)),
            userData,
            userState
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
        MainFooter(
            Modifier.align(Alignment.CenterHorizontally),
            onLogOut = { userViewModel.onLogout() },
            onHomeClick = {})

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
fun HomeTopSection(modifier: Modifier, userData: User?, userState: FirebaseUser?) {
    var busqueda by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    Column(modifier.padding(top = 30.dp)) {
        Welcome(userData, userState)
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
fun Welcome(userData: User?, userState: FirebaseUser?) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val username = userData?.username ?: userState?.displayName ?: "User"
        Text(
            "Welcome, $username",
            fontSize = 25.sp,
            fontWeight = Bold,
            color = Color(0xFFf7fdfd)
        )
        Spacer(Modifier.weight(1f))

        if (userState?.photoUrl != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(userState.photoUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "User Profile",
                modifier = Modifier
                    .size(80.dp)
                    .clip(shape = CircleShape)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.prueba_imagen_perfil),
                contentDescription = "User",
                modifier = Modifier
                    .size(80.dp)
                    .clip(shape = CircleShape)
            )
        }
    }
}




