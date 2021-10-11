package com.electronicsmarket.viewcontrollers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.electronicsmarket.R
import com.electronicsmarket.data.CartItem
import com.electronicsmarket.data.ShoppingCart


class OrderAdapter( private val refresh: (Long, Int) -> (Unit),
    private val cartMap: List<CartItem>
): ListAdapter<CartItem, OrderAdapter.ItemViewHolder>(RecyclerViewDiffCallbackTwo) {

    class ItemViewHolder(private val refresh: (Long, Int) -> (Unit), private val view: View) : RecyclerView.ViewHolder(view) {
        // Connect variables to UI elements.
        var orderItemId: Long = 0
        val orderItemName: TextView = view.findViewById(R.id.order_product_name)
        val orderItemPrice: TextView = view.findViewById(R.id.order_product_price)
        val orderItemQuantity: TextView = view.findViewById(R.id.order_product_quantity)
        val orderItemImage: ImageView = view.findViewById(R.id.orderImageView)

        val cart = ShoppingCart.getShoppingCart()

        private val increaseButton: Button = view.findViewById(R.id.increase_quant_button)
        private val decreaseButton: Button = view.findViewById(R.id.decrease_quant_button)

        init {
            increaseButton.setOnClickListener {
                cart.addProduct(orderItemId)

                refresh(orderItemId, 1)
            }
            decreaseButton.setOnClickListener {
                cart.removeProduct(orderItemId)

                refresh(orderItemId, -1)
            }

        }

    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.ItemViewHolder {
        // create a new view

        val adapterLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.order_item_view, parent, false)

        return OrderAdapter.ItemViewHolder(refresh, adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: OrderAdapter.ItemViewHolder, position: Int) {
        val item = cartMap[position]

        holder.orderItemId = item.product.id
        holder.orderItemName.text = item.product.name
        holder.orderItemPrice.text = item.product.price.toString()
        holder.orderItemQuantity.text = item.quantity.toString()
        holder.orderItemImage.setImageResource(item.product.images!![0])

    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = cartMap.size
}

object RecyclerViewDiffCallbackThree : DiffUtil.ItemCallback<CartItem>() {
    override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem == newItem
    }

    // Check quantity since it will be changing with the -,+ buttons
    override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem.quantity == newItem.quantity
    }
}