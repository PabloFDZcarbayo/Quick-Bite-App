package com.example.quickbite.View.Components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quickbite.R


@Composable
fun Password(modifier: Modifier, password: String, onTextChanged: (String) -> Unit) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

        TextField(
            password,
            onValueChange = { onTextChanged(it) },
            modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
            textStyle = TextStyle(fontSize = 20.sp),
            placeholder = { Text(text = "Password", fontSize = 20.sp) },
            maxLines = 1, //Hace que como mucho pueda escribir una línea
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), //Configuramos para que el teclado sea de tipo contraseña

            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFF1d2721),
                focusedContainerColor = Color(0xFF1d2721),
                cursorColor = Color(0xFF01bd5f),
                focusedIndicatorColor = Color(0xFF01bd5f),
                unfocusedIndicatorColor = Color(0xFF57665f),
                focusedTextColor = Color(0xFF01bd5f),
                unfocusedTextColor = Color(0xFF57665f),
                unfocusedPlaceholderColor = Color(0xFF57665f),
                focusedPlaceholderColor = Color(0xFF57665f)
            ), shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),

            /*
            En este bloque añadimos el icono de mostrar ocultar la contraseña
            La primera parte gestiona que icono se muestra dependiendo de la variable passwordVisibility
            La ultima parte gestiona el evento de mostrar/ocultar la contraseña al pulsar el icono
            */
            trailingIcon = {
                val imagen = if (passwordVisibility) {
                    R.drawable.hidden_eye
                } else {
                    R.drawable.eye
                }

                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = imagen),
                        tint = Color(0xFF57665f),
                        contentDescription = "Mostrar contraseña"
                    )
                }
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
        )
    }

