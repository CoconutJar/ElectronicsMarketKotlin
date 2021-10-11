package com.electronicsmarket.viewcontrollers

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.electronicsmarket.R
import com.electronicsmarket.data.AddressListHelper
import com.electronicsmarket.data.CartItem
import com.electronicsmarket.data.ShoppingCart


class ShoppingCartActivity : AppCompatActivity() {

    private val cart = mutableListOf<CartItem>()
    private var orderArrayList: ArrayList<CartItem> = ArrayList<CartItem>()
    private lateinit var recyclerViewer: RecyclerView
    private lateinit var totalCostView: TextView
    private var totalCost: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shopping_cart_activity)

        val purchaseButton: Button = findViewById(R.id.purchase_button)
        purchaseButton.setOnClickListener {
            val intent3 = Intent(this, OrderActivity()::class.java)
            intent3.putExtra("OrderList", orderArrayList)
            startActivityForResult(intent3, 5)
        }

        val shoppingList = ShoppingCart.getShoppingCart().getProducts()
        for ((id, count) in shoppingList) {
            val product = ShoppingCart.getShoppingCart().getProductForId(id)
            if (product != null) {
                val newItem = CartItem(product, count)h
                cart.add(newItem)
            }
        }

        val cartAdapter = CartAdapter({ refresh() }, { cost -> addTotalPrice(cost) }, { id, quantity -> addToOrder(id, quantity)}, cart)
        totalCostView = findViewById(R.id.cart_total)
        recyclerViewer = findViewById<RecyclerView>(R.id.recycler_cart_view)
        recyclerViewer.layoutManager = GridLayoutManager(this, 1)
        recyclerViewer.adapter = cartAdapter

        cartAdapter.submitList(cart)
    }

    override fun onResume() {
        super.onResume()
        resetTotal()
    }

    /** Updates RecyclerView with new data */
    private fun refresh(){

        cart.clear()
        val shoppingList = ShoppingCart.getShoppingCart().getProducts()
        for (item in shoppingList) {
            val product = item.product
            if (product != null) {
                val newItem = CartItem(product, item.quantity, item.selected)
                cart.add(newItem)
            }
        }
        recyclerViewer.adapter?.notifyDataSetChanged()
        resetTotal()
    }

    private fun addTotalPrice(cost: Double){

        totalCost += cost
        totalCostView.text = totalCost.toString()
        refresh()
    }

    private fun addToOrder(id: Long, quantity: Int){
        val product = ShoppingCart.getShoppingCart().getProductForId(id)

//        if(product!= null) {
//            val orderItem: CartItem = CartItem(product, quantity)
//            var exists: Boolean = false
//            for (item in orderArrayList){
//                if(item.product == orderItem.product){
//                    exists = true
//                    item.quantity += quantity
//                    if(item.quantity <= 0 ) {
//                        orderArrayList.remove(item)
//                    }
//                    break
//                }
//            }
//            if(!exists && quantity > 0){
//                orderArrayList.add(orderItem)
//            }
//        }
    }

    /** Updates Default Address with new data */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode >= 1) {
            // When the user presses the back button
            if (resultCode == 0) {
                //Update orders
                refresh()
            }
        }
    }

    private fun resetTotal(){
        var total:Float = 0.0F
        val shoppingList = ShoppingCart.getShoppingCart().getProducts()
        for ((id, count) in shoppingList) {
            val product = ShoppingCart.getShoppingCart().getProductForId(id)
            if (product != null) {
                total += product.price.times(count)
            }
        }
        totalCostView.text = total.toString()
    }
}
