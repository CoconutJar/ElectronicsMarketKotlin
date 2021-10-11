package com.electronicsmarket.data


/** The full list of Addresses */
fun addressList(): MutableList<Address> {
    return mutableListOf(
            Address(
                    address = "A place",
                    name = "John Smith",
                    phoneNumber = "1234567890",
                    default = true
            ),
            Address(
                    address = "A place 1",
                    name = "John Smith",
                    phoneNumber = "1234567890",
                    default = false
            ),
            Address(
                    address = "A place 2",
                    name = "John Smith",
                    phoneNumber = "1234567890",
                    default = false
            )
    )
}