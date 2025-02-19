package com.example.quickbite.View.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.quickbite.R

@Composable
fun MainFooter(modifier: Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 20.dp, top = 20.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .weight(1f)
                .clickable { },
            painter = painterResource(id = R.drawable.star),
            contentDescription = "Favoritos",
            tint = Color(0xFFfb8500)

        )
        VerticalDivider(
            thickness = 3.dp,
            color = Color(0xFFfb8500)
        )
        Icon(
            modifier = Modifier
                .size(24.dp)
                .weight(1f)
                .clickable { },
            painter = painterResource(id = R.drawable.home),
            contentDescription = "Home",
            tint = Color(0xFFfb8500)
        )
        VerticalDivider(
            thickness = 3.dp,
            color = Color(0xFFfb8500)
        )
        Icon(
            modifier = Modifier
                .size(24.dp)
                .weight(1f)
                .clickable { },
            painter = painterResource(id = R.drawable.user),
            contentDescription = "Usuario",
            tint = Color(0xFFfb8500)
        )


    }
}
