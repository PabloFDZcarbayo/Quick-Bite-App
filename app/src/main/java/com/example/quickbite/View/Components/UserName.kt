package com.example.quickbite.View.Components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun UserName(modifier: Modifier, username: String, onTextChanged:(String) -> Unit) {

        TextField(
            username,
            onValueChange = { onTextChanged(it) },
            modifier
                .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
            textStyle = TextStyle(fontSize = 20.sp),
            placeholder = { Text("Username", fontSize = 20.sp) },
            maxLines = 1, //Hace que como mucho pueda escribir una línea
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), //Configuramos para que el teclado sea de tipo email

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
            ),
            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        )
    }

