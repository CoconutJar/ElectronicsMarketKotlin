package com.electronicsmarket.data

/** This is a singleton that keep track of the items added to the cart */
class ShoppingCart {

    private val productCatalog = productList()
    // ArrayList that keeps track of the product and the count of each product and whether it is to be ordered
    var orderList:ArrayList<CartItem> = ArrayList<CartItem>()

    fun getProductForId(product_id: Long): Product? {
        return productCatalog.firstOrNull { it.id == product_id }
    }

    /** Add items to the cart map */
    fun addProduct(product: Product, count: Int = 1) {

        var found:Boolean = false
        for(item in orderList){
            if(item.product.id == product.id){
                item.quantity += 1
                found = true
                break
            }
        }
        if(!found){
            orderList.add(CartItem(product,1,false))
        }
    }

    /** Reduces the Quantity of the item in the cart.
     *  Does not remove the product from the numbersMap */
    fun removeProduct(product: Product, count: Int = 1) {

        for(item in orderList){
            if(item.product.id == product.id){
                if(item.quantity > 0) {
                    item.quantity -= 1
                    break
                }
            }
        }
    }

    fun orderProduct(product: Product, order:Boolean){
        for(item in orderList){
            if(item.product.id == product.id){
                item.selected = order
            }
        }
    }

    /** Used to retrieve the items currently in the cart */
    fun getProducts(): ArrayList<CartItem> {
        return orderList
    }

    /** Print content in the shopping cart */
    fun printCart(): String {
        var cartStr = ""
        var totalItems = 0

        for(item in orderList){
            cartStr += "\n${item.product.name}, ${item.quantity}"
            totalItems += item.quantity
        }

        return "Total number of items in cart: $totalItems$cartStr"
    }

    /** Single instance of ShoppingCart class */
    companion object {
        private var INSTANCE: ShoppingCart? = null

        fun getShoppingCart(): ShoppingCart {
            return synchronized(ShoppingCart::class) {
                val newInstance = INSTANCE ?: ShoppingCart()
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}