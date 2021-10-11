package com.electronicsmarket.data

import java.io.Serializable

data class CartItem(val product: Product, var quantity: Int, var selected:Boolean): Serializable
