package com.electronicsmarket.viewcontrollers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.electronicsmarket.R
import com.electronicsmarket.data.CartItem
import com.electronicsmarket.data.Product

/**
 * Adapter for main activity's recyclerview (Tile Mode)
 */
class ProductTileAdapter(private val onClick: (Product) -> Unit) :
    ListAdapter<Product, ProductTileAdapter.TileViewHolder>(RecyclerViewDiffCallback) {

    /** ViewHolder for Product */
    class TileViewHolder(itemView: View, val onClick: (Product) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val productImageView: ImageView = itemView.findViewById(R.id.tile_product_image)
        private val productNameView: TextView = itemView.findViewById(R.id.tile_product_name)
        private val productCategoryView: TextView =
            itemView.findViewById(R.id.tile_product_category)
        private val productPriceView: TextView = itemView.findViewById(R.id.tile_product_price)
        private val productConditionView: TextView =
            itemView.findViewById(R.id.tile_product_condition)

        private var currentProduct: Product? = null

        init {
            itemView.setOnClickListener {
                currentProduct?.let {
                    onClick(it)
                }
            }
        }

        fun bind(product: Product) {
            // Bind product information, price, condition, category and the first image
            currentProduct = product
            productNameView.text = product.name
            productPriceView.text = String.format("%.2f", product.price)
            productCategoryView.text = product.category.printableName
            productConditionView.text = product.condition.printableName
            if (product.images != null) {
                productImageView.setImageResource(product.images[0])
            }
        }
    }

    /** Creates and inflates view and return TileViewHolder */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TileViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_tile_view, parent, false)
        return TileViewHolder(view, onClick)
    }

    /** Gets current product and uses it to bind view */
    override fun onBindViewHolder(holder: TileViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }
}