package com.example.quickbite.View


import android.annotation.SuppressLint
import android.app.Activity
import android.util.Patterns
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickbite.R
import com.example.quickbite.View.Components.Email
import com.example.quickbite.View.Components.Password
import com.example.quickbite.View.Components.ShadowBackground

@Composable
fun Login_Screen(modifier: Modifier, navigateToHome: () -> Unit, navigateToSingUp: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        LoginTopSection(
            Modifier
                .align(Alignment.TopCenter)
                .height(400.dp)
        )
        LoginBody(
            Modifier
                .align(Alignment.BottomCenter),
            navigateToHome,
            navigateToSingUp

        )
    }
}

@Composable
fun LoginTopSection(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        ImageBackground()
        LoginHeader(Modifier.align(Alignment.TopStart))
        ImageLogo(
            Modifier
                .align(Alignment.Center)
                .size(300.dp)
        )
    }
}

@Composable
fun ImageBackground() {
    // Imagen de fondo
    Image(
        painter = painterResource(id = R.drawable.background_login),
        contentDescription = "Login Background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    ShadowBackground()
}


@Composable
fun Footer(modifier: Modifier, navigateToSingUp: () -> Unit) {
    Column(
        modifier
            .fillMaxWidth()
    ) {
        HorizontalDivider(
            Modifier
                .height(1.dp)
                .fillMaxWidth()
        )
        Spacer(
            Modifier
                .size(20.dp)
        )
        SingUp(navigateToSingUp)

    }
}

@Composable
fun SingUp(navigateToSingUp: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            "¿No tienes una cuenta?",
            Modifier.padding(end = 6.dp),
            fontSize = 14.sp,
            color = Color(0xFFf7fdfd)
        )
        Text(
            "Regístrate",
            Modifier.clickable {navigateToSingUp() },
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color(0xFF01bd5f)
        )
    }
    Spacer(Modifier.size(13.dp))
}


@Composable
fun LoginBody(modifier: Modifier, navigateToHome: () -> Unit, navigateToSingUp: () -> Unit) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isLoginEnable by rememberSaveable { mutableStateOf(false) }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 34.dp, topEnd = 34.dp))

    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF0a0c0c))

        ) {
            Spacer(Modifier.size(40.dp))
            Email(Modifier.align(Alignment.CenterHorizontally), email) {
                email = it
                isLoginEnable = enableLogin(email, password)
            }
            Spacer(Modifier.size(8.dp))
            Password(Modifier.align(Alignment.CenterHorizontally), password) {
                password = it
                isLoginEnable = enableLogin(email, password)
            }
            Spacer(Modifier.size(2.dp))
            ForgotPassword(Modifier.align(Alignment.End))
            Spacer(Modifier.size(16.dp))
            LoginButton(isLoginEnable, navigateToHome)
            Spacer(Modifier.size(10.dp))
            LoginDivider(Modifier.align(Alignment.CenterHorizontally))
            Spacer(Modifier.size(10.dp))
            SocialLogin(Modifier.align(Alignment.CenterHorizontally))
            Spacer(Modifier.size(20.dp))
            Footer(Modifier.align(Alignment.CenterHorizontally), navigateToSingUp)

        }

    }
}


@Composable
fun SocialLogin(modifier: Modifier) {
    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(id = R.drawable.google),
            contentDescription = "google",
            Modifier.size(17.dp)
        )
        Text(
            "Continuar con Google",
            Modifier.padding(start = 12.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = Color(0xFFf7fdfd)
        )
    }
    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(id = R.drawable.facebook),
            contentDescription = "Facebook",
            Modifier.size(17.dp)
        )
        Text(
            "Continuar con Facebook",
            Modifier.padding(start = 12.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = Color(0xFFf7fdfd)
        )
    }


}

@Composable
fun LoginDivider(modifier: Modifier) {
    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(
            Modifier
                .background(Color(0xFF01bd5f))
                .height(1.dp)
                .weight(1f) // 1/3 of the space
        )
        Text(
            "OR",
            Modifier.padding(horizontal = 20.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = Color(0xFFf7fdfd)
        )
        HorizontalDivider(
            Modifier
                .background(Color(0xFF01bd5f))
                .height(1.dp)
                .weight(1f)// 1/3 of the space
        )
    }

}

@Composable
fun LoginButton(loginEnable: Boolean, navigateToHome: () -> Unit) {
    Button(
        onClick = { navigateToHome() },
        enabled = loginEnable,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF01bd5f),
            disabledContainerColor = Color(0xFF1d2721),
            contentColor = Color(0xFF0a0c0c),
            disabledContentColor = Color(0xFF57665f),

            )
    ) {
        Text("Iniciar sesión")
    }
}

//Comprueba que el texto que le pasamos (email) tenga el formato email, ademas que la password tenga la longitud mínima
fun enableLogin(email: String, password: String) =
    Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 6


@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        "¿Olvidaste tu contraseña?",
        modifier = modifier
            .padding(end = 30.dp)
            .clickable { },
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF01bd5f)
    )
}


@Composable
fun ImageLogo(modifier: Modifier) {
    Image(
        painterResource(id = R.drawable.logo_transparent_background),
        contentDescription = "logo",
        modifier = modifier
    )
}

@SuppressLint("ContextCastToActivity")
@Composable
fun LoginHeader(modifier: Modifier) {
    val activity =
        LocalContext.current as Activity // Almacenar el contexto de la actividad

    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {

        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "close app",
            modifier = modifier
                .clickable { activity.finish() }
                .size(30.dp)
                .padding(end = 10.dp),
        )
    }
}


