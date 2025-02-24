package com.example.quickbite.View.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.quickbite.R

@Composable
fun MainFooter(modifier: Modifier) {
    Surface(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(topStart = 34.dp, topEnd = 34.dp))
            .background(Color(0xFF01c2721))) {
        Row(
            modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 10.dp, end = 10.dp, bottom = 20.dp, top = 20.dp)
                ,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .weight(1f)
                    .clickable { },
                painter = painterResource(id = R.drawable.star),
                contentDescription = "Favoritos",
                tint = Color(0xFF57665f)

            )
            VerticalDivider(
                thickness = 3.dp,
                color = Color(0xFF01bd5f)
            )
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .weight(1f)
                    .clickable { },
                painter = painterResource(id = R.drawable.home),
                contentDescription = "Home",
                tint = Color(0xFF57665f)
            )
            VerticalDivider(
                thickness = 3.dp,
                color = Color(0xFF01bd5f)
            )
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .weight(1f)
                    .clickable { },
                painter = painterResource(id = R.drawable.user),
                contentDescription = "Usuario",
                tint = Color(0xFF57665f)
            )


        }
    }
}