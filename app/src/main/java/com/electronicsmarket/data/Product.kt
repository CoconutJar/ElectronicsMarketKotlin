package com.electronicsmarket.data

import androidx.annotation.DrawableRes
import java.io.*

/** Data types that define a product */

enum class ProductCategory (val printableName: String) {
    CLEAR("No Filter"),
    COMPUTER("Computer"),
    HEADPHONE("Headphone"),
    PHONE("Phone"),
    TABLET("Tablet"),
}

enum class ProductCondition (val printableName: String) {
    NEW("New"),
    OLD("Old"),
    USED("Used")
}

data class Product(
    val id: Long,
    val name: String,
    @DrawableRes
    val images: List<Int>?,
    val price: Float,
    val category: ProductCategory,
    val condition: ProductCondition,
    val description: String,
) : Serializable