package com.electronicsmarket.data

import java.io.Serializable

// Model for addresses
data class Address(
        var address: String,
        var name: String,
        var phoneNumber: String,
        var default: Boolean
) : Serializable