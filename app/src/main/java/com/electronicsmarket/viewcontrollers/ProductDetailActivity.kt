package com.electronicsmarket.viewcontrollers

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Carousel
import com.electronicsmarket.R
import com.electronicsmarket.data.ShoppingCart
import com.electronicsmarket.data.productList

/**
 * Detailed view from tapping on an item in list mode or tile mode. Contains "Add to cart" button
 */
class ProductDetailActivity : AppCompatActivity() {
    private val productCatalog = productList()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_detail_activity)

        var currentProductId: Long? = null

        // Connect variables to UI elements.
        val productName: TextView = findViewById(R.id.detail_product_name)
        val productCategory: TextView = findViewById(R.id.detail_product_category)
        val productCondition: TextView = findViewById(R.id.detail_product_condition)
        val productPrice: TextView = findViewById(R.id.detail_product_price)
        val productDescription: TextView = findViewById(R.id.detail_product_description)

        val carouselView: Carousel = findViewById(R.id.carousel)
        val addToCartButton: Button = findViewById(R.id.add_to_cart_button)
        val backItemButton: Button = findViewById(R.id.back_item_button)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentProductId = bundle.getLong(PRODUCT_ID)
        }

        // If currentProductId is not null, get name, image and description
        currentProductId?.let {
            val currentProduct = productCatalog.firstOrNull { it.id == currentProductId }
            productName.text = currentProduct?.name
            productCategory.text = "Category: " + currentProduct?.category?.printableName
            productCondition.text = "Condition: " + currentProduct?.condition?.printableName
            productPrice.text = "Price: $" + String.format("%.2f", currentProduct?.price)
            productDescription.text = "Description: " + currentProduct?.description

            addToCartButton.setOnClickListener {
                if (currentProduct != null) {
                    val cart = ShoppingCart.getShoppingCart()
                    cart.addProduct(currentProduct.id)
                    Toast.makeText(this, cart.printCart(), Toast.LENGTH_SHORT).show()
                }
            }

            backItemButton.setOnClickListener {
                // Go back to main activity
                finish()
            }

            carouselView.setAdapter(object : Carousel.Adapter {
                override fun count(): Int {
                    // Return the number of items we have in the carousel
                    return 3
                }

                override fun populate(view: View, index: Int) {
                    if (currentProduct == null) {
                        (view as ImageView).setImageResource(R.drawable.acer_1)
                    } else {
                        (view as ImageView).setImageResource(currentProduct.images!![index])
                    }
                }

                override fun onNewItem(index: Int) {
                }
            })
        }
    }
}