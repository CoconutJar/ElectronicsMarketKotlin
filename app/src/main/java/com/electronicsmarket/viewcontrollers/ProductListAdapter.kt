package com.electronicsmarket.viewcontrollers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.electronicsmarket.R
import com.electronicsmarket.data.Product

/**
 * Adapter for main activity's recyclerview (List Mode)
 */
class ProductListAdapter(private val onClick: (Product) -> Unit) :
    ListAdapter<Product, ProductListAdapter.ListViewHolder>(RecyclerViewDiffCallback) {

    /** ViewHolder for product, takes in the inflated view and the onClick behavior. */
    class ListViewHolder(itemView: View, val onClick: (Product) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val productNameView: TextView = itemView.findViewById(R.id.list_product_name)
        private val productCategoryView: TextView =
            itemView.findViewById(R.id.list_product_category)
        private val productPriceView: TextView = itemView.findViewById(R.id.list_product_price)
        private val productConditionView: TextView =
            itemView.findViewById(R.id.list_product_condition)

        private var currentProduct: Product? = null

        init {
            itemView.setOnClickListener {
                currentProduct?.let {
                    onClick(it)
                }
            }
        }

        fun bind(product: Product) {
            currentProduct = product
            productNameView.text = product.name
            productPriceView.text = String.format("%.2f", product.price)
            productCategoryView.text = product.category.printableName
            productConditionView.text = product.condition.printableName
        }
    }

    // Creates and inflates view and return ListViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_list_view, parent, false)
        return ListViewHolder(view, onClick)
    }

    // Gets current product and uses it to bind view.
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }
}

object RecyclerViewDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }
}