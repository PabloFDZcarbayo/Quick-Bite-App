package com.example.quickbite.Core.Navigation.Types

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.example.quickbite.Data.Remote.SpoonacularService
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val RecipeType = object : NavType<SpoonacularService.Recipe>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): SpoonacularService.Recipe? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, SpoonacularService.Recipe::class.java)
        } else {
            bundle.getParcelable(key)
        }
    }

    override fun serializeAsValue(value: SpoonacularService.Recipe): String {
        return Uri.encode(Json.encodeToString(value))
    }
    override fun parseValue(value: String): SpoonacularService.Recipe {
        return Json.decodeFromString<SpoonacularService.Recipe>(value)
    }

    override fun put(bundle: Bundle, key: String, value: SpoonacularService.Recipe) {
        bundle.putParcelable(key, value)
    }
}