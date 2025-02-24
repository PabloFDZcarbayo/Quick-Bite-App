package com.example.quickbite.Model

import android.net.Uri

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val image: Uri
) {
}