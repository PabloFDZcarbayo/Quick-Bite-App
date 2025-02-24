package com.example.quickbite.View

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.quickbite.R
import com.example.quickbite.View.Components.ConfirmPassword
import com.example.quickbite.View.Components.Email
import com.example.quickbite.View.Components.Password
import com.example.quickbite.View.Components.UserName



@Composable
fun SingUp_Screen(modifier: Modifier, navigateToLogin: ()-> Unit) {
    var isSingUpEnable by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier.fillMaxSize()
    ) {
        ImageBackground()
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painterResource(id = R.drawable.logo_transparent_background),
                contentDescription = "LOGO",
                Modifier.width(300.dp),
            )
            Surface(
                Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 70.dp,
                            bottomEnd = 70.dp,
                            topEnd = 12.dp,
                            bottomStart = 12.dp
                        )
                    )
                    .background(Color(0xFF0a0c0c))
            ) {
                Card(
                    Modifier.fillMaxSize(),
                    elevation = CardDefaults.cardElevation(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0a0c0c)),
                ) {

                    Text(
                        "Sing Up",
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 10.dp),
                        fontSize = 30.sp,
                        fontWeight = ExtraBold,
                        letterSpacing = 3.sp,
                        color = Color(0xFFf7fdfd)
                    )
                    TextFieldsContainer(Modifier.fillMaxWidth())
                    ProfileImage()
                    Button(
                        onClick = {navigateToLogin()},
                        enabled = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, top = 20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF01bd5f),
                            disabledContainerColor = Color(0xFF1d2721),
                            contentColor = Color(0xFF0a0c0c),
                            disabledContentColor = Color(0xFF57665f),

                            )
                    ) {
                        Text("Create account")
                    }
                }

            }
        }
    }
}

@Composable
fun TextFieldsContainer(modifier: Modifier) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }


    Column(
        modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        UserName(Modifier.fillMaxWidth(), username) { username = it }
        Spacer(Modifier.size(4.dp))
        Email(Modifier.fillMaxWidth(), email) { email = it }
        Spacer(Modifier.size(4.dp))
        Password(Modifier.fillMaxWidth(), password) { password = it }
        Spacer(Modifier.size(4.dp))
        ConfirmPassword(Modifier.fillMaxWidth(), confirmPassword) { confirmPassword = it }
        Spacer(Modifier.size(4.dp))

    }
}

@Composable
fun ProfileImage() {
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    // Launcher para abrir la galería
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            profileImageUri = uri
        })
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.image_animation))
    val progress by animateLottieCompositionAsState(composition)
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { launcher.launch("image/*") },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Select your profile image",
            Modifier.padding(start = 20.dp, top = 15.dp),
            color = Color(0xFF01bd5f),
            fontSize = 15.sp,
            fontWeight = SemiBold
        )
        Spacer (Modifier.width(30.dp))
        // Imagen de perfil o ícono de imagen predeterminado
        if (profileImageUri != null) {
            // Si se seleccionó una imagen, mostrarla
            AsyncImage(
                model = profileImageUri,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
           LottieAnimation(
               composition = composition,
               progress = { progress },
               modifier = Modifier
                   .size(70.dp)
                   .clip(CircleShape),
               contentScale = ContentScale.Crop
           )
        }

    }
}








